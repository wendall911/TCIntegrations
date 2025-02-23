package tcintegrations.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
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
import tcintegrations.data.tcon.EnchantmentToModifierProvider;
import tcintegrations.data.tcon.ModifierTagProvider;
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
        PackOutput packOutput = gen.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(packOutput, event.getLookupProvider(), existingFileHelper);
        TinkerMaterialSpriteProvider materialSprites = new TinkerMaterialSpriteProvider();
        MaterialDataProvider materials = new MaterialDataProvider(packOutput);
        boolean server = event.includeServer();
        boolean client = event.includeClient();

        gen.addProvider(server, new ModItemModelProvider(packOutput, existingFileHelper));
        gen.addProvider(server, new ModBlockStateProvider(packOutput, existingFileHelper));
        gen.addProvider(server, blockTags);
        gen.addProvider(server, new ModItemTagsProvider(packOutput, event.getLookupProvider(), blockTags, existingFileHelper));
        gen.addProvider(server, new FluidTagProvider(packOutput, event.getLookupProvider(), existingFileHelper));
        gen.addProvider(server, new ModifierRecipeProvider(packOutput));
        gen.addProvider(server, new ModRecipesProvider(packOutput));
        gen.addProvider(server, ModLootTables.create(packOutput));
        gen.addProvider(client, new MaterialRenderInfoProvider(packOutput, materialSprites, existingFileHelper));
        gen.addProvider(server, new MaterialStatsDataProvider(packOutput, materials));
        gen.addProvider(server, new MaterialTraitsDataProvider(packOutput, materials));
        gen.addProvider(server, new MaterialRecipeProvider(packOutput));
        gen.addProvider(server, new SmelteryRecipeProvider(packOutput));
        gen.addProvider(server, materials);
        gen.addProvider(server, new EntityTypeTagProvider(packOutput, event.getLookupProvider(), existingFileHelper));
        gen.addProvider(server, new ProjectEConversionProvider(packOutput, event.getLookupProvider()));
        gen.addProvider(server, new ModifierProvider(packOutput));
        gen.addProvider(client, new FluidTextureProvider(packOutput));
        gen.addProvider(client, new FluidBlockstateModelProvider(packOutput, TCIntegrations.MODID));
        gen.addProvider(client, new FluidBucketModelProvider(packOutput, TCIntegrations.MODID));
        gen.addProvider(server, new ModifierTagProvider(packOutput, TCIntegrations.MODID, existingFileHelper));
        gen.addProvider(server, new EnchantmentToModifierProvider(packOutput));
    }

}