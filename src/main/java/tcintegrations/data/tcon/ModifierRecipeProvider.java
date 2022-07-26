package tcintegrations.data.tcon;

import java.util.function.Consumer;

import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;

import com.teammetallurgy.aquaculture.api.fishing.Hooks;
import com.teammetallurgy.aquaculture.init.AquaItems;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;

import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.common.data.BaseRecipeProvider;
import slimeknights.tconstruct.library.modifiers.util.LazyModifier;
import slimeknights.tconstruct.library.recipe.modifiers.ModifierMatch;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.tools.SlotType;

import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lib.ModTags;

import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.TCIntegrationsModifiers;
import tcintegrations.TCIntegrations;

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
        Consumer<FinishedRecipe> arsConsumer = withCondition(consumer, modLoaded(ModIntegration.ARS_MODID));
        Consumer<FinishedRecipe> alexConsumer = withCondition(consumer, modLoaded(ModIntegration.ALEX_MODID));

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
            .addInput(AquaItems.NEPTUNIUM_INGOT.get())
            .addInput(AquaItems.NEPTUNIUM_INGOT.get())
            .addInput(AquaItems.NEPTUNIUM_INGOT.get())
            .addInput(AquaItems.TIN_CAN.get())
            .addInput(Hooks.REDSTONE.getItem())
            .setSlots(SlotType.ABILITY, 1)
            .setMaxLevel(1)
            .saveSalvage(aquacultureConsumer, prefix(TCIntegrationsModifiers.POSEIDON_MODIFIER, compatSalvage))
            .save(aquacultureConsumer, prefix(TCIntegrationsModifiers.POSEIDON_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.SIREN_MODIFIER)
            .setTools(TinkerTags.Items.MELEE_OR_HARVEST)
            .addInput(AquaItems.NEPTUNIUM_INGOT.get())
            .addInput(AquaItems.NEPTUNIUM_INGOT.get())
            .addInput(AquaItems.NEPTUNIUM_INGOT.get())
            .addInput(AquaItems.FISH_FILLET.get())
            .addInput(Hooks.IRON.getItem())
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(aquacultureConsumer, prefix(TCIntegrationsModifiers.SIREN_MODIFIER, compatSalvage))
            .save(aquacultureConsumer, prefix(TCIntegrationsModifiers.SIREN_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ARS_MODIFIER)
            .setTools(TinkerTags.Items.ARMOR)
            .addInput(ItemsRegistry.MAGE_FIBER)
            .addInput(ItemsRegistry.MAGE_FIBER)
            .addInput(ItemsRegistry.MAGE_FIBER)
            .addInput(ItemsRegistry.MAGE_FIBER)
            .addInput(ItemsRegistry.MAGE_FIBER)
            .setSalvageLevelRange(1, 1)
            .setMaxLevel(1)
            .setSlots(SlotType.UPGRADE, 1)
            .saveSalvage(arsConsumer, wrap(TCIntegrationsModifiers.ARS_MODIFIER.getId(), compatSalvage, "_level_1"))
            .save(arsConsumer, wrap(TCIntegrationsModifiers.ARS_MODIFIER.getId(), compatFolder, "_level_1"));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ARS_MODIFIER)
            .setTools(TinkerTags.Items.ARMOR)
            .addInput(ItemsRegistry.BLAZE_FIBER)
            .addInput(ItemsRegistry.BLAZE_FIBER)
            .addInput(ItemsRegistry.BLAZE_FIBER)
            .addInput(ItemsRegistry.BLAZE_FIBER)
            .addInput(ItemsRegistry.BLAZE_FIBER)
            .setSalvageLevelRange(2, 2)
            .setMaxLevel(2)
            .setSlots(SlotType.UPGRADE, 1)
            .setRequirements(ModifierMatch.entry(TCIntegrationsModifiers.ARS_MODIFIER, 1))
            .setRequirementsError("recipe." + TCIntegrations.MODID + ".modifier." + TCIntegrationsModifiers.ARS_MODIFIER.getId().getPath() + ".level_2")
            .saveSalvage(arsConsumer, wrap(TCIntegrationsModifiers.ARS_MODIFIER.getId(), compatSalvage, "_level_2"))
            .save(arsConsumer, wrap(TCIntegrationsModifiers.ARS_MODIFIER.getId(), compatFolder, "_level_2"));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ARS_MODIFIER)
            .setTools(TinkerTags.Items.ARMOR)
            .addInput(ItemsRegistry.END_FIBER)
            .addInput(ItemsRegistry.END_FIBER)
            .addInput(ItemsRegistry.END_FIBER)
            .addInput(ItemsRegistry.END_FIBER)
            .addInput(ItemsRegistry.END_FIBER)
            .setSalvageLevelRange(3, 3)
            .setMaxLevel(3)
            .setSlots(SlotType.UPGRADE, 1)
            .setRequirements(ModifierMatch.entry(TCIntegrationsModifiers.ARS_MODIFIER, 2))
            .setRequirementsError("recipe." + TCIntegrations.MODID + ".modifier." + TCIntegrationsModifiers.ARS_MODIFIER.getId().getPath() + ".level_3")
            .saveSalvage(arsConsumer, wrap(TCIntegrationsModifiers.ARS_MODIFIER.getId(), compatSalvage, "_level_3"))
            .save(arsConsumer, wrap(TCIntegrationsModifiers.ARS_MODIFIER.getId(), compatFolder, "_level_3"));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ENCHANTERS_SHIELD_MODIFIER)
            .setTools(TinkerTags.Items.CHESTPLATES)
            .addInput(BlockRegistry.SOURCE_GEM_BLOCK)
            .addInput(BlockRegistry.SOURCE_GEM_BLOCK)
            .addInput(Ingredient.of(Tags.Items.STORAGE_BLOCKS_GOLD))
            .addInput(Ingredient.of(Tags.Items.STORAGE_BLOCKS_GOLD))
            .addInput(Items.SHIELD)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(arsConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.ENCHANTERS_SHIELD_MODIFIER.getId() + "_chestplates"), compatSalvage))
            .save(arsConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.ENCHANTERS_SHIELD_MODIFIER.getId() + "_chestplates"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ROADRUNNER_MODIFIER)
            .setTools(TinkerTags.Items.BOOTS)
            .addInput(Items.CHISELED_SANDSTONE)
            .addInput(AMItemRegistry.ROADRUNNER_FEATHER.get())
            .addInput(AMItemRegistry.ROADRUNNER_FEATHER.get())
            .addInput(AMItemRegistry.ROADRUNNER_FEATHER.get())
            .addInput(AMItemRegistry.ROADRUNNER_FEATHER.get())
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(3)
            .saveSalvage(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.ROADRUNNER_MODIFIER.getId() + "_boots"), compatSalvage))
            .save(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.ROADRUNNER_MODIFIER.getId() + "_boots"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.FRONTIER_CAP_MODIFIER)
            .setTools(TinkerTags.Items.HELMETS)
            .addInput(AMItemRegistry.BEAR_FUR.get())
            .addInput(AMItemRegistry.BEAR_FUR.get())
            .addInput(AMItemRegistry.BEAR_FUR.get())
            .addInput(AMItemRegistry.BEAR_FUR.get())
            .addInput(AMItemRegistry.RACCOON_TAIL.get())
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(3)
            .saveSalvage(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.FRONTIER_CAP_MODIFIER.getId() + "_helmets"), compatSalvage))
            .save(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.FRONTIER_CAP_MODIFIER.getId() + "_helmets"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.TURTLE_SHELL_MODIFIER)
            .setTools(TinkerTags.Items.HELMETS)
            .addInput(AMItemRegistry.SPIKED_SCUTE.get())
            .addInput(AMItemRegistry.SPIKED_SCUTE.get())
            .addInput(AMItemRegistry.SPIKED_SCUTE.get())
            .addInput(AMItemRegistry.SPIKED_SCUTE.get())
            .addInput(AMItemRegistry.SPIKED_SCUTE.get())
            .setSlots(SlotType.ABILITY, 1)
            .setMaxLevel(1)
            .saveSalvage(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TURTLE_SHELL_MODIFIER.getId() + "_helmets"), compatSalvage))
            .save(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TURTLE_SHELL_MODIFIER.getId() + "_helmets"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.BISON_FUR_MODIFIER)
            .setTools(TinkerTags.Items.BOOTS)
            .addInput(AMItemRegistry.BISON_FUR.get())
            .addInput(AMItemRegistry.BISON_FUR.get())
            .addInput(AMItemRegistry.BISON_FUR.get())
            .addInput(AMItemRegistry.BISON_FUR.get())
            .addInput(AMItemRegistry.BISON_FUR.get())
            .setSlots(SlotType.ABILITY, 1)
            .setMaxLevel(1)
            .saveSalvage(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.BISON_FUR_MODIFIER.getId() + "_boots"), compatSalvage))
            .save(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.BISON_FUR_MODIFIER.getId() + "_boots"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.SHIELD_OF_THE_DEEP_MODIFIER)
            .setTools(TinkerTags.Items.CHESTPLATES)
            .addInput(Items.HEART_OF_THE_SEA)
            .addInput(Items.PRISMARINE_BRICKS)
            .addInput(AMItemRegistry.SERRATED_SHARK_TOOTH.get())
            .addInput(AMItemRegistry.SHARK_TOOTH.get())
            .addInput(AMItemRegistry.SHARK_TOOTH.get())
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.SHIELD_OF_THE_DEEP_MODIFIER.getId() + "_chestplates"), compatSalvage))
            .save(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.SHIELD_OF_THE_DEEP_MODIFIER.getId() + "_chestplates"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.MOSQUITO_MODIFIER)
            .setTools(TinkerTags.Items.ARMOR)
            .addInput(AMItemRegistry.MOSQUITO_PROBOSCIS.get())
            .addInput(AMItemRegistry.MOSQUITO_PROBOSCIS.get())
            .addInput(AMItemRegistry.MOSQUITO_PROBOSCIS.get())
            .addInput(AMItemRegistry.MOSQUITO_PROBOSCIS.get())
            .addInput(AMItemRegistry.MOSQUITO_PROBOSCIS.get())
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(alexConsumer, prefix(TCIntegrationsModifiers.MOSQUITO_MODIFIER, compatSalvage))
            .save(alexConsumer, prefix(TCIntegrationsModifiers.MOSQUITO_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.CROCODILE_MODIFIER)
            .setTools(TinkerTags.Items.CHESTPLATES)
            .addInput(AMItemRegistry.CROCODILE_SCUTE.get())
            .addInput(AMItemRegistry.CROCODILE_SCUTE.get())
            .addInput(AMItemRegistry.CROCODILE_SCUTE.get())
            .addInput(AMItemRegistry.CROCODILE_SCUTE.get())
            .addInput(AMItemRegistry.CROCODILE_SCUTE.get())
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.CROCODILE_MODIFIER.getId() + "_chestplates"), compatSalvage))
            .save(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.CROCODILE_MODIFIER.getId() + "_chestplates"), compatFolder));
    }

    public ResourceLocation prefix(LazyModifier modifier, String prefix) {
        return prefix(modifier.getId(), prefix);
    }

}
