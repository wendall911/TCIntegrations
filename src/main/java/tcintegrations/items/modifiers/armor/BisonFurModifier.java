package tcintegrations.items.modifiers.armor;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class BisonFurModifier extends NoLevelsModifier implements EquipmentChangeModifierHook {

    @Override
    public void onEquip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        final Player player = context.getEntity() instanceof Player ? (Player) context.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            ItemStack replacement = context.getReplacement();
            CompoundTag tag = replacement.getOrCreateTag();

            tag.putBoolean("BisonFur", true);
            replacement.setTag(tag);
        }
    }

}
