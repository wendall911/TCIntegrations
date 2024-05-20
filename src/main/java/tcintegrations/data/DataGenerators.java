package tcintegrations.data;

import net.minecraft.data.DataGenerator;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import slimeknights.tconstruct.fluids.data.FluidBlockstateModelProvider;

import slimeknights.tconstruct.fluids.data.FluidBucketModelProvider;
import tcintegrations.data.client.ModBlockStateProvider;
import tcintegrations.data.client.ModItemModelProvider;
import tcintegrations.data.integration.ProjectEConversionProvider;
import tcintegrations.data.loot.ModLootTables;
import tcintegrations.data.recipes.ModRecipesProvider;
import tcintegrations.data.tcon.fluid.FluidTagProvider;
import tcintegrations.data.tcon.fluid.FluidTextureProvider;
import tcintegrations.data.tcon.ModifierProvider;
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
        boolean server = event.includeServer();
        boolean client = event.includeClient();

        gen.addProvider(server, new ModItemModelProvider(gen, existingFileHelper));
        gen.addProvider(server, new ModBlockStateProvider(gen, existingFileHelper));
        gen.addProvider(server, blockTags);
        gen.addProvider(server, new ModItemTagsProvider(gen, blockTags, existingFileHelper));
        gen.addProvider(server, new FluidTagProvider(gen, existingFileHelper));
        gen.addProvider(server, new ModifierRecipeProvider(gen));
        gen.addProvider(server, new ModRecipesProvider(gen));
        gen.addProvider(server, new ModLootTables(gen));
        gen.addProvider(client, new MaterialRenderInfoProvider(gen, materialSprites, existingFileHelper));
        gen.addProvider(server, new MaterialStatsDataProvider(gen, materials));
        gen.addProvider(server, new MaterialTraitsDataProvider(gen, materials));
        gen.addProvider(server, new MaterialRecipeProvider(gen));
        gen.addProvider(server, new SmelteryRecipeProvider(gen));
        gen.addProvider(server, materials);
        gen.addProvider(server, new EntityTypeTagProvider(gen, existingFileHelper));
        gen.addProvider(server, new ProjectEConversionProvider(gen));
        gen.addProvider(server, new ModifierProvider(gen));
        gen.addProvider(client, new FluidTextureProvider(gen));
        gen.addProvider(client, new FluidBlockstateModelProvider(gen, TCIntegrations.MODID));
        gen.addProvider(client, new FluidBucketModelProvider(gen, TCIntegrations.MODID));
    }

}