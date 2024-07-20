package tcintegrations.data;

import java.util.Arrays;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.Tags;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.shared.TinkerMaterials;

import tcintegrations.common.TagManager;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.TCIntegrations;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagsProvider, TCIntegrations.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "TCIntegrations - Item Tags";
    }

    @Override
    protected void addTags() {
        this.tag(TagManager.Items.BATTERY_ITEMS)
            .addOptional(ModIntegration.mekanismLoc("energy_tablet"))
                .addOptional(ModIntegration.ieLoc("capacitor_lv"))
                .addOptional(ModIntegration.ieLoc("capacitor_mv"))
                .addOptional(ModIntegration.ieLoc("capacitor_hv"))
                .addOptional(ModIntegration.ieLoc("capacitor_creative"));

        this.tag(TagManager.Items.SOLAR_PANEL_ITEMS)
            .addOptional(ModIntegration.beyondEarthLoc("solar_panel"))
            .addOptional(ModIntegration.adAstraLoc("solar_panel"))
                .addOptional(ModIntegration.mekgenLoc("advanced_solar_generator"))
            .addOptional(ModIntegration.mekgenLoc("solar_generator"));

        this.copy(TagManager.Blocks.BRONZE, TagManager.Items.BRONZE);
        copy(TinkerTags.Blocks.ANVIL_METAL, TinkerTags.Items.ANVIL_METAL);

        this.tag(Tags.Items.INGOTS)
            .add(TCIntegrationsItems.BRONZE.getIngot())
            .addOptional(ModIntegration.malumLoc("soul_stained_steel_ingot"));
        this.tag(Tags.Items.NUGGETS)
            .add(TCIntegrationsItems.BRONZE.getNugget())
            .addOptional(ModIntegration.malumLoc("soul_stained_steel_nugget"));

        getBuilder(TagManager.Items.BRONZE_INGOTS).add(TCIntegrationsItems.BRONZE.getIngot());
        getBuilder(TagManager.Items.BRONZE_NUGGETS).add(TCIntegrationsItems.BRONZE.getNugget());

        // Botania
        addBotaniaLogVariants(TagManager.Items.BOTANIA_LIVINGWOOD_LOGS, "livingwood");
        this.tag(TinkerTags.Items.VARIANT_LOGS).addOptionalTag(TagManager.Items.BOTANIA_LIVINGWOOD_LOGS.location());
        this.tag(TinkerTags.Items.VARIANT_PLANKS).addOptional(ModIntegration.botaniaLoc("livingwood_planks"));

        // Malum
        this.copy(TagManager.Blocks.SOUL_STAINED_STEEL, TagManager.Items.SOUL_STAINED_STEEL);
        getBuilder(TagManager.Items.SOUL_STAINED_STEEL_INGOTS).addOptional(ModIntegration.malumLoc("soul_stained_steel_ingot"));
        getBuilder(TagManager.Items.SOUL_STAINED_STEEL_NUGGETS).addOptional(ModIntegration.malumLoc("soul_stained_steel_nugget"));

        // BYG
        getBuilder(TagManager.Items.EMERALDITE_SHARDS)
            .addOptional(ModIntegration.bygLoc("emeraldite_shards"));
        getBuilder(TagManager.Items.EMERALDITE_ORE)
            .addOptional(ModIntegration.bygLoc("emeraldite_ore"));
        getBuilder(TagManager.Items.PENDORITE_ALLOY_INGOTS)
            .addOptional(ModIntegration.bygLoc("pendorite_ingot"));

        // Ice and Fire: Dragons
        getBuilder(TagManager.Items.WITHER_BONES)
            .add(TinkerMaterials.necroticBone.get())
            .addOptional(ModIntegration.ifdLoc("witherbone"));
        this.copy(TagManager.Blocks.DRAGONSTEEL_FIRE, TagManager.Items.DRAGONSTEEL_FIRE);
        getBuilder(TagManager.Items.DRAGONSTEEL_FIRE_INGOTS).addOptional(ModIntegration.ifdLoc("dragonsteel_fire_ingot"));
        this.copy(TagManager.Blocks.DRAGONSTEEL_ICE, TagManager.Items.DRAGONSTEEL_ICE);
        getBuilder(TagManager.Items.DRAGONSTEEL_ICE_INGOTS).addOptional(ModIntegration.ifdLoc("dragonsteel_ice_ingot"));
        this.copy(TagManager.Blocks.DRAGONSTEEL_LIGHTNING, TagManager.Items.DRAGONSTEEL_LIGHTNING);
        getBuilder(TagManager.Items.DRAGONSTEEL_LIGHTNING_INGOTS).addOptional(ModIntegration.ifdLoc("dragonsteel_lightning_ingot"));

        // Ad Astra & Beyond Earth
        getBuilder(TagManager.Items.CHEESE)
            .addOptional(ModIntegration.adAstraLoc("cheese"))
            .addOptional(ModIntegration.beyondEarthLoc("cheese"));

        // Ars Nouveau
        getBuilder(TagManager.Items.SOURCE_GEM)
            .add(ModIntegration.SOURCE_GEM);
        getBuilder(TagManager.Items.SOURCE_GEM_BLOCK)
            .add(ModIntegration.SOURCE_GEM_BLOCK);
    }

    private void addBotaniaLogVariants(TagKey<Item> tag, String type) {
        getBuilder(tag)
            .addOptional(ModIntegration.botaniaLoc(type + "_log"))
            .addOptional(ModIntegration.botaniaLoc("stripped_" + type + "_log"))
            .addOptional(ModIntegration.botaniaLoc(type))
            .addOptional(ModIntegration.botaniaLoc("stripped_" + type));
    }

    private void builder(TagKey<Item> tag) {
        getBuilder(tag);
    }

    private void builder(TagKey<Item> tag, ItemLike... items) {
        getBuilder(tag).add(Arrays.stream(items).map(ItemLike::asItem).toArray(Item[]::new));
    }

    protected TagsProvider.TagAppender<Item> getBuilder(TagKey<Item> tag) {
        return tag(tag);
    }

}
