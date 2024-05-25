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

import tcintegrations.common.TagManager;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.TCIntegrations;

public class MaterialRecipeProvider extends RecipeProvider implements IMaterialRecipeHelper, IConditionBuilder {

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
        Consumer<FinishedRecipe> brassConsumer = withCondition(consumer, tagCondition("ingots/brass"));
        Consumer<FinishedRecipe> bygConsumer = withCondition(consumer, modLoaded(ModIntegration.BYG_MODID));

        materialRecipe(botaniaConsumer, MaterialIds.livingWood, Ingredient.of(ModIntegration.BOTANIA_LIVINGWOOD_PLANKS), 1, 1, folder + "livingwood/planks");
        materialRecipe(botaniaConsumer, MaterialIds.livingWood, Ingredient.of(TagManager.Items.BOTANIA_LIVINGWOOD_LOGS), 4, 1, ItemOutput.fromStack(new ItemStack(ModIntegration.BOTANIA_LIVINGWOOD_PLANKS)), folder + "livingwood/logs");
        materialRecipe(botaniaConsumer, MaterialIds.livingRock, Ingredient.of(new ItemStack(ModIntegration.LIVING_ROCK)), 1, 1, folder + "livingrock");
        materialRecipe(botaniaConsumer, MaterialIds.manaString, Ingredient.of(new ItemStack(ModIntegration.MANA_STRING)), 1, 1, folder + "manastring");

        metalMaterialRecipe(botaniaConsumer, MaterialIds.manaSteel, folder, "manasteel", true);
        metalMaterialRecipe(aquacultureConsumer, MaterialIds.neptunium, folder, "neptunium", true);
        metalMaterialRecipe(malumConsumer, MaterialIds.soulStainedSteel, folder, "soul_stained_steel", true);
        metalMaterialRecipe(brassConsumer, MaterialIds.brass, folder, "brass", true);
        metalMaterialRecipe(bygConsumer, MaterialIds.pendoriteAlloy, folder, "pendorite_alloy", true);
    }

    private void addMaterialSmeltery(Consumer<FinishedRecipe> consumer) {
        String folder = "tools/materials/";

        compatMeltingCasting(consumer, MaterialIds.brass, TinkerFluids.moltenBrass, folder);
        compatMeltingCasting(consumer, MaterialIds.manaSteel, TCIntegrationsItems.MOLTEN_MANASTEEL, folder);
        compatMeltingCasting(consumer, MaterialIds.neptunium, TCIntegrationsItems.MOLTEN_NEPTUNIUM, folder);
        compatMeltingCasting(consumer, MaterialIds.soulStainedSteel, TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL, folder);
        compatMeltingCasting(consumer, MaterialIds.pendoriteAlloy, TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY, folder);
    }

}
