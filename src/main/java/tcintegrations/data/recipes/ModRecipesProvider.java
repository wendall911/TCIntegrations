package tcintegrations.data.recipes;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraftforge.common.crafting.conditions.ICondition;

import slimeknights.mantle.recipe.data.ConsumerWrapperBuilder;

import tcintegrations.TCIntegrations;
import tcintegrations.items.TCIntegrationsItems;

public class ModRecipesProvider extends RecipeProvider {

    public ModRecipesProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    public String getName() {
        return "TCIntegrations - Recipies";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        Consumer<FinishedRecipe> wrapped;

        ShapelessRecipeBuilder.shapeless(TCIntegrationsItems.BRONZE.getNugget(), 9)
            .requires(TCIntegrationsItems.BRONZE.getIngotTag())
            .unlockedBy("has_bronze_ingot", has(TCIntegrationsItems.BRONZE.getIngotTag()))
            .save(consumer, new ResourceLocation(TCIntegrations.MODID, "bronze_ingot_from_nuggets"));

        ShapelessRecipeBuilder.shapeless(TCIntegrationsItems.BRONZE.getIngot(), 1)
            .requires(Ingredient.of(TCIntegrationsItems.BRONZE.getNuggetTag()), 9)
            .unlockedBy("has_bronze_nugget", has(TCIntegrationsItems.BRONZE.getNuggetTag()))
            .save(consumer, new ResourceLocation(TCIntegrations.MODID, "bronze_nuggets_from_ingot"));

        ShapelessRecipeBuilder.shapeless(TCIntegrationsItems.BRONZE.get(), 1)
            .requires(Ingredient.of(TCIntegrationsItems.BRONZE.getIngotTag()), 9)
            .unlockedBy("has_bronze_ingot", has(TCIntegrationsItems.BRONZE.getIngotTag()))
            .save(consumer, new ResourceLocation(TCIntegrations.MODID, "bronze_block_from_ingots"));

        ShapelessRecipeBuilder.shapeless(TCIntegrationsItems.BRONZE.getIngot(), 9)
            .requires(TCIntegrationsItems.BRONZE.getBlockItemTag())
            .unlockedBy("has_bronze_ingot", has(TCIntegrationsItems.BRONZE.getIngotTag()))
            .save(consumer, new ResourceLocation(TCIntegrations.MODID, "bronze_ingots_from_block"));
    }

    private static Consumer<FinishedRecipe> withCondition(Consumer<FinishedRecipe> consumer, ICondition... conditions) {
        ConsumerWrapperBuilder builder = ConsumerWrapperBuilder.wrap();

        for (ICondition condition : conditions) {
            builder.addCondition(condition);
        }

        return builder.build(consumer);
    }

}
