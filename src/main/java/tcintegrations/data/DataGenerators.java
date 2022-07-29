package tcintegrations.data;

import net.minecraft.data.DataGenerator;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import tcintegrations.data.client.ModBlockStateProvider;
import tcintegrations.data.client.ModItemModelProvider;
import tcintegrations.data.integration.ProjectEConversionProvider;
import tcintegrations.data.loot.ModLootTables;
import tcintegrations.data.recipes.ModRecipesProvider;
import tcintegrations.data.tcon.FluidTagProvider;
import tcintegrations.data.tcon.ModifierRecipeProvider;
import tcintegrations.data.tcon.material.MaterialDataProvider;
import tcintegrations.data.tcon.material.MaterialRecipeProvider;
import tcintegrations.data.tcon.material.MaterialRenderInfoProvider;
import tcintegrations.data.tcon.material.MaterialStatsDataProvider;
import tcintegrations.data.tcon.material.MaterialTraitsDataProvider;
import tcintegrations.data.tcon.sprite.TinkerMaterialSpriteProvider;
import tcintegrations.data.tcon.SmelteryRecipeProvider;
import tcintegrations.TCIntegrations;

@Mod.EventBusSubscriber(modid = TCIntegrations.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class DataGenerators {

    private DataGenerators() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, existingFileHelper);
        TinkerMaterialSpriteProvider materialSprites = new TinkerMaterialSpriteProvider();
        MaterialDataProvider materials = new MaterialDataProvider(gen);

        gen.addProvider(new ModItemModelProvider(gen, existingFileHelper));
        gen.addProvider(new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(blockTags);
        gen.addProvider(new ModItemTagsProvider(gen, blockTags, existingFileHelper));
        gen.addProvider(new FluidTagProvider(gen, existingFileHelper));
        gen.addProvider(new ModifierRecipeProvider(gen));
        gen.addProvider(new ModRecipesProvider(gen));
        gen.addProvider(new ModLootTables(gen));
        gen.addProvider(new MaterialRenderInfoProvider(gen, materialSprites));
        gen.addProvider(new MaterialStatsDataProvider(gen, materials));
        gen.addProvider(new MaterialTraitsDataProvider(gen, materials));
        gen.addProvider(new MaterialRecipeProvider(gen));
        gen.addProvider(new SmelteryRecipeProvider(gen));
        gen.addProvider(materials);
        gen.addProvider(new EntityTypeTagProvider(gen, existingFileHelper));
        gen.addProvider(new ProjectEConversionProvider(gen));
    }

}
