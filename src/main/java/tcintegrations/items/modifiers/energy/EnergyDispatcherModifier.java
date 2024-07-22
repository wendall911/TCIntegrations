package tcintegrations.items.modifiers.energy;

import com.google.common.collect.Iterables;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.item.IModifiable;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import tcintegrations.common.capabilities.ToolEnergyHelper;

import java.util.HashSet;
import java.util.Set;

public class EnergyDispatcherModifier extends EnergyInventoryTickModifier{

    @Override
    public int getCapacityEachLevel() {
        return 500000;
    }

    @Override
    protected void spInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, ServerPlayer holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        double remainingEnergy = 0;
        double totalCap = 0;
        Set<IToolStackView> toolsWithEnergy = new HashSet<>();
        Inventory inv = holder.getInventory();
        for(ItemStack itemStack : Iterables.concat(inv.items, inv.armor, inv.offhand)){ // Iterate over all items in the player's inventory
            if(itemStack.getItem() instanceof IModifiable &&
               ToolEnergyHelper.ENERGY_HELPER.getCapacity(ToolStack.from(itemStack)) > 0){
                toolsWithEnergy.add(ToolStack.from(itemStack));
                remainingEnergy += ToolEnergyHelper.ENERGY_HELPER.getEnergy(ToolStack.copyFrom(itemStack));
                totalCap += ToolEnergyHelper.ENERGY_HELPER.getCapacity(ToolStack.from(itemStack));
            }
        }
        if(toolsWithEnergy.size() == 0 || (remainingEnergy / totalCap) > 0.99995f){
            return; // No tools with energy or all tools are (almost) full
        }
        for(IToolStackView toolWithEnergy : toolsWithEnergy){
            ToolEnergyHelper.ENERGY_HELPER.setEnergy(toolWithEnergy,
                    (int) ((float) ToolEnergyHelper.ENERGY_HELPER.getCapacity(toolWithEnergy) * (remainingEnergy / totalCap)));
            // Distribute the remaining energy to make all tools have the same percentage of energy
        }
    }

}
