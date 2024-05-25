package tcintegrations.items.modifiers.tool;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import com.teammetallurgy.aquaculture.init.AquaBlocks;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;

import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerEvent;

import slimeknights.mantle.client.TooltipKey;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.BlockInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap.Builder;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import static tcintegrations.util.ResourceLocationHelper.resource;

public class SirenModifier extends NoLevelsModifier implements BreakSpeedModifierHook, TooltipModifierHook, BlockInteractionModifierHook, MeleeDamageModifierHook {

    private static final float ATTACK_BONUS = 1.5F;
    private static final float SPEED_BONUS = 5.0F;
    private static final Component MINING_SPEED = Component.translatable(
            Util.makeDescriptionId("modifier", resource("siren.mining_speed")));

    @Override
    protected void registerHooks(Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BREAK_SPEED, ModifierHooks.TOOLTIP, ModifierHooks.BLOCK_INTERACT, ModifierHooks.MELEE_DAMAGE);
    }

    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        if (tool.hasTag(TinkerTags.Items.MELEE) && context.getAttacker().isEyeInFluid(FluidTags.WATER) && !tool.isBroken()) {
            return damage * ATTACK_BONUS;
        }

        return damage;
    }

    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        final Player player = event.getEntity();

        if (player != null && !player.level.isClientSide && !tool.isBroken()) {
            final ServerPlayer sp = (ServerPlayer) player;
            boolean isSubmerged = sp.isUnderWater() && sp.isInWater() && sp.isInWaterRainOrBubble();

            if (isEffective && tool.hasTag(TinkerTags.Items.HARVEST) && isSubmerged) {
                event.setNewSpeed(event.getNewSpeed() + (SPEED_BONUS * tool.getMultiplier(ToolStats.MINING_SPEED) * miningSpeedModifier));
            }
        }
    }

    @Override
    public InteractionResult afterBlockUse(IToolStackView tool, ModifierEntry modifier, UseOnContext context, InteractionSource source) {
        Player player = context.getPlayer();
        Level mcLevel = context.getLevel();

        if (player != null && !mcLevel.isClientSide && !tool.isBroken()) {
            BlockPos pos = context.getClickedPos();
            BlockState blockState = mcLevel.getBlockState(pos).getToolModifiedState(context, ToolActions.HOE_TILL, false);

            if (blockState != null
                    && context.getClickedFace() != Direction.DOWN
                    && mcLevel.isEmptyBlock(pos.above())
                    && blockState == Blocks.FARMLAND.defaultBlockState()) {
                mcLevel.setBlock(pos, AquaBlocks.FARMLAND.get().defaultBlockState(), 2);

                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        float bonus = 0.0F;

        if (tool.hasTag(TinkerTags.Items.MELEE)) {
            if (player != null
                    && tooltipKey == TooltipKey.SHIFT
                    && player.isEyeInFluid(FluidTags.WATER)) {
                float damage = ToolAttackUtil.getAttributeAttackDamage(tool, player, slimeknights.tconstruct.library.utils.Util.getSlotType(player.getUsedItemHand()));
                bonus = damage * ATTACK_BONUS;
            }

            if (bonus > 0.0F) {
                TooltipModifierHook.addDamageBoost(tool, modifier.getModifier(), bonus, tooltip);
            }
        }

        if (tool.hasTag(TinkerTags.Items.HARVEST)) {
            if (player != null
                    && tooltipKey == TooltipKey.SHIFT
                    && player.isEyeInFluid(FluidTags.WATER)) {
                bonus = SPEED_BONUS;
            }

            if (bonus > 0.0F) {
                TooltipModifierHook.addFlatBoost(modifier.getModifier(), MINING_SPEED, bonus * tool.getMultiplier(ToolStats.MINING_SPEED), tooltip);
            }
        }
    }

}
