package tcintegrations.data.tcon.material;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;

import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.IRecipeHelper;

import static slimeknights.tconstruct.tools.data.material.MaterialIds.brass;

import vazkii.botania.common.block.ModBlocks;
import vazkii.botania.common.item.ModItems;

import tcintegrations.common.TagManager;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.TCIntegrations;

public class MaterialRecipeProvider extends RecipeProvider implements IConditionBuilder, IRecipeHelper, IMaterialRecipeHelper {

    public MaterialRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "TCIntegrations - TCon Material Recipe";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        addMaterialItems(consumer);
        addMaterialSmeltery(consumer);
    }

    @Override
    public String getModId() {
        return TCIntegrations.MODID;
    }

    private void addMaterialItems(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/materials/";
        Consumer<FinishedRecipe> botaniaConsumer = withCondition(consumer, modLoaded(ModIntegration.BOTANIA_MODID));
        Consumer<FinishedRecipe> aquacultureConsumer = withCondition(consumer, modLoaded(ModIntegration.AQUACULTURE_MODID));
        Consumer<FinishedRecipe> malumConsumer = withCondition(consumer, modLoaded(ModIntegration.MALUM_MODID));
        Consumer<FinishedRecipe> beyondEarthConsumer = withCondition(consumer, modLoaded(ModIntegration.BEYOND_EARTH_MODID));
        Consumer<FinishedRecipe> brassConsumer = withCondition(consumer, tagCondition("ingots/brass"));
        Consumer<FinishedRecipe> bygConsumer = withCondition(consumer, modLoaded(ModIntegration.BYG_MODID));

        materialRecipe(botaniaConsumer, MaterialIds.livingWood, Ingredient.of(ModIntegration.BOTANIA_LIVINGWOOD_PLANKS), 1, 1, folder + "livingwood/planks");
        materialRecipe(botaniaConsumer, MaterialIds.livingWood, Ingredient.of(TagManager.Items.BOTANIA_LIVINGWOOD_LOGS), 4, 1, ItemOutput.fromStack(new ItemStack(ModIntegration.BOTANIA_LIVINGWOOD_PLANKS)), folder + "livingwood/logs");
        materialRecipe(botaniaConsumer, MaterialIds.livingRock, Ingredient.of(new ItemStack(ModBlocks.livingrock)), 1, 1, folder + "livingrock");
        materialRecipe(botaniaConsumer, MaterialIds.manaString, Ingredient.of(new ItemStack(ModItems.manaString)), 1, 1, folder + "manastring");

        metalMaterialRecipe(botaniaConsumer, MaterialIds.manaSteel, folder, "manasteel", true);
        metalMaterialRecipe(aquacultureConsumer, MaterialIds.neptunium, folder, "neptunium", true);
        metalMaterialRecipe(malumConsumer, MaterialIds.soulStainedSteel, folder, "soul_stained_steel", true);
        metalMaterialRecipe(beyondEarthConsumer, MaterialIds.desh, folder, "desh", true);
        metalMaterialRecipe(beyondEarthConsumer, MaterialIds.calorite, folder, "calorite", true);
        metalMaterialRecipe(beyondEarthConsumer, MaterialIds.ostrum, folder, "ostrum", true);
        metalMaterialRecipe(brassConsumer, brass, folder, "brass", true);
        metalMaterialRecipe(bygConsumer, MaterialIds.pendoriteAlloy, folder, "pendorite_alloy", true);
    }

    private void addMaterialSmeltery(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/materials/";

        compatMeltingCasting(consumer, brass, TinkerFluids.moltenBrass, folder);
        compatMeltingCasting(consumer, MaterialIds.manaSteel, TCIntegrationsItems.MOLTEN_MANASTEEL, folder);
        compatMeltingCasting(consumer, MaterialIds.neptunium, TCIntegrationsItems.MOLTEN_NEPTUNIUM, folder);
        compatMeltingCasting(consumer, MaterialIds.soulStainedSteel, TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL, folder);
        compatMeltingCasting(consumer, MaterialIds.desh, ModIntegration.MOLTEN_DESH, folder);
        compatMeltingCasting(consumer, MaterialIds.calorite, ModIntegration.MOLTEN_CALORITE, folder);
        compatMeltingCasting(consumer, MaterialIds.ostrum, ModIntegration.MOLTEN_OSTRUM, folder);
        compatMeltingCasting(consumer, MaterialIds.pendoriteAlloy, TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY, folder);
    }

}