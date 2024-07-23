package tcintegrations.common.capabilities;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.capability.CompoundIndexHookIterator;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Iterator;


abstract class EnergyModifierHookIterator<I> extends CompoundIndexHookIterator<ToolEnergyCapability.EnergyModifierHook,I> {
    /* 迭代器迭代到哪个modifier了 ({@link #findHook(IToolStackView, int)}) */
    protected ModifierEntry indexEntry = null;


    @Override
    protected int getSize(IToolStackView tool, ToolEnergyCapability.EnergyModifierHook hook) {
        return hook.getCellCount(tool.getVolatileData(), indexEntry);
    }


    protected int receiveEnergy(IToolStackView tool,  int maxReceive, boolean simulate) {
        int received = 0;
        Iterator<I> iterator = getIterator(tool);
        while (iterator.hasNext()) {
            I entry = iterator.next();
            int toReceive = getHook(entry).receiveEnergy(tool, indexEntry, maxReceive - received, simulate);
            if (toReceive > 0) {
                received += toReceive;
                if (received >= maxReceive) {
                    break;
                }
            }
        }
        return received;
    }

    public int extractEnergy(IToolStackView tool, int maxExtract, boolean simulate) {
        int extracted = 0;
        Iterator<I> iterator = getIterator(tool);
        while (iterator.hasNext()) {
            I entry = iterator.next();
            int toExtract = getHook(entry).extractEnergy(tool, indexEntry, maxExtract - extracted, simulate);
            if (toExtract > 0) {
                extracted += toExtract;
                if (extracted >= maxExtract) {
                    break;
                }
            }
        }
        return extracted;
    }
}
