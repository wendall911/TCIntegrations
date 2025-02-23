package tcintegrations.data.tcon;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;

import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;

import slimeknights.mantle.recipe.data.ICommonRecipeHelper;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.SmelteryRecipeBuilder;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipeBuilder;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;

import tcintegrations.common.TagManager;
import tcintegrations.data.BaseRecipeProvider;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.data.tcon.material.MaterialIds;
import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.util.ResourceLocationHelper;

public class SmelteryRecipeProvider extends BaseRecipeProvider implements ISmelteryRecipeHelper, ICommonRecipeHelper {

    public SmelteryRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "TCIntegrations - Smeltery Recipes";
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        this.addMeltingRecipes(consumer);
        this.addCastingRecipes(consumer);
        this.addAlloyRecipes(consumer);
    }

    private void addCastingRecipes(Consumer<FinishedRecipe> consumer) {
        // Pure Fluid Recipes
        String folder = "smeltery/casting/";

        // Molten objects with Bucket, Block, Ingot, and Nugget forms with standard values
        String metalFolder = folder + "metal/";

        Consumer<FinishedRecipe> arsConsumer = withCondition(consumer, modLoaded(ModIntegration.ARS_MODID));
        Consumer<FinishedRecipe> bygConsumer = withCondition(consumer, modLoaded(ModIntegration.BYG_MODID));
        Consumer<FinishedRecipe> undergardenConsumer = withCondition(consumer, modLoaded(ModIntegration.UNDERGARDEN_MODID));

        this.gemCasting(arsConsumer, TCIntegrationsItems.MOLTEN_SOURCE_GEM, ModIntegration.SOURCE_GEM, folder + "source_gem/gem");
        ItemCastingRecipeBuilder.basinRecipe(ModIntegration.SOURCE_GEM_BLOCK)
            .setFluidAndTime(TCIntegrationsItems.MOLTEN_SOURCE_GEM, FluidValues.SMALL_GEM_BLOCK)
            .save(arsConsumer, prefix(TCIntegrationsItems.MOLTEN_SOURCE_GEM, folder + "source_gem/block"));

        this.ingotCasting(bygConsumer, TCIntegrationsItems.MOLTEN_PENDORITE, FluidValues.INGOT, ModIntegration.PENDORITE_SCRAPS, metalFolder + "pendorite/scrap");

        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.PENDORITE_BLOCK), TCIntegrationsItems.MOLTEN_PENDORITE.get(), FluidValues.INGOT * 9, 2.0F)
            .save(bygConsumer, location(metalFolder + "pendorite_alloy/block"));

        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.FORGOTTEN_BLOCK), TCIntegrationsItems.MOLTEN_FORGOTTEN.get(), FluidValues.INGOT * 9, 2.0F)
            .save(undergardenConsumer, location(metalFolder + "forgotten_metal/block"));
    }

    private void addMeltingRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/melting/";

        // ores
        String metalFolder = folder + "metal/";
        Consumer<FinishedRecipe> botaniaConsumer = withCondition(consumer, modLoaded(ModIntegration.BOTANIA_MODID));
        Consumer<FinishedRecipe> aquacultureConsumer = withCondition(consumer, modLoaded(ModIntegration.AQUACULTURE_MODID));
        Consumer<FinishedRecipe> malumConsumer = withCondition(consumer, modLoaded(ModIntegration.MALUM_MODID));
        Consumer<FinishedRecipe> undergardenConsumer = withCondition(consumer, modLoaded(ModIntegration.UNDERGARDEN_MODID));
        Consumer<FinishedRecipe> deshConsumer = withCondition(consumer, tagCondition("ingots/" + MaterialIds.desh.getPath()));
        Consumer<FinishedRecipe> caloriteConsumer = withCondition(consumer, tagCondition("ingots/" + MaterialIds.calorite.getPath()));
        Consumer<FinishedRecipe> ostrumConsumer = withCondition(consumer, tagCondition("ingots/" + MaterialIds.ostrum.getPath()));
        Consumer<FinishedRecipe> bygConsumer = withCondition(consumer, modLoaded(ModIntegration.BYG_MODID));
        Consumer<FinishedRecipe> ifdConsumer = withCondition(consumer, modLoaded(ModIntegration.IFD_MODID));
        Consumer<FinishedRecipe> arsConsumer = withCondition(consumer, modLoaded(ModIntegration.ARS_MODID));

        metal(botaniaConsumer, TCIntegrationsItems.MOLTEN_MANASTEEL).metal();
        metal(aquacultureConsumer, TCIntegrationsItems.MOLTEN_NEPTUNIUM).metal();
        metal(malumConsumer, TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL).metal();
        metal(undergardenConsumer, TCIntegrationsItems.MOLTEN_CLOGGRUM).metal();
        metal(undergardenConsumer, TCIntegrationsItems.MOLTEN_FROSTSTEEL).metal();
        metal(undergardenConsumer, TCIntegrationsItems.MOLTEN_FORGOTTEN).metal();
        metal(deshConsumer, TCIntegrationsItems.MOLTEN_DESH).metal();
        metal(caloriteConsumer, TCIntegrationsItems.MOLTEN_CALORITE).metal();
        metal(ostrumConsumer, TCIntegrationsItems.MOLTEN_OSTRUM).metal();
        metal(ifdConsumer, TCIntegrationsItems.MOLTEN_DRAGONSTEEL_FIRE).metal();
        metal(ifdConsumer, TCIntegrationsItems.MOLTEN_DRAGONSTEEL_ICE).metal();
        metal(ifdConsumer, TCIntegrationsItems.MOLTEN_DRAGONSTEEL_LIGHTNING).metal();

        MeltingRecipeBuilder.melting(Ingredient.of(TagManager.Items.EMERALDITE_SHARDS), TinkerFluids.moltenEmerald.get(), FluidValues.GEM_SHARD, 1.0F)
            .save(bygConsumer, location("emeraldite/shard"));
        MeltingRecipeBuilder.melting(Ingredient.of(TagManager.Items.EMERALDITE_ORE), TinkerFluids.moltenEmerald.get(), FluidValues.GEM_SHARD, 1.0F)
            .save(bygConsumer, location("emeraldite/ore"));

        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.PENDORITE_ORE), TCIntegrationsItems.MOLTEN_PENDORITE.get(), FluidValues.INGOT, 2.0F)
            .save(bygConsumer, location(metalFolder + "pendorite/ore"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.RAW_PENDORITE), TCIntegrationsItems.MOLTEN_PENDORITE.get(), FluidValues.INGOT, 2.0F)
            .save(bygConsumer, location(metalFolder + "pendorite/raw"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.RAW_PENDORITE_BLOCK), TCIntegrationsItems.MOLTEN_PENDORITE.get(), FluidValues.INGOT * 9, 2.0F)
            .save(bygConsumer, location(metalFolder + "pendorite/raw_block"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.PENDORITE_SCRAPS), TCIntegrationsItems.MOLTEN_PENDORITE.get(), FluidValues.INGOT, 2.0F)
            .save(bygConsumer, location(metalFolder + "pendorite/scrap"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.PENDORITE_INGOT), TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY.get(), FluidValues.INGOT, 2.0F)
            .save(bygConsumer, location(metalFolder + "pendorite_alloy/ingot"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.SOURCE_GEM_BLOCK), TCIntegrationsItems.MOLTEN_SOURCE_GEM.get(), FluidValues.SMALL_GEM_BLOCK, 2.0F)
            .save(arsConsumer, location(folder + "source_gem/block"));

        // IFD Silver Items
        // armor
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_SILVER_METAL_HELMET), TinkerFluids.moltenSilver.get(), FluidValues.INGOT * 5)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "silver/helmet"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_SILVER_METAL_CHESTPLATE), TinkerFluids.moltenSilver.get(), FluidValues.INGOT * 8)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "silver/chestplate"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_SILVER_METAL_LEGGINGS), TinkerFluids.moltenSilver.get(), FluidValues.INGOT * 7)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "silver/leggings"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_SILVER_METAL_BOOTS), TinkerFluids.moltenSilver.get(), FluidValues.INGOT * 4)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "silver/boots"));
        // tools
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_SILVER_AXE, ModIntegration.IFD_SILVER_PICKAXE), TinkerFluids.moltenSilver.get(), FluidValues.INGOT * 3)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "silver/axes"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_SILVER_SWORD, ModIntegration.IFD_SILVER_HOE), TinkerFluids.moltenSilver.get(), FluidValues.INGOT * 2)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "silver/weapon"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_SILVER_SHOVEL), TinkerFluids.moltenSilver.get(), FluidValues.INGOT)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "silver/small"));
        // Dragon Armor
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_DRAGONARMOR_SILVER_HEAD, ModIntegration.IFD_DRAGONARMOR_SILVER_NECK), TinkerFluids.moltenSilver.get(), FluidValues.METAL_BLOCK * 5)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "silver/dragon_armor_head_neck"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_DRAGONARMOR_SILVER_BODY), TinkerFluids.moltenSilver.get(), FluidValues.METAL_BLOCK * 8)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "silver/dragon_armor_head_body"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_DRAGONARMOR_SILVER_TAIL), TinkerFluids.moltenSilver.get(), FluidValues.METAL_BLOCK * 3)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "silver/dragon_armor_head_tail"));

        // IFD Copper Items
        // armor
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_COPPER_METAL_HELMET), TinkerFluids.moltenCopper.get(), FluidValues.INGOT * 5)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "copper/helmet"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_COPPER_METAL_CHESTPLATE), TinkerFluids.moltenCopper.get(), FluidValues.INGOT * 8)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "copper/chestplate"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_COPPER_METAL_LEGGINGS), TinkerFluids.moltenCopper.get(), FluidValues.INGOT * 7)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "copper/leggings"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_COPPER_METAL_BOOTS), TinkerFluids.moltenCopper.get(), FluidValues.INGOT * 4)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "copper/boots"));
        // tools
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_COPPER_AXE, ModIntegration.IFD_COPPER_PICKAXE), TinkerFluids.moltenCopper.get(), FluidValues.INGOT * 3)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "copper/axes"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_COPPER_SWORD, ModIntegration.IFD_COPPER_HOE), TinkerFluids.moltenCopper.get(), FluidValues.INGOT * 2)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "copper/weapon"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_COPPER_SHOVEL), TinkerFluids.moltenCopper.get(), FluidValues.INGOT)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "copper/small"));
        // Dragon Armor
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_DRAGONARMOR_COPPER_HEAD, ModIntegration.IFD_DRAGONARMOR_COPPER_NECK), TinkerFluids.moltenCopper.get(), FluidValues.METAL_BLOCK * 5)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "copper/dragon_armor_head_neck"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_DRAGONARMOR_COPPER_BODY), TinkerFluids.moltenCopper.get(), FluidValues.METAL_BLOCK * 8)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "copper/dragon_armor_head_body"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_DRAGONARMOR_COPPER_TAIL), TinkerFluids.moltenCopper.get(), FluidValues.METAL_BLOCK * 3)
            .setDamagable(FluidValues.NUGGET)
            .save(consumer, location(metalFolder + "copper/dragon_armor_head_tail"));
    }

    private void addAlloyRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/alloys/";
        Consumer<FinishedRecipe> bygConsumer = withCondition(consumer, modLoaded(ModIntegration.BYG_MODID));

        // Update Recipe to use Obsidian instead of Quartz to not interfere with Hepatizon
        ConditionalRecipe.builder()
            .addCondition(new TagEmptyCondition(ResourceLocationHelper.location("forge", "ingots/tin")))
            .addRecipe(
                AlloyRecipeBuilder.alloy(TinkerFluids.moltenBronze.get(), FluidValues.INGOT * 4)
                    .addInput(TinkerFluids.moltenCopper.getTag(), FluidValues.INGOT * 3)
                    .addInput(TinkerFluids.moltenObsidian.getLocalTag(), FluidValues.GLASS_BLOCK)::save)
            .build(consumer, prefix(TinkerFluids.moltenBronze, folder));

        AlloyRecipeBuilder.alloy(TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY.get(), FluidValues.INGOT)
            .addInput(TCIntegrationsItems.MOLTEN_PENDORITE.getTag(), FluidValues.INGOT * 4)
            .addInput(TinkerFluids.moltenDiamond.getLocalTag(), FluidValues.GEM * 2)
            .addInput(TinkerFluids.moltenEmerald.getLocalTag(), FluidValues.GEM_SHARD * 2)
            .save(bygConsumer, prefix(TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY, folder));
    }

    /** Creates a metal from a tag */
    public SmelteryRecipeBuilder metal(Consumer<FinishedRecipe> consumer, String name, TagKey<Fluid> fluid) {
        return SmelteryRecipeBuilder.fluid(consumer, location(name), fluid).castingFolder("smeltery/casting/metal").meltingFolder("smeltery/melting/metal");
    }

    /** Creates a smeltery builder for a metal fluid */
    public SmelteryRecipeBuilder metal(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid) {
        return molten(consumer, fluid).castingFolder("smeltery/casting/metal").meltingFolder("smeltery/melting/metal");
    }

}
