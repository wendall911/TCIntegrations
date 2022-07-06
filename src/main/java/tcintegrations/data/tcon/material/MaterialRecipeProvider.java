package tcintegrations.data.tcon.material;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.tconstruct.common.data.BaseRecipeProvider;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;

import tcintegrations.common.TagManager;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.TCIntegrationsItems;
import vazkii.botania.common.block.ModBlocks;

public class MaterialRecipeProvider extends BaseRecipeProvider implements IMaterialRecipeHelper {

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

    private void addMaterialItems(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/materials/";

        materialRecipe(consumer, MaterialIds.livingWood, Ingredient.of(ModIntegration.BOTANIA_LIVINGWOOD_PLANKS), 1, 1, folder + "livingwood/planks");
        materialRecipe(consumer, MaterialIds.livingWood, Ingredient.of(TagManager.Items.BOTANIA_LIVINGWOOD_LOGS), 4, 1, ItemOutput.fromStack(new ItemStack(ModIntegration.BOTANIA_LIVINGWOOD_PLANKS)), folder + "livingwood/logs");

        materialRecipe(consumer, MaterialIds.livingRock, Ingredient.of(new ItemStack(ModBlocks.livingrock)), 1, 1, folder + "livingrock");

        metalMaterialRecipe(consumer, MaterialIds.manaSteel, folder, "manasteel", true);
    }

    private void addMaterialSmeltery(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/materials/";

        compatMeltingCasting(consumer, MaterialIds.manaSteel, TCIntegrationsItems.MANASTEEL, folder);

    }

}