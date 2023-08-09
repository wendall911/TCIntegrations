package tcintegrations.data.tcon;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import com.github.alexthe666.iceandfire.item.IafItemRegistry;

import blusunrize.immersiveengineering.common.register.IEItems;

import com.github.alexthe666.alexsmobs.item.AMItemRegistry;

import com.hollingsworth.arsnouveau.setup.BlockRegistry;
import com.hollingsworth.arsnouveau.setup.ItemsRegistry;

import com.sammy.malum.registry.common.item.ItemRegistry;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;

import com.teammetallurgy.aquaculture.api.fishing.Hooks;
import com.teammetallurgy.aquaculture.init.AquaItems;

import mekanism.common.registries.MekanismItems;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;

import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.Tags;

import quek.undergarden.registry.UGItems;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.data.recipe.IRecipeHelper;
import slimeknights.tconstruct.library.modifiers.util.LazyModifier;
import slimeknights.tconstruct.library.recipe.modifiers.ModifierMatch;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IncrementalModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.tools.SlotType;

import vazkii.botania.common.item.ModItems;
import vazkii.botania.common.lib.ModTags;

import tcintegrations.common.TagManager;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.data.tcon.material.TciModifierIds;
import tcintegrations.items.TCIntegrationsModifiers;
import tcintegrations.TCIntegrations;

public class ModifierRecipeProvider extends RecipeProvider implements IConditionBuilder, IRecipeHelper {

    public ModifierRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "TCIntegrations - TCon Modifier Recipes";
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        addModifierRecipes(consumer);
    }

    @Override
    public String getModId() {
        return TCIntegrations.MODID;
    }

    private void addModifierRecipes(Consumer<FinishedRecipe> consumer) {
        // upgrades
        String compatFolder = "tools/modifiers/compat/";
        String compatSalvage = "tools/modifiers/salvage/compat/";
        Consumer<FinishedRecipe> botaniaConsumer = withCondition(consumer, modLoaded(ModIntegration.BOTANIA_MODID));
        Consumer<FinishedRecipe> createConsumer = withCondition(consumer, modLoaded(ModIntegration.CREATE_MODID));
        Consumer<FinishedRecipe> aquacultureConsumer = withCondition(consumer, modLoaded(ModIntegration.AQUACULTURE_MODID));
        Consumer<FinishedRecipe> arsConsumer = withCondition(consumer, modLoaded(ModIntegration.ARS_MODID));
        Consumer<FinishedRecipe> alexConsumer = withCondition(consumer, modLoaded(ModIntegration.ALEX_MODID));
        Consumer<FinishedRecipe> malumConsumer = withCondition(consumer, modLoaded(ModIntegration.MALUM_MODID));
        Consumer<FinishedRecipe> undergardenConsumer = withCondition(consumer, modLoaded(ModIntegration.UNDERGARDEN_MODID));
        Consumer<FinishedRecipe> beyondEarthConsumer = withCondition(consumer, modLoaded(ModIntegration.BEYOND_EARTH_MODID));
        Consumer<FinishedRecipe> ieConsumer = withCondition(consumer, modLoaded(ModIntegration.IE_MODID));
        Consumer<FinishedRecipe> mekanismConsumer = withCondition(consumer, modLoaded(ModIntegration.MEKANISM_MODID));
        Consumer<FinishedRecipe> mythicBotanyConsumer = withCondition(consumer, modLoaded(ModIntegration.MYTHIC_BOTANY_MODID));
        Consumer<FinishedRecipe> ifdConsumer = withCondition(consumer, modLoaded(ModIntegration.IFD_MODID));

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

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ALF_MODIFIER)
            .setTools(TinkerTags.Items.MELEE_PRIMARY)
            .addInput(mythicbotany.ModItems.alfsteelIngot)
            .setRequirements(ModifierMatch.entry(TCIntegrationsModifiers.TERRA_MODIFIER))
            .setSlots(SlotType.UPGRADE, 0)
            .setMaxLevel(1)
            .saveSalvage(mythicBotanyConsumer, prefix(TCIntegrationsModifiers.ALF_MODIFIER, compatSalvage))
            .save(mythicBotanyConsumer, prefix(TCIntegrationsModifiers.ALF_MODIFIER, compatFolder));

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

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ALFHEIM_MODIFIER)
            .setTools(TinkerTags.Items.ARMOR)
            .addInput(mythicbotany.ModItems.alfsteelIngot)
            .setRequirements(ModifierMatch.entry(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER))
            .setSlots(SlotType.UPGRADE, 0)
            .setMaxLevel(1)
            .saveSalvage(mythicBotanyConsumer, prefix(TCIntegrationsModifiers.ALFHEIM_MODIFIER, compatSalvage))
            .save(mythicBotanyConsumer, prefix(TCIntegrationsModifiers.ALFHEIM_MODIFIER, compatFolder));

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
            .setSlots(SlotType.ABILITY, 0)
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
            .setTools(ingredientFromTags(TinkerTags.Items.MELEE, TinkerTags.Items.HARVEST))
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

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.SOUL_STAINED_MODIFIER)
            .setTools(TinkerTags.Items.ARMOR)
            .addInput(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())
            .addInput(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())
            .addInput(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())
            .addInput(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())
            .addInput(ItemRegistry.SOUL_STAINED_STEEL_INGOT.get())
            .setSlots(SlotType.DEFENSE, 1)
            .setMaxLevel(1)
            .saveSalvage(malumConsumer, prefix(TCIntegrationsModifiers.SOUL_STAINED_MODIFIER, compatSalvage))
            .save(malumConsumer, prefix(TCIntegrationsModifiers.SOUL_STAINED_MODIFIER, compatFolder));

        IncrementalModifierRecipeBuilder.modifier(TciModifierIds.masticate)
            .setTools(TinkerTags.Items.ARMOR)
            .setInput(UGItems.MASTICATOR_SCALES.get(), 1, 2)
            .setMaxLevel(3)
            .setSlots(SlotType.UPGRADE, 1)
            .saveSalvage(undergardenConsumer, prefix(TCIntegrationsModifiers.MASTICATE_MODIFIER, compatSalvage))
            .save(undergardenConsumer, prefix(TCIntegrationsModifiers.MASTICATE_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.UTHERIUM_MODIFIER)
            .setTools(TinkerTags.Items.MELEE)
            .addInput(UGItems.UTHERIUM_CRYSTAL.get())
            .addInput(UGItems.UTHERIUM_CRYSTAL.get())
            .addInput(UGItems.UTHERIUM_CRYSTAL.get())
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(undergardenConsumer, prefix(TCIntegrationsModifiers.UTHERIUM_MODIFIER, compatSalvage))
            .save(undergardenConsumer, prefix(TCIntegrationsModifiers.UTHERIUM_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.FROSTSTEEL_MODIFIER)
            .setTools(ingredientFromTags(TinkerTags.Items.MELEE, TinkerTags.Items.HARVEST))
            .addInput(UGItems.FROSTSTEEL_INGOT.get())
            .addInput(UGItems.FROSTSTEEL_INGOT.get())
            .addInput(UGItems.FROSTSTEEL_INGOT.get())
            .addInput(UGItems.FROSTSTEEL_INGOT.get())
            .addInput(UGItems.FROSTSTEEL_INGOT.get())
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(undergardenConsumer, prefix(TCIntegrationsModifiers.FROSTSTEEL_MODIFIER, compatSalvage))
            .save(undergardenConsumer, prefix(TCIntegrationsModifiers.FROSTSTEEL_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.FORGOTTEN_MODIFIER)
            .setTools(ingredientFromTags(TinkerTags.Items.MELEE, TinkerTags.Items.HARVEST))
            .addInput(UGItems.FORGOTTEN_INGOT.get())
            .addInput(UGItems.CLOGGRUM_INGOT.get())
            .addInput(UGItems.CLOGGRUM_INGOT.get())
            .addInput(UGItems.CLOGGRUM_INGOT.get())
            .addInput(UGItems.CLOGGRUM_INGOT.get())
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(undergardenConsumer, prefix(TCIntegrationsModifiers.FORGOTTEN_MODIFIER, compatSalvage))
            .save(undergardenConsumer, prefix(TCIntegrationsModifiers.FORGOTTEN_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.CHEESY_MODIFIER)
            .setTools(ingredientFromTags(TinkerTags.Items.MELEE, TinkerTags.Items.HARVEST))
            .addInput(ModIntegration.BEYOND_EARTH_CHEESE)
            .addInput(ModIntegration.BEYOND_EARTH_CHEESE)
            .addInput(ModIntegration.BEYOND_EARTH_CHEESE)
            .addInput(ModIntegration.BEYOND_EARTH_CHEESE)
            .addInput(ModIntegration.BEYOND_EARTH_CHEESE)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(3)
            .saveSalvage(beyondEarthConsumer, prefix(TCIntegrationsModifiers.CHEESY_MODIFIER, compatSalvage))
            .save(beyondEarthConsumer, prefix(TCIntegrationsModifiers.CHEESY_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.MULTIVISION_MODIFIER)
            .setTools(TinkerTags.Items.HELMETS)
            .addInput(IEItems.Tools.VOLTMETER.get())
            .setSlots(SlotType.ABILITY, 0)
            .setMaxLevel(1)
            .saveSalvage(ieConsumer, prefix(TCIntegrationsModifiers.MULTIVISION_MODIFIER, compatSalvage))
            .save(ieConsumer, prefix(TCIntegrationsModifiers.MULTIVISION_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.GLOWUP_MODIFIER)
            .setTools(ingredientFromTags(TinkerTags.Items.MELEE, TinkerTags.Items.HARVEST))
            .addInput(MekanismItems.REFINED_GLOWSTONE_INGOT)
            .addInput(MekanismItems.REFINED_GLOWSTONE_INGOT)
            .addInput(MekanismItems.REFINED_GLOWSTONE_INGOT)
            .addInput(MekanismItems.REFINED_GLOWSTONE_INGOT)
            .addInput(MekanismItems.REFINED_GLOWSTONE_INGOT)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(3)
            .saveSalvage(mekanismConsumer, prefix(TCIntegrationsModifiers.GLOWUP_MODIFIER, compatSalvage))
            .save(mekanismConsumer, prefix(TCIntegrationsModifiers.GLOWUP_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.FLAMED_MODIFIER)
            .setTools(ingredientFromTags(TinkerTags.Items.MELEE, TinkerTags.Items.RANGED))
            .addInput(IafItemRegistry.DRAGON_BONE.get())
            .addInput(IafItemRegistry.DRAGON_BONE.get())
            .addInput(TagManager.Items.WITHER_BONES)
            .addInput(IafItemRegistry.FIRE_DRAGON_BLOOD.get())
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(ifdConsumer, prefix(TCIntegrationsModifiers.FLAMED_MODIFIER, compatSalvage))
            .save(ifdConsumer, prefix(TCIntegrationsModifiers.FLAMED_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ICED_MODIFIER)
            .setTools(ingredientFromTags(TinkerTags.Items.MELEE, TinkerTags.Items.RANGED))
            .addInput(IafItemRegistry.DRAGON_BONE.get())
            .addInput(IafItemRegistry.DRAGON_BONE.get())
            .addInput(TagManager.Items.WITHER_BONES)
            .addInput(IafItemRegistry.ICE_DRAGON_BLOOD.get())
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(ifdConsumer, prefix(TCIntegrationsModifiers.ICED_MODIFIER, compatSalvage))
            .save(ifdConsumer, prefix(TCIntegrationsModifiers.ICED_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ZAPPED_MODIFIER)
            .setTools(ingredientFromTags(TinkerTags.Items.MELEE, TinkerTags.Items.RANGED))
            .addInput(IafItemRegistry.DRAGON_BONE.get())
            .addInput(IafItemRegistry.DRAGON_BONE.get())
            .addInput(TagManager.Items.WITHER_BONES)
            .addInput(IafItemRegistry.LIGHTNING_DRAGON_BLOOD.get())
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(ifdConsumer, prefix(TCIntegrationsModifiers.ZAPPED_MODIFIER, compatSalvage))
            .save(ifdConsumer, prefix(TCIntegrationsModifiers.ZAPPED_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.PHANTASMAL_MODIFIER)
            .setTools(TinkerTags.Items.SWORD)
            .addInput(IafItemRegistry.DRAGON_BONE.get())
            .addInput(IafItemRegistry.DRAGON_BONE.get())
            .addInput(TagManager.Items.WITHER_BONES)
            .addInput(IafItemRegistry.GHOST_INGOT.get())
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(ifdConsumer, prefix(TCIntegrationsModifiers.PHANTASMAL_MODIFIER, compatSalvage))
            .save(ifdConsumer, prefix(TCIntegrationsModifiers.PHANTASMAL_MODIFIER, compatFolder));
    }

    public ResourceLocation prefix(LazyModifier modifier, String prefix) {
        return prefix(modifier.getId(), prefix);
    }

    @SafeVarargs
    private static Ingredient ingredientFromTags(TagKey<Item>... tags) {
        Ingredient[] tagIngredients = new Ingredient[tags.length];

        for (int i = 0; i < tags.length; i++) {
            tagIngredients[i] = Ingredient.of(tags[i]);
        }

        return CompoundIngredient.of(tagIngredients);
    }

}
