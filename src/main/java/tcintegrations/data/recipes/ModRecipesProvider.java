package tcintegrations.data.recipes;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import net.minecraftforge.common.crafting.conditions.ICondition;

import slimeknights.mantle.recipe.data.ConsumerWrapperBuilder;

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
    }

    private static Consumer<FinishedRecipe> withCondition(Consumer<FinishedRecipe> consumer, ICondition... conditions) {
        ConsumerWrapperBuilder builder = ConsumerWrapperBuilder.wrap();

        for (ICondition condition : conditions) {
            builder.addCondition(condition);
        }

        return builder.build(consumer);
    }

}
