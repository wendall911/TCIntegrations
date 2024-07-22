package tcintegrations.items.modifiers.energy;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import tcintegrations.common.capabilities.ToolEnergyHelper;

public class SolarPanelHatModifier extends EnergyInventoryTickModifier {

    @Override
    public int getCapacityEachLevel() {
        return 500000;
    }

    @Override
    protected void spInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, ServerPlayer holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (world.isDay() &&
                world.canSeeSky(holder.blockPosition()) &&
                isCorrectSlot) {
            int energyToGen = (int) Math.pow(10, Math.min(modifier.getLevel(), 4)) * 2;// 20, 200, 2000, 20000 RF/tick
            ToolEnergyHelper.ENERGY_HELPER.setEnergy(tool,
                    Math.min(ToolEnergyHelper.ENERGY_HELPER.getEnergy(tool) + energyToGen,
                            ToolEnergyHelper.ENERGY_HELPER.getCapacity(tool)));
        }
    }


}

