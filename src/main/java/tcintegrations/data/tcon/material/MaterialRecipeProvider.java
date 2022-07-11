package tcintegrations.data.tcon.material;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.tconstruct.common.data.BaseRecipeProvider;
import slimeknights.tconstruct.library.data.recipe.IMaterialRecipeHelper;

import vazkii.botania.common.block.ModBlocks;

import tcintegrations.common.TagManager;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.TCIntegrationsItems;

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
        Consumer<FinishedRecipe> botaniaConsumer = withCondition(consumer, modLoaded(ModIntegration.BOTANIA_MODID));

        materialRecipe(botaniaConsumer, MaterialIds.livingWood, Ingredient.of(ModIntegration.BOTANIA_LIVINGWOOD_PLANKS), 1, 1, folder + "livingwood/planks");
        materialRecipe(botaniaConsumer, MaterialIds.livingWood, Ingredient.of(TagManager.Items.BOTANIA_LIVINGWOOD_LOGS), 4, 1, ItemOutput.fromStack(new ItemStack(ModIntegration.BOTANIA_LIVINGWOOD_PLANKS)), folder + "livingwood/logs");

        materialRecipe(botaniaConsumer, MaterialIds.livingRock, Ingredient.of(new ItemStack(ModBlocks.livingrock)), 1, 1, folder + "livingrock");

        metalMaterialRecipe(botaniaConsumer, MaterialIds.manaSteel, folder, "manasteel", true);
    }

    private void addMaterialSmeltery(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/materials/";

        compatMeltingCasting(consumer, MaterialIds.manaSteel, TCIntegrationsItems.MANASTEEL, folder);
    }

}