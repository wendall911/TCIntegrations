package tcintegrations.data.tcon;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import net.minecraftforge.common.crafting.conditions.TrueCondition;
import net.minecraftforge.common.crafting.ConditionalRecipe;

import potionstudios.byg.common.block.BYGBlocks;
import potionstudios.byg.common.item.BYGItems;

import slimeknights.tconstruct.common.data.BaseRecipeProvider;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.ICommonRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipeBuilder;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer.OreRateType;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;
import slimeknights.tconstruct.smeltery.data.Byproduct;

import tcintegrations.common.TagManager;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.TCIntegrations;

public class SmelteryRecipeProvider extends BaseRecipeProvider implements ISmelteryRecipeHelper, ICommonRecipeHelper {

    public SmelteryRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "TCIntegrations - TCon Smeltery Recipes";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        this.addMeltingRecipes(consumer);
        this.addCastingRecipes(consumer);
        this.addAlloyRecipes(consumer);
    }

    @Override
    public String getModId() {
        return TCIntegrations.MODID;
    }

    private void addCastingRecipes(Consumer<FinishedRecipe> consumer) {
        // Pure Fluid Recipes
        String folder = "smeltery/casting/";

        // Molten objects with Bucket, Block, Ingot, and Nugget forms with standard values
        String metalFolder = folder + "metal/";

        Map<String, Consumer<FinishedRecipe>> modConsumers = new HashMap<>();
        Consumer<FinishedRecipe> arsConsumer = withCondition(consumer, modLoaded(ModIntegration.ARS_MODID));
        Consumer<FinishedRecipe> bygConsumer = withCondition(consumer, modLoaded(ModIntegration.BYG_MODID));

        modConsumers.put(ModIntegration.BOTANIA_MODID, withCondition(consumer, modLoaded(ModIntegration.BOTANIA_MODID)));
        modConsumers.put(ModIntegration.AQUACULTURE_MODID, withCondition(consumer, modLoaded(ModIntegration.AQUACULTURE_MODID)));
        modConsumers.put(ModIntegration.MALUM_MODID, withCondition(consumer, modLoaded(ModIntegration.MALUM_MODID)));
        modConsumers.put(ModIntegration.UNDERGARDEN_MODID, withCondition(consumer, modLoaded(ModIntegration.UNDERGARDEN_MODID)));
        modConsumers.put(ModIntegration.BYG_MODID, bygConsumer);

        this.gemCasting(arsConsumer, TCIntegrationsItems.MOLTEN_SOURCE_GEM, ItemsRegistry.SOURCE_GEM.asItem(), folder + "source_gem/gem");
        ItemCastingRecipeBuilder.basinRecipe(BlockRegistry.SOURCE_GEM_BLOCK)
            .setFluidAndTime(TCIntegrationsItems.MOLTEN_SOURCE_GEM, false, FluidValues.SMALL_GEM_BLOCK)
            .save(arsConsumer, prefix(TCIntegrationsItems.MOLTEN_SOURCE_GEM, folder + "source_gem/block"));

        this.ingotCasting(bygConsumer, TCIntegrationsItems.MOLTEN_PENDORITE, false, BYGItems.PENDORITE_SCRAPS.get(), metalFolder + "pendorite/scrap");
        this.metalCasting(bygConsumer, TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY, false, BYGBlocks.PENDORITE_BLOCK.get(), BYGItems.PENDORITE_INGOT.get(), null, metalFolder, "pendorite_alloy/block");

        for (SmelteryCompat compat : SmelteryCompat.values()) {
            this.metalTagCasting(modConsumers.get(compat.getModid()), compat.getFluid(), compat.getName(), metalFolder, false);
        }
    }

    private void addMeltingRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/melting/";

        // ores
        String metalFolder = folder + "metal/";
        Consumer<FinishedRecipe> botaniaConsumer = withCondition(consumer, modLoaded(ModIntegration.BOTANIA_MODID));
        Consumer<FinishedRecipe> aquacultureConsumer = withCondition(consumer, modLoaded(ModIntegration.AQUACULTURE_MODID));
        Consumer<FinishedRecipe> malumConsumer = withCondition(consumer, modLoaded(ModIntegration.MALUM_MODID));
        Consumer<FinishedRecipe> undergardenConsumer = withCondition(consumer, modLoaded(ModIntegration.UNDERGARDEN_MODID));
        Consumer<FinishedRecipe> beyondEarthConsumer = withCondition(consumer, modLoaded(ModIntegration.BEYOND_EARTH_MODID));
        Consumer<FinishedRecipe> bygConsumer = withCondition(consumer, modLoaded(ModIntegration.BYG_MODID));

        metalMelting(botaniaConsumer, TCIntegrationsItems.MOLTEN_MANASTEEL.get(), "manasteel", false, metalFolder, false, Byproduct.IRON);
        metalMelting(aquacultureConsumer, TCIntegrationsItems.MOLTEN_NEPTUNIUM.get(), "neptunium", false, metalFolder, false);
        metalMelting(malumConsumer, TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL.get(), "soul_stained_steel", false, metalFolder, false, Byproduct.IRON);
        metalMelting(undergardenConsumer, TCIntegrationsItems.MOLTEN_CLOGGRUM.get(), "cloggrum", true, metalFolder, false);
        metalMelting(undergardenConsumer, TCIntegrationsItems.MOLTEN_FROSTSTEEL.get(), "froststeel", true, metalFolder, false);
        metalMelting(undergardenConsumer, TCIntegrationsItems.MOLTEN_FORGOTTEN.get(), "forgotten", false, metalFolder, false);
        metalMelting(beyondEarthConsumer, ModIntegration.MOLTEN_DESH.get(), "desh", true, metalFolder, false);
        metalMelting(beyondEarthConsumer, ModIntegration.MOLTEN_CALORITE.get(), "calorite", true, metalFolder, false);
        metalMelting(beyondEarthConsumer, ModIntegration.MOLTEN_OSTRUM.get(), "ostrum", true, metalFolder, false);

        MeltingRecipeBuilder.melting(Ingredient.of(TagManager.Items.EMERALDITE_SHARDS), TinkerFluids.moltenEmerald.get(), FluidValues.GEM_SHARD, 1.0F)
            .setOre(OreRateType.GEM)
            .save(bygConsumer, modResource("emeraldite/shard"));
        MeltingRecipeBuilder.melting(Ingredient.of(TagManager.Items.EMERALDITE_ORE), TinkerFluids.moltenEmerald.get(), FluidValues.GEM_SHARD, 1.0F)
            .setOre(OreRateType.GEM)
            .save(bygConsumer, modResource("emeraldite/ore"));

        MeltingRecipeBuilder.melting(Ingredient.of(BYGItems.PENDORITE_ORE.get()), TCIntegrationsItems.MOLTEN_PENDORITE.get(), FluidValues.INGOT, 2.0F)
            .setOre(OreRateType.METAL, OreRateType.GEM, OreRateType.METAL)
            .save(bygConsumer, modResource(metalFolder + "pendorite/ore"));
        MeltingRecipeBuilder.melting(Ingredient.of(BYGItems.RAW_PENDORITE.get()), TCIntegrationsItems.MOLTEN_PENDORITE.get(), FluidValues.INGOT, 2.0F)
            .setOre(OreRateType.METAL, OreRateType.GEM, OreRateType.METAL)
            .save(bygConsumer, modResource(metalFolder + "pendorite/raw"));
        MeltingRecipeBuilder.melting(Ingredient.of(BYGItems.RAW_PENDORITE_BLOCK.get()), TCIntegrationsItems.MOLTEN_PENDORITE.get(), FluidValues.INGOT * 9, 2.0F)
            .setOre(OreRateType.METAL, OreRateType.GEM, OreRateType.METAL)
            .save(bygConsumer, modResource(metalFolder + "pendorite/raw_block"));
        MeltingRecipeBuilder.melting(Ingredient.of(BYGItems.PENDORITE_SCRAPS.get()), TCIntegrationsItems.MOLTEN_PENDORITE.get(), FluidValues.INGOT, 2.0F)
            .setOre(OreRateType.METAL, OreRateType.GEM, OreRateType.METAL)
            .save(bygConsumer, modResource(metalFolder + "pendorite/scrap"));
        MeltingRecipeBuilder.melting(Ingredient.of(BYGItems.PENDORITE_INGOT.get()), TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY.get(), FluidValues.INGOT, 2.0F)
            .setOre(OreRateType.METAL, OreRateType.GEM, OreRateType.METAL)
            .save(bygConsumer, modResource(metalFolder + "pendorite_alloy/ingot"));
    }

    private void addAlloyRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/alloys/";
        Consumer<FinishedRecipe> bygConsumer = withCondition(consumer, modLoaded(ModIntegration.BYG_MODID));

        ConditionalRecipe.builder()
            .addCondition(new TagEmptyCondition(new ResourceLocation("forge", "ingots/tin")))
            .addRecipe(
                AlloyRecipeBuilder.alloy(TinkerFluids.moltenBronze.get(), FluidValues.INGOT * 4)
                    .addInput(TinkerFluids.moltenCopper.getForgeTag(), FluidValues.INGOT * 3)
                    .addInput(TinkerFluids.moltenQuartz.getLocalTag(), FluidValues.GEM)::save)
            .build(consumer, prefix(TinkerFluids.moltenBronze, folder));

        AlloyRecipeBuilder.alloy(TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY.get(), FluidValues.INGOT)
            .addInput(TCIntegrationsItems.MOLTEN_PENDORITE.getForgeTag(), FluidValues.INGOT * 4)
            .addInput(TinkerFluids.moltenDiamond.getLocalTag(), FluidValues.GEM * 2)
            .addInput(TinkerFluids.moltenEmerald.getLocalTag(), FluidValues.GEM_SHARD * 2)
            .save(bygConsumer, prefix(TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY, folder));
    }

}
