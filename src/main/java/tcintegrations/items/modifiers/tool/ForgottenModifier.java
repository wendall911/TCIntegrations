package tcintegrations.items.modifiers.tool;

import java.util.List;
import java.util.Objects;

import org.jetbrains.annotations.Nullable;

import net.minecraftforge.registries.ForgeRegistries;

import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;

import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.TinkerHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import tcintegrations.TCIntegrations;
import tcintegrations.data.integration.ModIntegration;

public class ForgottenModifier extends NoLevelsModifier implements MeleeDamageModifierHook, BreakSpeedModifierHook, TooltipModifierHook {

    private static final Component MINING_SPEED = Component.translatable(
            Util.makeDescriptionId("modifier", new ResourceLocation(TCIntegrations.MODID, "forgotten.mining_speed")));
    private static final Component ATTACK_INCREASE = Component.translatable(
            Util.makeDescriptionId("modifier", new ResourceLocation(TCIntegrations.MODID, "forgotten.attack_increase")));

    @Override
    protected void registerHooks(ModifierHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, TinkerHooks.MELEE_DAMAGE, TinkerHooks.BREAK_SPEED, TinkerHooks.TOOLTIP);
    }

    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        LivingEntity target = context.getLivingTarget();

        if (target != null) {
            LivingEntity attacker = context.getAttacker();
            BlockState state = attacker.level.getBlockState(attacker.getOnPos());

            if (tool.hasTag(TinkerTags.Items.MELEE) && isUndergarden(state, attacker)) {
                return damage * 1.5F;
            }
        }

        return damage;
    }

    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        BlockState state = event.getState();

        if (tool.hasTag(TinkerTags.Items.HARVEST) && isEffective && state != null && isUndergarden(state, event.getEntity())) {
            event.setNewSpeed(event.getOriginalSpeed() * 1.5F);
        }
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        BlockState state = player != null ? player.level.getBlockState(player.getOnPos()) : null;

        if (state != null && isUndergarden(state, player)) {
            if (tool.hasTag(TinkerTags.Items.HARVEST)) {
                TooltipModifierHook.addPercentBoost(modifier.getModifier(), MINING_SPEED, 0.5F, tooltip);
            }

            if (tool.hasTag(TinkerTags.Items.MELEE)) {
                TooltipModifierHook.addPercentBoost(modifier.getModifier(), ATTACK_INCREASE, 0.5F, tooltip);
            }
        }
    }

    private boolean isUndergarden(BlockState state, LivingEntity livingEntity) {
        return Objects.requireNonNull(ForgeRegistries.BLOCKS.getKey(state.getBlock())).getNamespace().equals(ModIntegration.UNDERGARDEN_MODID) && livingEntity.canChangeDimensions();
    }

}
