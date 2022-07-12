package tcintegrations.data.tcon.material;

import net.minecraft.data.DataGenerator;

import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;

import static slimeknights.tconstruct.tools.data.material.MaterialIds.brass;

import static net.minecraft.world.item.Tiers.DIAMOND;
import static net.minecraft.world.item.Tiers.STONE;
import static net.minecraft.world.item.Tiers.WOOD;

public class MaterialStatsDataProvider extends AbstractMaterialStatsDataProvider {

    public MaterialStatsDataProvider(DataGenerator gen, AbstractMaterialDataProvider materials) {
        super(gen, materials);
    }

    @Override
    public String getName() {
        return "TCIntegrations - TCon Material Stats";
    }

    @Override
    protected void addMaterialStats() {
        // head order is durability, mining speed, mining level, damage

        // tier 1 (mod integration)
        addMaterialStats(MaterialIds.livingWood,
                new HeadMaterialStats(60, 2F, WOOD,0F),
                HandleMaterialStats.DEFAULT,
                ExtraMaterialStats.DEFAULT);
        addMaterialStats(MaterialIds.livingRock,
                new HeadMaterialStats(130, 4F, STONE, 1F),
                HandleMaterialStats.DEFAULT.withDurability(1.2F).withAttackDamage(1.2F),
                ExtraMaterialStats.DEFAULT);

        // tier 3 (mod integration)
        addMaterialStats(MaterialIds.manaSteel,
                new HeadMaterialStats(775, 6F, DIAMOND, 2.75F),
                HandleMaterialStats.DEFAULT.withDurability(1.05F).withMiningSpeed(1.05F).withAttackSpeed(1.05F),
                ExtraMaterialStats.DEFAULT);
        addMaterialStats(brass,
                new HeadMaterialStats(730, 6.0F, DIAMOND, 2.25F),
                HandleMaterialStats.DEFAULT.withDurability(1.05F).withMiningSpeed(1.15F),
                ExtraMaterialStats.DEFAULT);
    }

}
