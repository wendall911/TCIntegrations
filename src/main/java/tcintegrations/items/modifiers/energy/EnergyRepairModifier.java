package tcintegrations.items.modifiers.energy;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import tcintegrations.common.capabilities.ToolEnergyHelper;

public class EnergyRepairModifier extends EnergyInventoryTickModifier{
    private static final int FE_PER_DAMAGE = 2500;
    public static int getFePerDamage(){
        return FE_PER_DAMAGE;
    }

    @Override
    public int getCapacityEachLevel() {
        return 250000;
    }

    @Override
    protected void serverPlayerTick(IToolStackView tool, ModifierEntry modifier, Level world, ServerPlayer holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (holder.tickCount % 200 != 0){
            return;
        }
        for(int counter = 0; counter < modifier.getLevel(); counter++){
            if (tool.getDamage() > 0
                    && ToolEnergyHelper.ENERGY_HELPER.getEnergy(tool) >= getFePerDamage()){
                ToolEnergyHelper.ENERGY_HELPER.setEnergy(tool,ToolEnergyHelper.ENERGY_HELPER.getEnergy(tool) - getFePerDamage());
                tool.setDamage(tool.getDamage() - 1);
            }
        }
    }


}
