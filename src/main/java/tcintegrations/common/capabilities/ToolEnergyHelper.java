package tcintegrations.common.capabilities;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.modifiers.modules.build.ModifierTraitModule;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.INumericToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import tcintegrations.TCIntegrations;
import tcintegrations.items.TCIntegrationsModifiers;

/**
 * 与slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper类似，
 * 这个类用于处理能量相关的事情。
 * This class is used to help with energy related things.
 * 处理能量尽量都用默认helper，这样可以保证兼容性
 */
@Getter
@RequiredArgsConstructor
public class ToolEnergyHelper {
    /**
     * 默认helper的能量容量属性，存储在工具nbt中的tic_stats
     * 如果以stats形式存储容量大小，那应该要把每个ability或者modifier
     * 的容量属性修改写进datagen中
     */
    public static final EnergyCapacityStat ENERGY_CAPACITY_STAT =
            ToolStats.register(new EnergyCapacityStat(
                    new ToolStatId(TCIntegrations.MODID,"energy_capacity"),
                    0xffefbe,
                    0,
                    Integer.MAX_VALUE
            ));
    /* 能量存储默认helper，用于处理能量的存储，将会把能量数据存储在工具nbt中的tic_persistent */
    public static final ToolEnergyHelper ENERGY_HELPER = new ToolEnergyHelper(
            ENERGY_CAPACITY_STAT, new ResourceLocation(TCIntegrations.MODID, "energy")
    );
    /* 打上这个modifier你就是富含fe的工具了 */
    public static final ModifierModule ENERGY_HANDLER = new ModifierTraitModule(
            TCIntegrationsModifiers.ENERGY_HANDLER.getId(),
            1,
            true
    );
    /* 能量容量属性 */
    private final INumericToolStat<?> capacityStat;
    /* helper的注册键 */
    private final ResourceLocation key;

    public int getEnergy(IToolStackView tool) {
        return tool.getPersistentData().getInt(key);
    }
    public int getCapacity(IToolStackView tool) {
        return tool.getStats().getInt(capacityStat);
    }
    public void setEnergy(IToolStackView tool, int energy) {
        tool.getPersistentData().putInt(key, energy);
    }
}
