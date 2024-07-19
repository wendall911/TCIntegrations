package tcintegrations.common.capabilities;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.capability.CompoundIndexHookIterator;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.util.Iterator;

/**
 * 用于迭代每一个 {@link ToolEnergyCapability} 的迭代器
 * 迭代器只管能量进来和出去
 * @param <I>
 */
abstract class EnergyModifierHookIterator<I> extends CompoundIndexHookIterator<ToolEnergyCapability.EnergyModifierHook,I> {
    /* 迭代器迭代到哪个modifier了 ({@link #findHook(IToolStackView, int)}) */
    protected ModifierEntry indexEntry = null;


    @Override
    protected int getSize(IToolStackView tool, ToolEnergyCapability.EnergyModifierHook hook) {
        return hook.getCellCount(tool.getVolatileData(), indexEntry);
    }

    /**
     * 用迭代器接受能量，这个cell满了换下一个
     * @param tool 工具
     * @param maxReceive 最大接受量
     * @param simulate 是否模拟
     * @return 接受了（或者可能会接受）的量
     */
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
    /**
     * 用迭代器提取能量，这个cell空了换下一个
     * @param tool 工具
     * @param maxExtract 最大提取量
     * @param simulate 是否模拟
     * @return 提取了（或者可能会提取）的量
     */
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
