package tcintegrations.data.recipes;

import java.util.function.Consumer;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import tcintegrations.TCIntegrations;
import tcintegrations.items.TCIntegrationsItems;

public class ModRecipesProvider extends RecipeProvider {

    public ModRecipesProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TCIntegrationsItems.BRONZE.getNugget(), 9)
            .requires(TCIntegrationsItems.BRONZE.getIngotTag())
            .unlockedBy("has_bronze_ingot", has(TCIntegrationsItems.BRONZE.getIngotTag()))
            .save(consumer, new ResourceLocation(TCIntegrations.MODID, "bronze_ingot_from_nuggets"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TCIntegrationsItems.BRONZE.getIngot(), 1)
            .requires(Ingredient.of(TCIntegrationsItems.BRONZE.getNuggetTag()), 9)
            .unlockedBy("has_bronze_nugget", has(TCIntegrationsItems.BRONZE.getNuggetTag()))
            .save(consumer, new ResourceLocation(TCIntegrations.MODID, "bronze_nuggets_from_ingot"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TCIntegrationsItems.BRONZE.get(), 1)
            .requires(Ingredient.of(TCIntegrationsItems.BRONZE.getIngotTag()), 9)
            .unlockedBy("has_bronze_ingot", has(TCIntegrationsItems.BRONZE.getIngotTag()))
            .save(consumer, new ResourceLocation(TCIntegrations.MODID, "bronze_block_from_ingots"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TCIntegrationsItems.BRONZE.getIngot(), 9)
            .requires(TCIntegrationsItems.BRONZE.getBlockItemTag())
            .unlockedBy("has_bronze_ingot", has(TCIntegrationsItems.BRONZE.getIngotTag()))
            .save(consumer, new ResourceLocation(TCIntegrations.MODID, "bronze_ingots_from_block"));
    }

}
