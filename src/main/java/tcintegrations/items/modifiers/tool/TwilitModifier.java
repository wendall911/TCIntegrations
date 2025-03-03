package tcintegrations.items.modifiers.tool;

import java.util.List;

import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;

import net.minecraftforge.event.entity.player.PlayerEvent;

import slimeknights.mantle.client.TooltipKey;

import slimeknights.mantle.util.RegistryHelper;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ConditionalStatModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BreakSpeedModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.tools.stats.ToolType;

import tcintegrations.data.integration.ModIntegration;

import static tcintegrations.util.ResourceLocationHelper.resource;

public class TwilitModifier extends Modifier implements ConditionalStatModifierHook, BreakSpeedModifierHook, MeleeDamageModifierHook, TooltipModifierHook {

    private static final Component MINING_SPEED = Component.translatable(
        Util.makeDescriptionId("modifier", resource("twilit.mining_speed")));
    private static final Component VELOCITY = Component.translatable(
        Util.makeDescriptionId("modifier", resource("twilit.velocity")));
    private static final Component DAMAGE = Component.translatable(
        Util.makeDescriptionId("modifier", resource("twilit.damage")));
    private static final float VELOCITY_MULTIPLIER = 1.1F;
    private static final float MINING_SPEED_MULTIPLIER = 2F;
    private static final float MELEE_DAMAGE_MULTIPLIER = 1.5F;

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.CONDITIONAL_STAT, ModifierHooks.BREAK_SPEED, ModifierHooks.MELEE_DAMAGE, ModifierHooks.TOOLTIP);
    }

    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity entity, FloatToolStat stat, float baseValue, float multiplier) {
        if (stat == ToolStats.VELOCITY) {
            final Player player = entity instanceof Player ? (Player) entity : null;

            if (player != null && !player.level().isClientSide) {
                return getVelocityBonus(player, baseValue);
            }
        }

        return baseValue;
    }

    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        final Player player = context.getPlayerAttacker();

        if (player != null && !player.level().isClientSide && !isTwilightForest(player)) {
            return getMeleeDamageBonus(player, damage);
        }

        return damage;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null && key == TooltipKey.SHIFT) {
            Item toolItem = tool.getItem();

            if (!isTwilightForest(player) && RegistryHelper.contains(ToolType.MELEE.getTag(), toolItem)) {
                TooltipModifierHook.addPercentBoost(this, DAMAGE, MELEE_DAMAGE_MULTIPLIER, tooltip);
            }
            if (isTwilightForest(player)) {
                if (RegistryHelper.contains(ToolType.HARVEST.getTag(), toolItem)) {
                    TooltipModifierHook.addPercentBoost(this, MINING_SPEED, MINING_SPEED_MULTIPLIER, tooltip);
                }
                if (RegistryHelper.contains(ToolType.RANGED.getTag(), toolItem)) {
                    TooltipModifierHook.addPercentBoost(this, VELOCITY, VELOCITY_MULTIPLIER, tooltip);
                }
            }
        }
    }

    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction direction, boolean isEffective, float miningSpeedModifier) {
        if (isEffective && isTwilightForest(event.getEntity())) {
            event.setNewSpeed(getMiningSpeedBonus(event.getEntity(), miningSpeedModifier));
        }
    }

    private static float getVelocityBonus(Player player, float baseValue) {
        if (isTwilightForest(player)) {
            return baseValue * VELOCITY_MULTIPLIER;
        }

        return baseValue;
    }

    private static float getMiningSpeedBonus(Player player, float miningSpeedModifier) {
        if (isTwilightForest(player)) {
            return miningSpeedModifier * MINING_SPEED_MULTIPLIER;
        }

        return miningSpeedModifier;
    }

    private static float getMeleeDamageBonus(Player player, float damage) {
        if (!isTwilightForest(player)) {
            return damage * MELEE_DAMAGE_MULTIPLIER;
        }

        return damage;
    }

    private static boolean isTwilightForest(Player player) {
        return player.level().dimension().location().toString().contains(ModIntegration.twilightLoc("twilight_forest").toString());
    }

}
