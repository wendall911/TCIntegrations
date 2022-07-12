package tcintegrations.client.compat;

import com.simibubi.create.content.contraptions.goggles.GogglesItem;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import tcintegrations.items.armor.modifiers.EngineersGogglesModifier;

public class Create {

    public static void init() {
        GogglesItem.addIsWearingPredicate(player -> {
            ItemStack stack = player.getItemBySlot(EquipmentSlot.HEAD);

            if (stack.isEmpty()) return false;

            CompoundTag tags = stack.getTag();

            return tags != null && tags.contains(EngineersGogglesModifier.CREATE_GOGGLES);
        });
    }

}
