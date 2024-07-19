package tcintegrations.common.capabilities;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.utils.Util;
public class EnergyCapacityStat extends FloatToolStat{


    public EnergyCapacityStat(ToolStatId name, int color, int defaultValue, int maxValue, @Nullable TagKey<Item> tag) {
        super(name,color,defaultValue,0,maxValue);
    }

    public EnergyCapacityStat(ToolStatId name, int color, int defaultValue, int maxValue){
        this(name,color,defaultValue,maxValue,null);
    }

    @Override
    public Component formatValue(float value) {
        return Component.translatable(getTranslationKey())
                .append(Component.translatable("tooltip.tcintegrations.FE", Util.COMMA_FORMAT.format(value))
                        .withStyle(style -> style.withColor(TextColor.fromRgb(0xffffee))));
    }
}
