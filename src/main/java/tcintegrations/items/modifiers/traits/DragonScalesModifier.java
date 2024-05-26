package tcintegrations.items.modifiers.traits;

import java.util.Arrays;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class DragonScalesModifier extends NoLevelsModifier implements ModifyDamageModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.MODIFY_DAMAGE);
    }

    @Override
    public float modifyDamageTaken(IToolStackView armor, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (armor.hasTag(TinkerTags.Items.HELMETS)) {
            amount -= 0.1;
        }
        else if (armor.hasTag(TinkerTags.Items.CHESTPLATES)) {
            amount -= 0.3;
        }
        else if (armor.hasTag(TinkerTags.Items.LEGGINGS)) {
            amount -= 0.2;
        }
        else if (armor.hasTag(TinkerTags.Items.BOOTS)) {
            amount -= 0.1;
        }
        else if (armor.hasTag(TinkerTags.Items.SHIELDS)) {
            amount -= 0.2;
        }

        return amount;
    }

    @Override
    public List<Component> getDescriptionList() {
        if (descriptionList == null) {
            assert Minecraft.getInstance().player != null;
            descriptionList = Arrays.asList(
                Component.translatable(getTranslationKey() + ".flavor", Minecraft.getInstance().player.getDisplayName())
                    .withStyle(ChatFormatting.ITALIC),
                Component.translatable(getTranslationKey() + ".description")
            );
        }

        return descriptionList;
    }

}
