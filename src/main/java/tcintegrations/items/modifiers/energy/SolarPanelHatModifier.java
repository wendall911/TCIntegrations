package tcintegrations.items.modifiers.energy;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import tcintegrations.common.capabilities.ToolEnergyHelper;

public class SolarPanelHatModifier extends Modifier implements InventoryTickModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK);
        hookBuilder.addModule(ToolEnergyHelper.ENERGY_HANDLER);
        hookBuilder.addModule(StatBoostModule.add(ToolEnergyHelper.ENERGY_CAPACITY_STAT).eachLevel(500000));
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        final Player player = holder instanceof Player ? (Player) holder : null;
        if (player != null && !player.level.isClientSide &&
                world.isDay() &&
                world.canSeeSky(holder.blockPosition()) &&
                isCorrectSlot) {
            int energyToGen = (int) Math.pow(10, Math.min(modifier.getLevel(), 5)) * 2;
            ToolEnergyHelper.ENERGY_HELPER.setEnergy(tool,
                    Math.min(ToolEnergyHelper.ENERGY_HELPER.getEnergy(tool) + energyToGen,
                            ToolEnergyHelper.ENERGY_HELPER.getCapacity(tool)));
        }
    }
}

