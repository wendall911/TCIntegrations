package tcintegrations.common.capabilities;

import lombok.RequiredArgsConstructor;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.Nullable;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ModifierRemovalHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ValidateModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.DurabilityDisplayModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import java.util.List;

@RequiredArgsConstructor
public class EnergyModule implements HookProvider, ToolEnergyCapability.EnergyModifierHook, TooltipModifierHook, VolatileDataModifierHook, ValidateModifierHook, ModifierRemovalHook, DurabilityDisplayModifierHook {
    private static final List<ModuleHook<?>> DEFAULT_HOOKS = HookProvider.<EnergyModule>defaultHooks(
            ToolEnergyCapability.HOOK,
            ModifierHooks.TOOLTIP,
            ModifierHooks.VOLATILE_DATA,
            ModifierHooks.VALIDATE,
            ModifierHooks.REMOVE,
            ModifierHooks.DURABILITY_DISPLAY
    );
    @Override
    public List<ModuleHook<?>> getDefaultHooks() {
        return DEFAULT_HOOKS;
    }
    private final ToolEnergyHelper energyHelper;
    @Nullable
    @Override
    public Component onRemoved(IToolStackView tool, Modifier modifier) {
        energyHelper.setEnergy(tool,0);
        return null;
    }

    @Nullable
    @Override
    public Component validate(IToolStackView tool, ModifierEntry modifier) {
        if (energyHelper.getEnergy(tool) > energyHelper.getCapacity(tool)) {
            energyHelper.setEnergy(tool,energyHelper.getCapacity(tool));
        }
        return null;
    }

    @Override
    public void addVolatileData(IToolContext context, ModifierEntry modifier, ModDataNBT volatileData) {
        ToolEnergyCapability.addCells(modifier, volatileData,this);
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        int currentEnergy =energyHelper.getEnergy(tool);
        tooltip.add(Component.translatable("tooltip.tcintegrations.energy_cap",currentEnergy,energyHelper.getCapacity(tool)).withStyle(ChatFormatting.GREEN));
        tooltip.add(energyHelper.getCapacityStat().formatValue(energyHelper.getCapacity(tool)));
    }


    @Override
    public int receiveEnergy(IToolStackView tool, ModifierEntry modifier, int maxReceive, boolean simulate) {
        int energy = energyHelper.getEnergy(tool);
        int capacity = energyHelper.getCapacity(tool);
        int toReceive = Math.min(maxReceive, capacity - energy);
        if (!simulate) {
            energyHelper.setEnergy(tool,energy + toReceive);
        }
        return toReceive;
    }

    @Override
    public int extractEnergy(IToolStackView tool, ModifierEntry modifier, int maxExtract, boolean simulate) {
        int energy = energyHelper.getEnergy(tool);
        int toExtract = Math.min(maxExtract, energy);
        if (!simulate) {
            energyHelper.setEnergy(tool,energy - toExtract);
        }
        return toExtract;
    }
    @Override
    public int getEnergy(IToolStackView tool, ModifierEntry modifier, int cellIndex) {
        return energyHelper.getEnergy(tool);
    }
    @Override
    public int getCellCapacity(IToolStackView tool, ModifierEntry modifier, int cellIndex) {
        return energyHelper.getCapacity(tool);
    }
    @Override
    public Boolean showDurabilityBar(IToolStackView tool, ModifierEntry modifier) {
        return energyHelper.getEnergy(tool) < energyHelper.getCapacity(tool);
    }

    @Override
    public int getDurabilityWidth(IToolStackView tool, ModifierEntry modifier) {
        int cap = energyHelper.getCapacity(tool);
        return DurabilityDisplayModifierHook.getWidthFor(
                energyHelper.getEnergy(tool)+1,
                cap+1
        );
    }

    @Override
    public int getDurabilityRGB(IToolStackView tool, ModifierEntry modifier) {
        return 0xf44336;
    }

}
