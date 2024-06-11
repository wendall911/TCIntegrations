package tcintegrations.data.integration;

import com.simibubi.create.content.equipment.goggles.GogglesItem;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import tcintegrations.items.modifiers.armor.EngineersGogglesModifier;

public class CreateGogglesPredicate {

    public static void init() {
        GogglesItem.addIsWearingPredicate(player -> {
            ItemStack helmet = player.getItemBySlot(EquipmentSlot.HEAD);
            CompoundTag tags = helmet.getTag();

            return tags != null && tags.contains(EngineersGogglesModifier.CREATE_GOGGLES);
        });
    }

}
