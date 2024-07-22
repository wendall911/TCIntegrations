package tcintegrations.common.capabilities;

import lombok.RequiredArgsConstructor;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
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
import slimeknights.tconstruct.library.modifiers.hook.interaction.BlockInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class EnergyModule implements HookProvider,
        ToolEnergyCapability.EnergyModifierHook,
        TooltipModifierHook,
        VolatileDataModifierHook,
        ValidateModifierHook,
        ModifierRemovalHook,
        DurabilityDisplayModifierHook,
        BlockInteractionModifierHook {
    private static final List<ModuleHook<?>> DEFAULT_HOOKS = HookProvider.<EnergyModule>defaultHooks(
            ToolEnergyCapability.HOOK,
            ModifierHooks.TOOLTIP,
            ModifierHooks.VOLATILE_DATA,
            ModifierHooks.VALIDATE,
            ModifierHooks.REMOVE,
            ModifierHooks.DURABILITY_DISPLAY,
            ModifierHooks.BLOCK_INTERACT
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
        if (tool.getDamage() > 0){
            return null;
        }
        return energyHelper.getEnergy(tool) < energyHelper.getCapacity(tool);  //先判定耐久条再判定能量条
    }

    @Override
    public int getDurabilityWidth(IToolStackView tool, ModifierEntry modifier) {
        if (showDurabilityBar(tool, modifier) == null) {
            return 0;
        }
        int cap = energyHelper.getCapacity(tool);
        return DurabilityDisplayModifierHook.getWidthFor(
                energyHelper.getEnergy(tool)+1,
                cap+1
        );
    }

    @Override
    public int getDurabilityRGB(IToolStackView tool, ModifierEntry modifier) {
        if (showDurabilityBar(tool, modifier) == null) {
            return -1;
        }
        return 0xf44336;
    }

    @Override
    public InteractionResult beforeBlockUse(IToolStackView tool, ModifierEntry modifier, UseOnContext context, InteractionSource source) {
        Level level = context.getLevel();
        if(energyHelper.getCapacity(tool)>0 &&
           level.getBlockEntity(context.getClickedPos()) != null) {
            Direction side = context.getClickedFace();
             LazyOptional<IEnergyStorage> cap = Objects.requireNonNull(level.getBlockEntity(context.getClickedPos()))
                    .getCapability(ForgeCapabilities.ENERGY,side);
            cap.ifPresent(energyHandler -> transferEnergy(context,energyHandler));
            if (cap.isPresent()) {
                return InteractionResult.SUCCESS;
            }
        }
        return InteractionResult.PASS;
    }
    private void transferEnergy(@Nonnull UseOnContext context, @Nonnull IEnergyStorage target)
    {
        context.getItemInHand().getCapability(ForgeCapabilities.ENERGY).ifPresent(e ->
        {
            if (Objects.requireNonNull(context.getPlayer()).isCrouching())
            {
                if (target.canExtract())
                {
                    int diff = e.getMaxEnergyStored() - e.getEnergyStored();
                    int extracted = e.receiveEnergy(target.extractEnergy(diff, false), false);
                    if (!context.getLevel().isClientSide) {
                        context.getPlayer().sendSystemMessage(Component.translatable("message.tcintegrations.energy_extracted", extracted));
                    }
                }
            }
            else
            {
                if (target.canReceive())
                {
                    int diff = e.getEnergyStored();
                    int released = e.extractEnergy(target.receiveEnergy(diff, false), false);
                    if (!context.getLevel().isClientSide) {
                        context.getPlayer().sendSystemMessage(Component.translatable("message.tcintegrations.energy_released", released));
                    }
                }
            }
        });
    }

}
