package tcintegrations.items.modifiers.armor;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.network.chat.Component;
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
import slimeknights.tconstruct.library.modifiers.hook.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.util.ModifierHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import tcintegrations.items.modifiers.TCIntegrationsHooks;
import tcintegrations.items.modifiers.hooks.ArmorCrouchModifierHook;

public class FrontierCapModifierHook extends Modifier implements ArmorCrouchModifierHook, TooltipModifierHook {

    private static final UUID ATTRIBUTE_BONUS = UUID.fromString("0f65a587-22ee-4147-9dbe-79d88f078402");
    private static final float SPEED_FACTOR = 0.04F;

    @Override
    public void onCrouch(IToolStackView tool, ModifierEntry modifier, LivingEntity living) {
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
            Random rand = living.getRandom();

            // boost speed
            float boost = modifier.getLevel() * SPEED_FACTOR;

            attribute.addTransientModifier(new AttributeModifier(ATTRIBUTE_BONUS, "tcintegrations.modifier.frontiercap", boost, AttributeModifier.Operation.ADDITION));

            // damage helmet
            if (rand.nextFloat() < 0.04F) {
                ToolDamageUtil.damageAnimated(tool, 1, living, EquipmentSlot.HEAD);
            }
        }
    }

    @Override
    public void onStand(ModifierEntry modifier, LivingEntity living) {
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
    public void onUnequip(IToolStackView tool, int level, EquipmentChangeContext context) {
        // remove boost when helmet is removed
        LivingEntity livingEntity = context.getEntity();
        if (!livingEntity.level.isClientSide && context.getChangedSlot() == EquipmentSlot.HEAD) {
            IToolStackView newTool = context.getReplacementTool();
            // damaging the tool will trigger this hook, so ensure the new tool has the same level
            if (newTool == null || newTool.isBroken() || newTool.getModifierLevel(this) != level) {
                AttributeInstance attribute = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
                if (attribute != null && attribute.getModifier(ATTRIBUTE_BONUS) != null) {
                    attribute.removeModifier(ATTRIBUTE_BONUS);
                }
            }
        }
    }

    @Override
    protected void registerHooks(ModifierHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, TCIntegrationsHooks.ARMOR_CROUCH);
    }

    // TODO Figure out why this is still needed. I'm not sure why it isn't firing correctly without the old method
    @Override
    public <T> T getModule(Class<T> type) {
        return tryModuleMatch(type, ArmorCrouchModifierHook.class, this);
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @org.jetbrains.annotations.Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player == null || tooltipKey == TooltipKey.SHIFT || (player.isCrouching() || player.isVisuallySwimming())) {
            TooltipModifierHook.addPercentBoost(modifier.getModifier(), getDisplayName(), modifier.getLevel() * SPEED_FACTOR, tooltip);
        }
    }

}
