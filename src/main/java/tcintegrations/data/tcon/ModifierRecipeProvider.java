package tcintegrations.data.tcon;

import com.simibubi.create.AllBlocks;

import java.util.function.Consumer;

import com.simibubi.create.AllItems;
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
import tcintegrations.items.TCIntegrationsModifiers;

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
        Consumer<FinishedRecipe> createConsumer = withCondition(consumer, modLoaded(ModIntegration.CREATE_MODID));
        Consumer<FinishedRecipe> aquacultureConsumer = withCondition(consumer, modLoaded(ModIntegration.AQUACULTURE_MODID));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.TERRA_MODIFIER)
            .setTools(TinkerTags.Items.MELEE_PRIMARY)
            .addInput(ModTags.Items.INGOTS_TERRASTEEL)
            .addInput(ModTags.Items.INGOTS_TERRASTEEL)
            .addInput(ModTags.Items.MYSTICAL_FLOWERS)
            .addInput(ModTags.Items.DOUBLE_MYSTICAL_FLOWERS)
            .addInput(ModTags.Items.LIVINGWOOD_LOGS)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(botaniaConsumer, prefix(TCIntegrationsModifiers.TERRA_MODIFIER, compatSalvage))
            .save(botaniaConsumer, prefix(TCIntegrationsModifiers.TERRA_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ELEMENTAL_MODIFIER)
            .setTools(TinkerTags.Items.MELEE_PRIMARY)
            .addInput(ModTags.Items.INGOTS_ELEMENTIUM)
            .addInput(ModTags.Items.INGOTS_ELEMENTIUM)
            .addInput(ModTags.Items.MYSTICAL_FLOWERS)
            .addInput(ModTags.Items.DOUBLE_MYSTICAL_FLOWERS)
            .addInput(ModTags.Items.LIVINGWOOD_LOGS_GLIMMERING)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(botaniaConsumer, prefix(TCIntegrationsModifiers.ELEMENTAL_MODIFIER, compatSalvage))
            .save(botaniaConsumer, prefix(TCIntegrationsModifiers.ELEMENTAL_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER)
            .setTools(TinkerTags.Items.HELMETS)
            .addInput(ModTags.Items.INGOTS_TERRASTEEL)
            .addInput(ModTags.Items.INGOTS_TERRASTEEL)
            .addInput(ModTags.Items.INGOTS_TERRASTEEL)
            .addInput(ModItems.livingwoodTwig)
            .addInput(ModItems.runeSpring)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId() + "_helmets"), compatSalvage))
            .save(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId() + "_helmets"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER)
            .setTools(TinkerTags.Items.CHESTPLATES)
            .addInput(ModTags.Items.INGOTS_TERRASTEEL)
            .addInput(ModTags.Items.INGOTS_TERRASTEEL)
            .addInput(ModTags.Items.INGOTS_TERRASTEEL)
            .addInput(ModItems.livingwoodTwig)
            .addInput(ModItems.runeSummer)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId() + "_chestplates"), compatSalvage))
            .save(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId() + "_chestplates"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER)
            .setTools(TinkerTags.Items.LEGGINGS)
            .addInput(ModTags.Items.INGOTS_TERRASTEEL)
            .addInput(ModTags.Items.INGOTS_TERRASTEEL)
            .addInput(ModTags.Items.INGOTS_TERRASTEEL)
            .addInput(ModItems.livingwoodTwig)
            .addInput(ModItems.runeAutumn)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId() + "_leggings"), compatSalvage))
            .save(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId() + "_leggings"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER)
            .setTools(TinkerTags.Items.BOOTS)
            .addInput(ModTags.Items.INGOTS_TERRASTEEL)
            .addInput(ModTags.Items.INGOTS_TERRASTEEL)
            .addInput(ModTags.Items.INGOTS_TERRASTEEL)
            .addInput(ModItems.livingwoodTwig)
            .addInput(ModItems.runeWinter)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId() + "_boots"), compatSalvage))
            .save(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId() + "_boots"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.GREAT_FAIRY_MODIFIER)
            .setTools(TinkerTags.Items.ARMOR)
            .addInput(ModTags.Items.INGOTS_ELEMENTIUM)
            .addInput(ModTags.Items.INGOTS_ELEMENTIUM)
            .addInput(ModTags.Items.INGOTS_ELEMENTIUM)
            .addInput(ModItems.livingwoodTwig)
            .addInput(ModTags.Items.LIVINGWOOD_LOGS_GLIMMERING)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(botaniaConsumer, prefix(TCIntegrationsModifiers.GREAT_FAIRY_MODIFIER, compatSalvage))
            .save(botaniaConsumer, prefix(TCIntegrationsModifiers.GREAT_FAIRY_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.MECHANICAL_ARM_MODIFIER)
            .setTools(TinkerTags.Items.MELEE_PRIMARY)
            .addInput(AllBlocks.MECHANICAL_ARM.asStack().getItem())
            .setSlots(SlotType.ABILITY, 1)
            .setMaxLevel(1)
            .saveSalvage(createConsumer, prefix(TCIntegrationsModifiers.MECHANICAL_ARM_MODIFIER, compatSalvage))
            .save(createConsumer, prefix(TCIntegrationsModifiers.MECHANICAL_ARM_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ENGINEERS_GOGGLES_MODIFIER)
            .setTools(TinkerTags.Items.HELMETS)
            .addInput(AllItems.GOGGLES.get())
            .setSlots(SlotType.ABILITY, 1)
            .setMaxLevel(1)
            .saveSalvage(createConsumer, prefix(TCIntegrationsModifiers.ENGINEERS_GOGGLES_MODIFIER, compatSalvage))
            .save(createConsumer, prefix(TCIntegrationsModifiers.ENGINEERS_GOGGLES_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.POSEIDON_MODIFIER)
            .setTools(TinkerTags.Items.ARMOR)
            .addInput(ModIntegration.AQUACULTURE_NEPTUNIUM_INGOT)
            .addInput(ModIntegration.AQUACULTURE_NEPTUNIUM_INGOT)
            .addInput(ModIntegration.AQUACULTURE_NEPTUNIUM_INGOT)
            .addInput(ModIntegration.AQUACULTURE_TIN_CAN)
            .addInput(ModIntegration.AQUACULTURE_REDSTONE_HOOK)
            .setSlots(SlotType.ABILITY, 1)
            .setMaxLevel(1)
            .saveSalvage(aquacultureConsumer, prefix(TCIntegrationsModifiers.POSEIDON_MODIFIER, compatSalvage))
            .save(aquacultureConsumer, prefix(TCIntegrationsModifiers.POSEIDON_MODIFIER, compatFolder));
    }

    public ResourceLocation prefix(LazyModifier modifier, String prefix) {
        return prefix(modifier.getId(), prefix);
    }

}
