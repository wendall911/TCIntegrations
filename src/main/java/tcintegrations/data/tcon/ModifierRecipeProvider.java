package tcintegrations.data.tcon;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.world.item.Items;

import net.minecraftforge.common.Tags;

import slimeknights.tconstruct.common.data.BaseRecipeProvider;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.tools.SlotType;

import tcintegrations.items.TCIntegrationsItems;

public class ModifierRecipeProvider extends BaseRecipeProvider {

    public ModifierRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "TCIntegrations - TCon Modifier Recipes";
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer) {
        addModifierRecipes(consumer);
    }

    private void addModifierRecipes(Consumer<FinishedRecipe> consumer) {
        // upgrades
        String upgradeFolder = "tools/modifiers/upgrade/";
        String upgradeSalvage = "tools/modifiers/salvage/upgrade/";

        ModifierRecipeBuilder.modifier(TCIntegrationsItems.EMERALD_MODIFIER.get())
            .addInput(Tags.Items.GEMS_EMERALD)
            .addSalvage(Items.EMERALD, 0.5f)
            .setMaxLevel(1)
            .setSlots(SlotType.UPGRADE, 1)
            .saveSalvage(consumer, prefix(TCIntegrationsItems.EMERALD_MODIFIER, upgradeSalvage))
            .save(consumer, prefix(TCIntegrationsItems.EMERALD_MODIFIER, upgradeFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsItems.DIAMOND_MODIFIER.get())
            .addInput(Tags.Items.GEMS_DIAMOND)
            .addSalvage(Items.DIAMOND, 0.65f)
            .setMaxLevel(1)
            .setSlots(SlotType.UPGRADE, 1)
            .saveSalvage(consumer, prefix(TCIntegrationsItems.DIAMOND_MODIFIER, upgradeSalvage))
            .save(consumer, prefix(TCIntegrationsItems.DIAMOND_MODIFIER, upgradeFolder));
    }

}
