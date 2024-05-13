package tcintegrations.items.modifiers.traits;

import java.util.Optional;

import org.jetbrains.annotations.Nullable;

import mekanism.api.Action;
import mekanism.api.energy.IStrictEnergyHandler;
import mekanism.api.math.FloatingLong;
import mekanism.common.Mekanism;
import mekanism.common.integration.curios.CuriosIntegration;
import mekanism.common.integration.energy.EnergyCompatUtils;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.IItemHandler;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap.Builder;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class KineticModifier extends NoLevelsModifier implements MeleeHitModifierHook, BlockBreakModifierHook {

    @Override
    protected void registerHooks(Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT, ModifierHooks.BLOCK_BREAK);
    }

    @Override
    public void failedMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageAttempted) {
        final ServerPlayer sp = (ServerPlayer) context.getPlayerAttacker();

        if (sp != null) {
            chargeInventoryItem(sp);
        }
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        final ServerPlayer sp = (ServerPlayer) context.getPlayerAttacker();

        if (sp != null) {
            chargeInventoryItem(sp);
        }
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        final ServerPlayer sp = context.getPlayer();

        if (sp != null && !sp.level.isClientSide) {
            chargeInventoryItem(sp);
        }
    }

    private void chargeInventoryItem(ServerPlayer sp) {
        Optional<IItemHandler> itemHandlerCap = sp.getCapability(ForgeCapabilities.ITEM_HANDLER).resolve();

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
