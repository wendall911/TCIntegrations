package tcintegrations.data.tcon.material;

import net.minecraft.data.DataGenerator;

import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialStatsDataProvider;
import slimeknights.tconstruct.tools.stats.GripMaterialStats;
import slimeknights.tconstruct.tools.stats.HandleMaterialStats;
import slimeknights.tconstruct.tools.stats.HeadMaterialStats;
import slimeknights.tconstruct.tools.stats.LimbMaterialStats;
import slimeknights.tconstruct.tools.stats.PlatingMaterialStats;
import slimeknights.tconstruct.tools.stats.StatlessMaterialStats;

import static net.minecraft.world.item.Tiers.DIAMOND;
import static net.minecraft.world.item.Tiers.IRON;
import static net.minecraft.world.item.Tiers.NETHERITE;
import static net.minecraft.world.item.Tiers.STONE;
import static net.minecraft.world.item.Tiers.WOOD;

import static slimeknights.tconstruct.tools.data.material.MaterialIds.osmium;

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
            HandleMaterialStats.percents().build(),
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.SHIELD_CORE);
        addMaterialStats(MaterialIds.livingRock,
            new HeadMaterialStats(130, 4F, STONE, 1F),
            new LimbMaterialStats(130, 0.05F, 0.05F, -0.1F),
            new GripMaterialStats(1.05F, -0.05F, 0.75F),
            HandleMaterialStats.multipliers().durability(1.2F).attackDamage(1.2F).build(),
            StatlessMaterialStats.BINDING,
            StatlessMaterialStats.SHIELD_CORE);
        
        // tier 2 (mod integration)
        addMaterialStats(MaterialIds.desh,
            new HeadMaterialStats(250, 6F, IRON, 2F),
            new LimbMaterialStats(250, -0.2F, 0.1F, 0),
            new GripMaterialStats(1.1F, 0F, 2F),
            HandleMaterialStats.multipliers().durability(1.10F).build(),
            StatlessMaterialStats.BINDING);
        addMaterialStats(MaterialIds.calorite,
            new HeadMaterialStats(250, 6F, IRON, 2F),
            new LimbMaterialStats(250, -0.2F, 0.1F, 0),
            new GripMaterialStats(1.1F, 0F, 2F),
            HandleMaterialStats.multipliers().durability(1.10F).build(),
            StatlessMaterialStats.BINDING);
        addMaterialStats(MaterialIds.ostrum,
            new HeadMaterialStats(250, 6F, IRON, 2F),
            new LimbMaterialStats(250, -0.2F, 0.1F, 0),
            new GripMaterialStats(1.1F, 0F, 2F),
            HandleMaterialStats.multipliers().durability(1.10F).build(),
            StatlessMaterialStats.BINDING);
        addMaterialStats(osmium,
            new HeadMaterialStats(525, 5.0F, IRON, 2.75F),
            new LimbMaterialStats(525, 0.1F, 0F, 0),
            new GripMaterialStats(1.2F, 0F, 2F),
            HandleMaterialStats.multipliers().durability(1.8F).attackSpeed(1.1F).miningSpeed(1.3F).build(),
            StatlessMaterialStats.BINDING);

        // tier 3 (mod integration)
        addMaterialStats(MaterialIds.manaSteel,
            new HeadMaterialStats(775, 6F, DIAMOND, 2.75F),
            HandleMaterialStats.multipliers().durability(1.05F).miningSpeed(1.05F).attackSpeed(1.05F).build(),
            new LimbMaterialStats(775, -0.3F, 0.2F, -0.1F),
            new GripMaterialStats(1.05F, -0.05F, 2.75F),
            StatlessMaterialStats.BINDING);
        addArmorShieldStats(MaterialIds.manaSteel,
            PlatingMaterialStats.builder()
                .durabilityFactor(29)
                .armor(2, 5, 7, 2)
                .toughness(2),
            StatlessMaterialStats.MAILLE);
        addMaterialStats(MaterialIds.brass,
            new HeadMaterialStats(730, 6F, DIAMOND, 2.25F),
            new LimbMaterialStats(730, -0.2f, 0.15f, -0.2f),
            new GripMaterialStats(1.1f, 0f, 2.25f),
            HandleMaterialStats.multipliers().durability(1.05F).miningSpeed(1.15F).build(),
            StatlessMaterialStats.BINDING);
        addMaterialStats(MaterialIds.neptunium,
            new HeadMaterialStats(765, 7F, DIAMOND, 2.65F),
            HandleMaterialStats.multipliers().durability(1.15F).miningSpeed(1.25F).attackSpeed(1.2F).build(),
            new LimbMaterialStats(765, -0.25F, 0.18F, -0.15F),
            new GripMaterialStats(1.05F, -0.05F, 2.5F),
            StatlessMaterialStats.BINDING);
        addMaterialStats(MaterialIds.soulStainedSteel,
            new HeadMaterialStats(785, 6F, DIAMOND, 2.75F),
            new LimbMaterialStats(785, -0.3F, 0.2F, -0.1F),
            new GripMaterialStats(1.1F, -0.05F, 2.75F),
            HandleMaterialStats.multipliers().durability(1.05F).miningSpeed(1.05F).attackSpeed(1.05F).build(),
            StatlessMaterialStats.BINDING);
        addMaterialStats(MaterialIds.manaString, StatlessMaterialStats.BINDING);

        // tier 4 (mod integration)
        addMaterialStats(MaterialIds.pendoriteAlloy,
            new HeadMaterialStats(1450, 8f, NETHERITE, 3.0F),
            new LimbMaterialStats(1450, 0, -0.20F,0.05F),
            new GripMaterialStats(1.2F, -0.15F, 3.0F),
            HandleMaterialStats.multipliers().durability(1.4F).miningSpeed(1.2F).attackSpeed(1.1F).attackDamage(1.25F).build(),
            StatlessMaterialStats.BINDING);
        addArmorShieldStats(MaterialIds.dragonsteelFire,
            PlatingMaterialStats.builder()
                .durabilityFactor(60)
                .armor(6, 9, 12, 7)
                .toughness(4),
            StatlessMaterialStats.MAILLE);
        addArmorShieldStats(MaterialIds.dragonsteelIce,
            PlatingMaterialStats.builder()
                .durabilityFactor(60)
                .armor(6, 9, 12, 7)
                .toughness(4),
            StatlessMaterialStats.MAILLE);
        addArmorShieldStats(MaterialIds.dragonsteelLightning,
            PlatingMaterialStats.builder()
                .durabilityFactor(60)
                .armor(6, 9, 12, 7)
                .toughness(4),
            StatlessMaterialStats.MAILLE);
    }

}
