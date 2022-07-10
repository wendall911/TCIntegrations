package tcintegrations.data.tcon;

import java.util.function.Consumer;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.common.data.BaseRecipeProvider;
import slimeknights.tconstruct.library.modifiers.util.LazyModifier;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.tools.SlotType;

import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lib.ModTags;

import tcintegrations.data.integration.ModIntegration;
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
        String abilityFolder = "tools/modifiers/ability/";
        String compatFolder = "tools/modifiers/compat/";
        String compatSalvage = "tools/modifiers/salvage/compat/";

        Consumer<FinishedRecipe> botaniaConsumer = withCondition(consumer, modLoaded(ModIntegration.BOTANIA_MODID));

        ModifierRecipeBuilder.modifier(TCIntegrationsItems.TERRA_MODIFIER)
                .setTools(TinkerTags.Items.MELEE_PRIMARY)
                .addInput(ModTags.Items.INGOTS_TERRASTEEL)
                .addInput(ModTags.Items.INGOTS_TERRASTEEL)
                .addInput(ModTags.Items.MYSTICAL_FLOWERS)
                .addInput(ModTags.Items.DOUBLE_MYSTICAL_FLOWERS)
                .addInput(ModTags.Items.LIVINGWOOD_LOGS)
                .setSlots(SlotType.UPGRADE, 1)
                .setMaxLevel(1)
                .saveSalvage(botaniaConsumer, prefix(TCIntegrationsItems.TERRA_MODIFIER, compatSalvage))
                .save(botaniaConsumer, prefix(TCIntegrationsItems.TERRA_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsItems.ELEMENTAL_MODIFIER)
                .setTools(TinkerTags.Items.MELEE_PRIMARY)
                .addInput(ModTags.Items.INGOTS_ELEMENTIUM)
                .addInput(ModTags.Items.INGOTS_ELEMENTIUM)
                .addInput(ModTags.Items.MYSTICAL_FLOWERS)
                .addInput(ModTags.Items.DOUBLE_MYSTICAL_FLOWERS)
                .addInput(ModTags.Items.LIVINGWOOD_LOGS_GLIMMERING)
                .setSlots(SlotType.UPGRADE, 1)
                .setMaxLevel(1)
                .saveSalvage(botaniaConsumer, prefix(TCIntegrationsItems.ELEMENTAL_MODIFIER, compatSalvage))
                .save(botaniaConsumer, prefix(TCIntegrationsItems.ELEMENTAL_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsItems.TERRESTRIAL_MODIFIER)
                .setTools(TinkerTags.Items.HELMETS)
                .addInput(ModTags.Items.INGOTS_TERRASTEEL)
                .addInput(ModTags.Items.INGOTS_TERRASTEEL)
                .addInput(ModTags.Items.INGOTS_TERRASTEEL)
                .addInput(ModItems.livingwoodTwig)
                .addInput(ModItems.runeSpring)
                .setSlots(SlotType.UPGRADE, 1)
                .setMaxLevel(1)
                .saveSalvage(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsItems.TERRESTRIAL_MODIFIER.getId() + "_helmets"), compatSalvage))
                .save(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsItems.TERRESTRIAL_MODIFIER.getId() + "_helmets"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsItems.TERRESTRIAL_MODIFIER)
                .setTools(TinkerTags.Items.CHESTPLATES)
                .addInput(ModTags.Items.INGOTS_TERRASTEEL)
                .addInput(ModTags.Items.INGOTS_TERRASTEEL)
                .addInput(ModTags.Items.INGOTS_TERRASTEEL)
                .addInput(ModItems.livingwoodTwig)
                .addInput(ModItems.runeSummer)
                .setSlots(SlotType.UPGRADE, 1)
                .setMaxLevel(1)
                .saveSalvage(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsItems.TERRESTRIAL_MODIFIER.getId() + "_chestplates"), compatSalvage))
                .save(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsItems.TERRESTRIAL_MODIFIER.getId() + "_chestplates"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsItems.TERRESTRIAL_MODIFIER)
                .setTools(TinkerTags.Items.LEGGINGS)
                .addInput(ModTags.Items.INGOTS_TERRASTEEL)
                .addInput(ModTags.Items.INGOTS_TERRASTEEL)
                .addInput(ModTags.Items.INGOTS_TERRASTEEL)
                .addInput(ModItems.livingwoodTwig)
                .addInput(ModItems.runeAutumn)
                .setSlots(SlotType.UPGRADE, 1)
                .setMaxLevel(1)
                .saveSalvage(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsItems.TERRESTRIAL_MODIFIER.getId() + "_leggings"), compatSalvage))
                .save(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsItems.TERRESTRIAL_MODIFIER.getId() + "_leggings"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsItems.TERRESTRIAL_MODIFIER)
                .setTools(TinkerTags.Items.BOOTS)
                .addInput(ModTags.Items.INGOTS_TERRASTEEL)
                .addInput(ModTags.Items.INGOTS_TERRASTEEL)
                .addInput(ModTags.Items.INGOTS_TERRASTEEL)
                .addInput(ModItems.livingwoodTwig)
                .addInput(ModItems.runeWinter)
                .setSlots(SlotType.UPGRADE, 1)
                .setMaxLevel(1)
                .saveSalvage(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsItems.TERRESTRIAL_MODIFIER.getId() + "_boots"), compatSalvage))
                .save(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsItems.TERRESTRIAL_MODIFIER.getId() + "_boots"), compatFolder));
    }

    public ResourceLocation prefix(LazyModifier modifier, String prefix) {
        return prefix(modifier.getId(), prefix);
    }

}
