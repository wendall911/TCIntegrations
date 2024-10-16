package tcintegrations.client.compat;

import com.simibubi.create.content.equipment.goggles.GogglesItem;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

//import tcintegrations.items.modifiers.armor.EngineersGogglesModifier;

public class Create {

    public static void init() {
        GogglesItem.addIsWearingPredicate(player -> {
            ItemStack stack = player.getItemBySlot(EquipmentSlot.HEAD);

            if (stack.isEmpty()) return false;

            CompoundTag tags = stack.getTag();

            return false; //return tags != null && tags.contains(EngineersGogglesModifier.CREATE_GOGGLES);
        });
    }

}
