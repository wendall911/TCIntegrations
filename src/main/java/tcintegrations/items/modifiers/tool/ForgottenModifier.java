package tcintegrations.items.modifiers.tool;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import tcintegrations.TCIntegrations;
import tcintegrations.data.integration.ModIntegration;

public class ForgottenModifier extends NoLevelsModifier {

    private static final Component MINING_SPEED = new TranslatableComponent(
            Util.makeDescriptionId("modifier", new ResourceLocation(TCIntegrations.MODID, "forgotten.mining_speed")));
    private static final Component ATTACK_INCREASE = new TranslatableComponent(
            Util.makeDescriptionId("modifier", new ResourceLocation(TCIntegrations.MODID, "forgotten.attack_increase")));

    @Override
    public float getEntityDamage(IToolStackView tool, int level, ToolAttackContext context, float baseDamage, float damage) {
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
    public void onBreakSpeed(IToolStackView tool, int level, PlayerEvent.BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        BlockState state = event.getState();

        if (tool.hasTag(TinkerTags.Items.HARVEST) && isEffective && state != null && isUndergarden(state, event.getEntityLiving())) {
            event.setNewSpeed(event.getOriginalSpeed() * 1.5F);
        }
    }

    @Override
    public void addInformation(IToolStackView tool, int level, @Nullable Player player, List<Component> tooltip, slimeknights.tconstruct.library.utils.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        BlockState state = player != null ? player.level.getBlockState(player.getOnPos()) : null;

        if (state != null && isUndergarden(state, player)) {
            if (tool.hasTag(TinkerTags.Items.HARVEST)) {
                addPercentTooltip(MINING_SPEED, 0.5F, tooltip);
            }

            if (tool.hasTag(TinkerTags.Items.MELEE)) {
                addPercentTooltip(ATTACK_INCREASE, 0.5F, tooltip);
            }
        }
    }

    private boolean isUndergarden(BlockState state, LivingEntity livingEntity) {
        return state.getBlock().getRegistryName().getNamespace().equals(ModIntegration.UNDERGARDEN_MODID) && livingEntity.canChangeDimensions();
    }

}
