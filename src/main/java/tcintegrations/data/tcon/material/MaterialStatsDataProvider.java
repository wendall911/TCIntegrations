package tcintegrations.data.tcon.material;

import net.minecraft.data.DataGenerator;

import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.tools.stats.ExtraMaterialStats;
import slimeknights.tconstruct.tools.stats.GripMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;
import slimeknights.tconstruct.tools.stats.LimbMaterialStats;

import static net.minecraft.world.item.Tiers.NETHERITE;
import static slimeknights.tconstruct.tools.data.material.MaterialIds.brass;
import static slimeknights.tconstruct.tools.data.material.MaterialIds.osmium;

import static net.minecraft.world.item.Tiers.DIAMOND;
import static net.minecraft.world.item.Tiers.IRON;
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
            new LimbMaterialStats(60, 0, 0,0),
            new GripMaterialStats(1.0F, 0, 0),
            HandleMaterialStats.DEFAULT,
            ExtraMaterialStats.DEFAULT);
        addMaterialStats(MaterialIds.livingRock,
            new HeadMaterialStats(130, 4F, STONE, 1F),
            HandleMaterialStats.DEFAULT.withDurability(1.2F).withAttackDamage(1.2F),
            ExtraMaterialStats.DEFAULT);
        
        // tier 2 (mod integration)
        addMaterialStats(MaterialIds.desh,
            new HeadMaterialStats(250, 6F, IRON, 2F),
            HandleMaterialStats.DEFAULT.withDurability(1.10F),
            ExtraMaterialStats.DEFAULT);
        addMaterialStats(MaterialIds.calorite,
            new HeadMaterialStats(250, 6F, IRON, 2F),
            HandleMaterialStats.DEFAULT.withDurability(1.10F),
            ExtraMaterialStats.DEFAULT);
        addMaterialStats(MaterialIds.ostrum,
            new HeadMaterialStats(250, 6F, IRON, 2F),
            HandleMaterialStats.DEFAULT.withDurability(1.10F),
            ExtraMaterialStats.DEFAULT);
        addMaterialStats(osmium,
            new HeadMaterialStats(525, 5.0F, IRON, 2.75F),
            HandleMaterialStats.DEFAULT.withDurability(1.8F).withAttackSpeed(1.1F).withMiningSpeed(1.3F),
            ExtraMaterialStats.DEFAULT);

        // tier 3 (mod integration)
        addMaterialStats(MaterialIds.manaSteel,
            new HeadMaterialStats(775, 6F, DIAMOND, 2.75F),
            HandleMaterialStats.DEFAULT.withDurability(1.05F).withMiningSpeed(1.05F).withAttackSpeed(1.05F),
            new LimbMaterialStats(775, -0.3f, 0.2f, -0.1f),
            new GripMaterialStats(1.05f, -0.05f, 2.75f),
            ExtraMaterialStats.DEFAULT);
        addMaterialStats(brass,
            new HeadMaterialStats(730, 6F, DIAMOND, 2.25F),
            HandleMaterialStats.DEFAULT.withDurability(1.05F).withMiningSpeed(1.15F),
            ExtraMaterialStats.DEFAULT);
        addMaterialStats(MaterialIds.neptunium,
            new HeadMaterialStats(765, 7F, DIAMOND, 2.65F),
            HandleMaterialStats.DEFAULT.withDurability(1.15F).withMiningSpeed(1.25F).withAttackSpeed(1.2F),
            ExtraMaterialStats.DEFAULT);
        addMaterialStats(MaterialIds.soulStainedSteel,
            new HeadMaterialStats(785, 6F, DIAMOND, 2.75F),
            HandleMaterialStats.DEFAULT.withDurability(1.05F).withMiningSpeed(1.05F).withAttackSpeed(1.05F),
            ExtraMaterialStats.DEFAULT);

        // tier 4 (mod integration)
        addMaterialStats(MaterialIds.pendoriteAlloy,
            new HeadMaterialStats(1450, 8f, NETHERITE, 3.0F),
            HandleMaterialStats.DEFAULT.withDurability(1.4F).withMiningSpeed(1.2F).withAttackSpeed(1.1F).withAttackDamage(1.25F),
            ExtraMaterialStats.DEFAULT);
    }

}
