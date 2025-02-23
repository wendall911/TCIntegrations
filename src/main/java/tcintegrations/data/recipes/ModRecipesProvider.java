package tcintegrations.data.recipes;

import java.util.function.Consumer;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import tcintegrations.data.BaseRecipeProvider;
import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.TCIntegrations;

public class ModRecipesProvider extends BaseRecipeProvider {

    public ModRecipesProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "TCIntegrations - Mod Recipes";
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> consumer) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TCIntegrationsItems.BRONZE.getNugget(), 9)
            .requires(TCIntegrationsItems.BRONZE.getIngotTag())
            .unlockedBy("has_bronze_ingot", has(TCIntegrationsItems.BRONZE.getIngotTag()))
            .save(consumer, ResourceLocation.fromNamespaceAndPath(TCIntegrations.MODID, "bronze_ingot_from_nuggets"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, TCIntegrationsItems.BRONZE.getIngot(), 1)
            .requires(Ingredient.of(TCIntegrationsItems.BRONZE.getNuggetTag()), 9)
            .unlockedBy("has_bronze_nugget", has(TCIntegrationsItems.BRONZE.getNuggetTag()))
            .save(consumer, ResourceLocation.fromNamespaceAndPath(TCIntegrations.MODID, "bronze_nuggets_from_ingot"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TCIntegrationsItems.BRONZE.get(), 1)
            .requires(Ingredient.of(TCIntegrationsItems.BRONZE.getIngotTag()), 9)
            .unlockedBy("has_bronze_ingot", has(TCIntegrationsItems.BRONZE.getIngotTag()))
            .save(consumer, ResourceLocation.fromNamespaceAndPath(TCIntegrations.MODID, "bronze_block_from_ingots"));

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC,TCIntegrationsItems.BRONZE.getIngot(), 9)
            .requires(TCIntegrationsItems.BRONZE.getBlockItemTag())
            .unlockedBy("has_bronze_ingot", has(TCIntegrationsItems.BRONZE.getIngotTag()))
            .save(consumer, ResourceLocation.fromNamespaceAndPath(TCIntegrations.MODID, "bronze_ingots_from_block"));
    }

}
