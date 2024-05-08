package tcintegrations.items.modifiers.armor;

import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;

import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.TinkerHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.util.ModifierHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import tcintegrations.items.TCIntegrationHooks;
import tcintegrations.items.modifiers.hooks.IArmorCrouchModifier;

public class FrontierCapModifier extends Modifier implements IArmorCrouchModifier, EquipmentChangeModifierHook, TooltipModifierHook {

    private static final UUID ATTRIBUTE_BONUS = UUID.fromString("0f65a587-22ee-4147-9dbe-79d88f078402");
    private static final float SPEED_FACTOR = 0.04F;

    @Override
    protected void registerHooks(ModifierHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, TCIntegrationHooks.CROUCH, TinkerHooks.EQUIPMENT_CHANGE, TinkerHooks.TOOLTIP);
    }

    @Override
    public void onCrouch(IToolStackView tool, int level, LivingEntity living) {
        // no point trying if not on the ground
        if (tool.isBroken() || !living.isOnGround() || living.level.isClientSide) {
            return;
        }
        // must have speed
        AttributeInstance attribute = living.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attribute == null) {
            return;
        }

        // start by removing the attribute, we are likely going to give it a new number
        if (attribute.getModifier(ATTRIBUTE_BONUS) != null) {
            attribute.removeModifier(ATTRIBUTE_BONUS);
        }

        if (!living.isFallFlying()) {
            RandomSource rand = living.getRandom();

            // boost speed
            float boost = level * SPEED_FACTOR;

            attribute.addTransientModifier(new AttributeModifier(ATTRIBUTE_BONUS, "tcintegrations.modifier.frontiercap", boost, AttributeModifier.Operation.ADDITION));

            // damage helmet
            if (rand.nextFloat() < 0.04F) {
                ToolDamageUtil.damageAnimated(tool, 1, living, EquipmentSlot.HEAD);
            }
        }
    }

    @Override
    public void onStand(LivingEntity living) {
        AttributeInstance attribute = living.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attribute == null) {
            return;
        }

        // start by removing the attribute, we are likely going to give it a new number
        if (attribute.getModifier(ATTRIBUTE_BONUS) != null) {
            attribute.removeModifier(ATTRIBUTE_BONUS);
        }
    }

    @Override
    public void onUnequip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        // remove boost when helmet is removed
        LivingEntity livingEntity = context.getEntity();
        if (!livingEntity.level.isClientSide && context.getChangedSlot() == EquipmentSlot.HEAD) {
            IToolStackView newTool = context.getReplacementTool();
            // damaging the tool will trigger this hook, so ensure the new tool has the same level
            if (newTool == null || newTool.isBroken() || newTool.getModifierLevel(this) != modifier.getEffectiveLevel()) {
                AttributeInstance attribute = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
                if (attribute != null && attribute.getModifier(ATTRIBUTE_BONUS) != null) {
                    attribute.removeModifier(ATTRIBUTE_BONUS);
                }
            }
        }
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player == null || tooltipKey == TooltipKey.SHIFT || (player.isCrouching() || player.isVisuallySwimming())) {
            TooltipModifierHook.addPercentBoost(modifier.getModifier(), getDisplayName(), modifier.getEffectiveLevel() * SPEED_FACTOR, tooltip);
        }
    }

}
