package tcintegrations.data;

import java.util.function.Consumer;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;

import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import slimeknights.mantle.recipe.data.IRecipeHelper;

import tcintegrations.TCIntegrations;

public abstract class BaseRecipeProvider extends RecipeProvider implements IConditionBuilder, IRecipeHelper {

    public BaseRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected abstract void buildRecipes(Consumer<FinishedRecipe> consumer);

    @Override
    public abstract String getName();

    @Override
    public String getModId() {
        return TCIntegrations.MODID;
    }

}
