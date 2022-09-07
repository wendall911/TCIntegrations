package tcintegrations.items.modifiers.traits;

import javax.annotation.Nullable;
import java.util.Optional;

import mekanism.api.Action;
import mekanism.api.energy.IStrictEnergyHandler;
import mekanism.api.math.FloatingLong;
import mekanism.common.Mekanism;
import mekanism.common.integration.curios.CuriosIntegration;
import mekanism.common.integration.energy.EnergyCompatUtils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class KineticModifier extends NoLevelsModifier {

    @Override
    public void failedEntityHit(IToolStackView tool, int level, ToolAttackContext context) {
        final ServerPlayer sp = (ServerPlayer) context.getPlayerAttacker();

        if (sp != null) {
            chargeInventoryItem(sp);
        }
    }

    @Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        final ServerPlayer sp = (ServerPlayer) context.getPlayerAttacker();

        if (sp != null) {
            chargeInventoryItem(sp);
        }

        return 0;
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, int level, ToolHarvestContext context) {
        final ServerPlayer sp = context.getPlayer();

        if (sp != null && !sp.level.isClientSide) {
            chargeInventoryItem(sp);
        }
    }

    private void chargeInventoryItem(ServerPlayer sp) {
        Optional<IItemHandler> itemHandlerCap = sp.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).resolve();

        if (!chargeHandler(itemHandlerCap) && Mekanism.hooks.CuriosLoaded) {
            chargeHandler(CuriosIntegration.getCuriosInventory(sp));
        }
    }

    private boolean chargeHandler(Optional<? extends IItemHandler> itemHandlerCap) {
        if (itemHandlerCap.isPresent()) {
            IItemHandler itemHandler = itemHandlerCap.get();
            int slots = itemHandler.getSlots();

            for (int slot = 0; slot < slots; slot++) {
                ItemStack stack = itemHandler.getStackInSlot(slot);

                if (!stack.isEmpty() && provideEnergy(EnergyCompatUtils.getStrictEnergyHandler(stack))) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean provideEnergy(@Nullable IStrictEnergyHandler energyHandler) {
        if (energyHandler == null) return false;

        FloatingLong energyToGive = FloatingLong.create(20);

        energyHandler.insertEnergy(energyToGive, Action.EXECUTE);

        return true;
    }

}
