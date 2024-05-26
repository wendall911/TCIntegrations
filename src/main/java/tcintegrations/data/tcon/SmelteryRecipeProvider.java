package tcintegrations.data.tcon;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.jetbrains.annotations.Nullable;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import slimeknights.mantle.recipe.data.ICommonRecipeHelper;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipeBuilder;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;
import slimeknights.tconstruct.smeltery.data.Byproduct;

import tcintegrations.common.TagManager;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.data.tcon.material.MaterialIds;
import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.TCIntegrations;
import tcintegrations.util.ResourceLocationHelper;

public class SmelteryRecipeProvider extends RecipeProvider implements ISmelteryRecipeHelper, ICommonRecipeHelper, IConditionBuilder {

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
        Consumer<FinishedRecipe> undergardenConsumer = withCondition(consumer, modLoaded(ModIntegration.UNDERGARDEN_MODID));

        modConsumers.put(ModIntegration.BOTANIA_MODID, withCondition(consumer, modLoaded(ModIntegration.BOTANIA_MODID)));
        modConsumers.put(ModIntegration.AQUACULTURE_MODID, withCondition(consumer, modLoaded(ModIntegration.AQUACULTURE_MODID)));
        modConsumers.put(ModIntegration.MALUM_MODID, withCondition(consumer, modLoaded(ModIntegration.MALUM_MODID)));
        modConsumers.put(ModIntegration.UNDERGARDEN_MODID, undergardenConsumer);
        modConsumers.put(ModIntegration.BYG_MODID, bygConsumer);
        modConsumers.put(MaterialIds.desh.getPath(), withCondition(consumer, tagCondition("ingots/" + MaterialIds.desh.getPath())));
        modConsumers.put(MaterialIds.calorite.getPath(), withCondition(consumer, tagCondition("ingots/" + MaterialIds.calorite.getPath())));
        modConsumers.put(MaterialIds.ostrum.getPath(), withCondition(consumer, tagCondition("ingots/" + MaterialIds.ostrum.getPath())));
        modConsumers.put(ModIntegration.IFD_MODID, withCondition(consumer, modLoaded(ModIntegration.IFD_MODID)));

        this.gemCasting(arsConsumer, TCIntegrationsItems.MOLTEN_SOURCE_GEM, ModIntegration.SOURCE_GEM, folder + "source_gem/gem");
        ItemCastingRecipeBuilder.basinRecipe(ModIntegration.SOURCE_GEM_BLOCK)
            .setFluidAndTime(TCIntegrationsItems.MOLTEN_SOURCE_GEM, false, FluidValues.SMALL_GEM_BLOCK)
            .save(arsConsumer, prefix(TCIntegrationsItems.MOLTEN_SOURCE_GEM, folder + "source_gem/block"));

        this.ingotCasting(bygConsumer, TCIntegrationsItems.MOLTEN_PENDORITE, false, ModIntegration.PENDORITE_SCRAPS, metalFolder + "pendorite/scrap");
        this.metalCasting(bygConsumer, TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY, false, ModIntegration.PENDORITE_BLOCK, ModIntegration.PENDORITE_INGOT, null, metalFolder, "pendorite_alloy/block");

        this.metalCasting(undergardenConsumer, TCIntegrationsItems.MOLTEN_FORGOTTEN, false, ModIntegration.FORGOTTEN_BLOCK, ModIntegration.FORGOTTEN_INGOT, ModIntegration.FORGOTTEN_NUGGET, metalFolder, "forgotten/block");

        for (SmelteryCompat compat : SmelteryCompat.values()) {
            this.metalTagCasting(modConsumers.get(compat.getIdentifier()), compat.getFluid(), compat.getName(), metalFolder, false);
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
        Consumer<FinishedRecipe> deshConsumer = withCondition(consumer, tagCondition("ingots/" + MaterialIds.desh.getPath()));
        Consumer<FinishedRecipe> caloriteConsumer = withCondition(consumer, tagCondition("ingots/" + MaterialIds.calorite.getPath()));
        Consumer<FinishedRecipe> ostrumConsumer = withCondition(consumer, tagCondition("ingots/" + MaterialIds.ostrum.getPath()));
        Consumer<FinishedRecipe> bygConsumer = withCondition(consumer, modLoaded(ModIntegration.BYG_MODID));
        Consumer<FinishedRecipe> ifdConsumer = withCondition(consumer, modLoaded(ModIntegration.IFD_MODID));
        Consumer<FinishedRecipe> arsConsumer = withCondition(consumer, modLoaded(ModIntegration.ARS_MODID));

        addMetal(botaniaConsumer, TCIntegrationsItems.MOLTEN_MANASTEEL, false, metalFolder, false, Byproduct.IRON);
        addMetal(aquacultureConsumer, TCIntegrationsItems.MOLTEN_NEPTUNIUM, false, metalFolder, false, null);
        addMetal(malumConsumer, TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL, false, metalFolder, false, Byproduct.IRON);
        addMetal(undergardenConsumer, TCIntegrationsItems.MOLTEN_CLOGGRUM, true, metalFolder, false, null);
        addMetal(undergardenConsumer, TCIntegrationsItems.MOLTEN_FROSTSTEEL, true, metalFolder, false, null);
        addMetal(undergardenConsumer, TCIntegrationsItems.MOLTEN_FORGOTTEN, false, metalFolder, false, null);
        addMetal(deshConsumer, TCIntegrationsItems.MOLTEN_DESH, true, metalFolder, false, null);
        addMetal(caloriteConsumer, TCIntegrationsItems.MOLTEN_CALORITE, true, metalFolder, false, null);
        addMetal(ostrumConsumer, TCIntegrationsItems.MOLTEN_OSTRUM, true, metalFolder, false, null);
        addMetal(ifdConsumer, TCIntegrationsItems.MOLTEN_DRAGONSTEEL_FIRE, false, metalFolder, false, Byproduct.IRON);
        addMetal(ifdConsumer, TCIntegrationsItems.MOLTEN_DRAGONSTEEL_ICE, false, metalFolder, false, Byproduct.IRON);
        addMetal(ifdConsumer, TCIntegrationsItems.MOLTEN_DRAGONSTEEL_LIGHTNING, false, metalFolder, false, Byproduct.IRON);

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
        gemMelting(arsConsumer, TCIntegrationsItems.MOLTEN_SOURCE_GEM.get(), "source_gem", false, 4, folder, false);
    }

    private void addAlloyRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/alloys/";
        Consumer<FinishedRecipe> bygConsumer = withCondition(consumer, modLoaded(ModIntegration.BYG_MODID));

        // Update Recipe to use Obsidian instead of Quartz to not interfere with Hepatizon
        ConditionalRecipe.builder()
            .addCondition(new TagEmptyCondition(ResourceLocationHelper.location("forge", "ingots/tin")))
            .addRecipe(
                AlloyRecipeBuilder.alloy(TinkerFluids.moltenBronze.get(), FluidValues.INGOT * 4)
                    .addInput(TinkerFluids.moltenCopper.getForgeTag(), FluidValues.INGOT * 3)
                    .addInput(TinkerFluids.moltenObsidian.getLocalTag(), FluidValues.GLASS_BLOCK)::save)
            .build(consumer, prefix(TinkerFluids.moltenBronze, folder));

        AlloyRecipeBuilder.alloy(TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY.get(), FluidValues.INGOT)
            .addInput(TCIntegrationsItems.MOLTEN_PENDORITE.getForgeTag(), FluidValues.INGOT * 4)
            .addInput(TinkerFluids.moltenDiamond.getLocalTag(), FluidValues.GEM * 2)
            .addInput(TinkerFluids.moltenEmerald.getLocalTag(), FluidValues.GEM_SHARD * 2)
            .save(bygConsumer, prefix(TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY, folder));
    }

    private void addMetal(Consumer<FinishedRecipe> consumer, FlowingFluidObject<ForgeFlowingFluid> fluid, boolean hasOre, String folder, boolean isOptional, @Nullable Byproduct byproduct) {
        if (byproduct != null) {
            metalMelting(consumer, fluid.get(), fluid.getId().getPath(), hasOre, folder, isOptional, byproduct);
        }
        else {
            metalMelting(consumer, fluid.get(), fluid.getId().getPath(), hasOre, folder, isOptional);
        }
    }

}
