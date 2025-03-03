package tcintegrations.items.modifiers.tool;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;

import net.minecraftforge.event.entity.player.PlayerEvent;

import slimeknights.mantle.client.TooltipKey;

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

import static tcintegrations.util.ResourceLocationHelper.resource;

public class PrecipitateModifier extends Modifier implements ConditionalStatModifierHook, BreakSpeedModifierHook, MeleeDamageModifierHook, TooltipModifierHook {

    private static final Component SPEED = Component.translatable(
        Util.makeDescriptionId("modifier", resource("precipitate.speed")));

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.CONDITIONAL_STAT, ModifierHooks.BREAK_SPEED, ModifierHooks.MELEE_DAMAGE, ModifierHooks.TOOLTIP);
    }

    private float getBonusPercentage(Player player) {
        // Grants a bonus based on the player's missing health
        if (player == null) return 0.1F;

        float maxHealth = player.getMaxHealth();

        return (maxHealth - player.getHealth()) / maxHealth;
    }

    @Override
    public float modifyStat(IToolStackView tool, ModifierEntry modifier, LivingEntity holder, FloatToolStat stat, float baseValue, float multiplier) {
        final Player player = holder instanceof Player ? (Player) holder : null;

        if (player != null && !player.level().isClientSide) {
            if (stat == ToolStats.VELOCITY) {
                return baseValue + (baseValue * getBonusPercentage(player));
            }
            if (stat == ToolStats.ATTACK_SPEED) {
                return baseValue + (baseValue * getBonusPercentage(player));
            }
        }

        return baseValue;
    }

    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        final Player player = context.getPlayerAttacker();

        if (player != null && !player.level().isClientSide) {
            return damage * (1 + getBonusPercentage(player));
        }

        return damage;
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey key, TooltipFlag tooltipFlag) {
        if (player != null && key == TooltipKey.SHIFT) {
            TooltipModifierHook.addPercentBoost(this, SPEED, getBonusPercentage(player), tooltip);
        }
    }

    @Override
    public void onBreakSpeed(IToolStackView tool, ModifierEntry modifier, PlayerEvent.BreakSpeed event, Direction direction, boolean isEffective, float miningSpeedModifier) {
        if (isEffective) {
            event.setNewSpeed(event.getNewSpeed() + (event.getNewSpeed() * (1 + getBonusPercentage(event.getEntity()))));
        }
    }

}
