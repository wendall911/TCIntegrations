package tcintegrations.data.tcon;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.crafting.CompoundIngredient;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.recipe.data.IRecipeHelper;
import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.util.LazyModifier;
import slimeknights.tconstruct.library.recipe.modifiers.adding.IncrementalModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.ModifierRecipeBuilder;
import slimeknights.tconstruct.library.recipe.modifiers.adding.MultilevelModifierRecipeBuilder;
import slimeknights.tconstruct.library.tools.SlotType;
import tcintegrations.TCIntegrations;
import tcintegrations.common.TagManager;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.data.tcon.material.TciModifierIds;
import tcintegrations.items.TCIntegrationsModifiers;

import java.util.function.Consumer;

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
        Consumer<FinishedRecipe> cheeseConsumer = withCondition(consumer, tagCondition(TagManager.Items.CHEESE.location().getPath()));
        Consumer<FinishedRecipe> batteryConsumer =  withCondition(consumer, tagCondition(TagManager.Items.BATTERY_ITEMS.location().getPath()));
        Consumer<FinishedRecipe> solarPanelConsumer = withCondition(consumer, tagCondition(TagManager.Items.SOLAR_PANEL_ITEMS.location().getPath()));
        Consumer<FinishedRecipe> ieConsumer = withCondition(consumer, modLoaded(ModIntegration.IE_MODID));
        Consumer<FinishedRecipe> mekanismConsumer = withCondition(consumer, modLoaded(ModIntegration.MEKANISM_MODID));
        Consumer<FinishedRecipe> mythicBotanyConsumer = withCondition(consumer, modLoaded(ModIntegration.MYTHIC_BOTANY_MODID));
        Consumer<FinishedRecipe> ifdConsumer = withCondition(consumer, modLoaded(ModIntegration.IFD_MODID));
        Consumer<FinishedRecipe> aeConsumer = withCondition(consumer, modLoaded(ModIntegration.ARS_ELEMENTAL_MODID));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.TERRA_MODIFIER)
                .setTools(TinkerTags.Items.MELEE_PRIMARY)
                .addInput(TagManager.Items.INGOTS_TERRASTEEL)
                .addInput(TagManager.Items.INGOTS_TERRASTEEL)
                .addInput(TagManager.Items.MYSTICAL_FLOWERS)
                .addInput(TagManager.Items.DOUBLE_MYSTICAL_FLOWERS)
                .addInput(TagManager.Items.LIVINGWOOD_LOGS)
                .setSlots(SlotType.UPGRADE, 1)
                .setMaxLevel(1)
                .saveSalvage(botaniaConsumer, prefix(TCIntegrationsModifiers.TERRA_MODIFIER, compatSalvage))
                .save(botaniaConsumer, prefix(TCIntegrationsModifiers.TERRA_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ENERGY_HANDLER)
                .setTools(TinkerTags.Items.MODIFIABLE)
                .addInput(TagManager.Items.BATTERY_ITEMS)
                .setSlots(SlotType.UPGRADE, 1)
                .saveSalvage(batteryConsumer, prefix(TCIntegrationsModifiers.ENERGY_HANDLER, compatSalvage))
                .save(batteryConsumer, prefix(TCIntegrationsModifiers.ENERGY_HANDLER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ENERGY_REPAIR_MODIFIER)
                .setTools(TinkerTags.Items.DURABILITY)
                .addInput(TagManager.Items.BATTERY_ITEMS)
                .addInput(Items.REDSTONE_BLOCK)
                .addInput(Items.END_CRYSTAL)
                .setSlots(SlotType.UPGRADE, 1)
                .setMaxLevel(3)
                .saveSalvage(batteryConsumer, prefix(TCIntegrationsModifiers.ENERGY_REPAIR_MODIFIER, compatSalvage))
                .save(batteryConsumer, prefix(TCIntegrationsModifiers.ENERGY_REPAIR_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ENERGY_DISPATCHER_MODIFIER)
                .setTools(TinkerTags.Items.MODIFIABLE)
                .addInput(TagManager.Items.BATTERY_ITEMS)
                .addInput(Items.COMPARATOR)
                .setSlots(SlotType.ABILITY, 1)
                .setMaxLevel(1)
                .saveSalvage(batteryConsumer, prefix(TCIntegrationsModifiers.ENERGY_DISPATCHER_MODIFIER, compatSalvage))
                .save(batteryConsumer, prefix(TCIntegrationsModifiers.ENERGY_DISPATCHER_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.DISCHARGE_MODIFIER)    //level 1
                .setTools(TinkerTags.Items.MELEE_PRIMARY)
                .addInput(TagManager.Items.BATTERY_ITEMS)
                .addInput(Items.LIGHTNING_ROD)
                .addInput(Items.LIGHTNING_ROD)
                .setSlots(SlotType.ABILITY, 1)
                .setMaxLevel(1)
                .disallowCrystal()
                .save(batteryConsumer, wrap(TCIntegrationsModifiers.DISCHARGE_MODIFIER, compatFolder,"_level_1"));
        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.DISCHARGE_MODIFIER)    //salvage
                .setTools(TinkerTags.Items.MELEE_PRIMARY)
                .exactLevel(1)
                .useSalvageMax()
                .setSlots(SlotType.ABILITY, 1)
                .saveSalvage(consumer, prefix(TCIntegrationsModifiers.DISCHARGE_MODIFIER, compatSalvage));
        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.DISCHARGE_MODIFIER)    //level 2+
                .setTools(TinkerTags.Items.MELEE_PRIMARY)
                .addInput(TagManager.Items.BATTERY_ITEMS)
                .addInput(TagManager.Items.BATTERY_ITEMS)
                .addInput(TagManager.Items.BATTERY_ITEMS)
                .addInput(TagManager.Items.BATTERY_ITEMS)
                .addInput(TagManager.Items.BATTERY_ITEMS)
                .setLevelRange(2, 3)
                .disallowCrystal()
                .save(batteryConsumer, wrap(TCIntegrationsModifiers.DISCHARGE_MODIFIER, compatFolder,"_level_2plus"));
        MultilevelModifierRecipeBuilder.modifier(TCIntegrationsModifiers.DISCHARGE_MODIFIER.getId()) //crystal
                .setTools(TinkerTags.Items.MELEE_PRIMARY)
                .addLevel(SlotType.ABILITY, 1, 1)
                .addLevelRange(2, 3)
                .save(batteryConsumer, wrap(TCIntegrationsModifiers.DISCHARGE_MODIFIER, compatFolder,"_crystal"));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.SOLAR_PANEL_HAT_MODIFIER)
                .setTools(TinkerTags.Items.HELMETS)
                .addInput(TagManager.Items.SOLAR_PANEL_ITEMS)
                .addInput(TagManager.Items.SOLAR_PANEL_ITEMS)
                .addInput(TagManager.Items.SOLAR_PANEL_ITEMS)
                .addInput(TagManager.Items.SOLAR_PANEL_ITEMS)
                .addInput(TagManager.Items.SOLAR_PANEL_ITEMS)
                .setSlots(SlotType.ABILITY, 1)
                .setMaxLevel(4)
                .saveSalvage(solarPanelConsumer, prefix(TCIntegrationsModifiers.SOLAR_PANEL_HAT_MODIFIER, compatSalvage))
                .save(solarPanelConsumer, prefix(TCIntegrationsModifiers.SOLAR_PANEL_HAT_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ALF_MODIFIER)
            .setTools(TinkerTags.Items.MELEE_PRIMARY)
            .addInput(ModIntegration.ALFSTEEL_INGOT)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(mythicBotanyConsumer, prefix(TCIntegrationsModifiers.ALF_MODIFIER, compatSalvage))
            .save(mythicBotanyConsumer, prefix(TCIntegrationsModifiers.ALF_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ALFHEIM_MODIFIER)
            .setTools(TinkerTags.Items.WORN_ARMOR)
            .addInput(ModIntegration.ALFSTEEL_INGOT)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(mythicBotanyConsumer, prefix(TCIntegrationsModifiers.ALFHEIM_MODIFIER, compatSalvage))
            .save(mythicBotanyConsumer, prefix(TCIntegrationsModifiers.ALFHEIM_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ELEMENTAL_MODIFIER)
            .setTools(TinkerTags.Items.MELEE_PRIMARY)
            .addInput(TagManager.Items.INGOTS_ELEMENTIUM)
            .addInput(TagManager.Items.INGOTS_ELEMENTIUM)
            .addInput(TagManager.Items.MYSTICAL_FLOWERS)
            .addInput(TagManager.Items.DOUBLE_MYSTICAL_FLOWERS)
            .addInput(TagManager.Items.LIVINGWOOD_LOGS_GLIMMERING)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(botaniaConsumer, prefix(TCIntegrationsModifiers.ELEMENTAL_MODIFIER, compatSalvage))
            .save(botaniaConsumer, prefix(TCIntegrationsModifiers.ELEMENTAL_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER)
            .setTools(TinkerTags.Items.HELMETS)
            .addInput(TagManager.Items.INGOTS_TERRASTEEL)
            .addInput(TagManager.Items.INGOTS_TERRASTEEL)
            .addInput(TagManager.Items.INGOTS_TERRASTEEL)
            .addInput(ModIntegration.LIVINGWOOD_TWIG)
            .addInput(ModIntegration.RUNE_SPRING)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId() + "_helmets"), compatSalvage))
            .save(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId() + "_helmets"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER)
            .setTools(TinkerTags.Items.CHESTPLATES)
            .addInput(TagManager.Items.INGOTS_TERRASTEEL)
            .addInput(TagManager.Items.INGOTS_TERRASTEEL)
            .addInput(TagManager.Items.INGOTS_TERRASTEEL)
            .addInput(ModIntegration.LIVINGWOOD_TWIG)
            .addInput(ModIntegration.RUNE_SUMMER)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId() + "_chestplates"), compatSalvage))
            .save(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId() + "_chestplates"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER)
            .setTools(TinkerTags.Items.LEGGINGS)
            .addInput(TagManager.Items.INGOTS_TERRASTEEL)
            .addInput(TagManager.Items.INGOTS_TERRASTEEL)
            .addInput(TagManager.Items.INGOTS_TERRASTEEL)
            .addInput(ModIntegration.LIVINGWOOD_TWIG)
            .addInput(ModIntegration.RUNE_AUTUMN)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId() + "_leggings"), compatSalvage))
            .save(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId() + "_leggings"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER)
            .setTools(TinkerTags.Items.BOOTS)
            .addInput(TagManager.Items.INGOTS_TERRASTEEL)
            .addInput(TagManager.Items.INGOTS_TERRASTEEL)
            .addInput(TagManager.Items.INGOTS_TERRASTEEL)
            .addInput(ModIntegration.LIVINGWOOD_TWIG)
            .addInput(ModIntegration.RUNE_WINTER)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId() + "_boots"), compatSalvage))
            .save(botaniaConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId() + "_boots"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.GREAT_FAIRY_MODIFIER)
            .setTools(TinkerTags.Items.WORN_ARMOR)
            .addInput(TagManager.Items.INGOTS_ELEMENTIUM)
            .addInput(TagManager.Items.INGOTS_ELEMENTIUM)
            .addInput(TagManager.Items.INGOTS_ELEMENTIUM)
            .addInput(ModIntegration.LIVINGWOOD_TWIG)
            .addInput(TagManager.Items.LIVINGWOOD_LOGS_GLIMMERING)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(botaniaConsumer, prefix(TCIntegrationsModifiers.GREAT_FAIRY_MODIFIER, compatSalvage))
            .save(botaniaConsumer, prefix(TCIntegrationsModifiers.GREAT_FAIRY_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TciModifierIds.engineersGoggles)
            .setTools(TinkerTags.Items.HELMETS)
            .addInput(ModIntegration.GOGGLES)
            .setMaxLevel(1)
            .saveSalvage(createConsumer, prefix(TciModifierIds.engineersGoggles, compatSalvage))
            .save(createConsumer, prefix(TciModifierIds.engineersGoggles, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.MECHANICAL_ARM_MODIFIER)
            .setTools(TinkerTags.Items.MELEE_PRIMARY)
            .addInput(ModIntegration.MECHANICAL_ARM)
            .setSlots(SlotType.ABILITY, 1)
            .setMaxLevel(1)
            .saveSalvage(createConsumer, prefix(TCIntegrationsModifiers.MECHANICAL_ARM_MODIFIER, compatSalvage))
            .save(createConsumer, prefix(TCIntegrationsModifiers.MECHANICAL_ARM_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.POSEIDON_MODIFIER)
            .setTools(TinkerTags.Items.WORN_ARMOR)
            .addInput(ModIntegration.NEPTUNIUM_INGOT)
            .addInput(ModIntegration.NEPTUNIUM_INGOT)
            .addInput(ModIntegration.NEPTUNIUM_INGOT)
            .addInput(ModIntegration.TIN_CAN)
            .addInput(ModIntegration.REDSTONE_HOOK)
            .setSlots(SlotType.ABILITY, 1)
            .setMaxLevel(1)
            .saveSalvage(aquacultureConsumer, prefix(TCIntegrationsModifiers.POSEIDON_MODIFIER, compatSalvage))
            .save(aquacultureConsumer, prefix(TCIntegrationsModifiers.POSEIDON_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.SIREN_MODIFIER)
            .setTools(ingredientFromTags(TinkerTags.Items.MELEE, TinkerTags.Items.HARVEST))
            .addInput(ModIntegration.NEPTUNIUM_INGOT)
            .addInput(ModIntegration.NEPTUNIUM_INGOT)
            .addInput(ModIntegration.NEPTUNIUM_INGOT)
            .addInput(ModIntegration.FISH_FILLET_RAW)
            .addInput(ModIntegration.IRON_HOOK)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(aquacultureConsumer, prefix(TCIntegrationsModifiers.SIREN_MODIFIER, compatSalvage))
            .save(aquacultureConsumer, prefix(TCIntegrationsModifiers.SIREN_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ARS_MODIFIER)
            .setTools(TinkerTags.Items.WORN_ARMOR)
            .addInput(ModIntegration.MAGE_FIBER)
            .addInput(ModIntegration.MAGE_FIBER)
            .addInput(ModIntegration.MAGE_FIBER)
            .addInput(ModIntegration.MAGE_FIBER)
            .addInput(ModIntegration.MAGE_FIBER)
            .setMaxLevel(1)
            .setSlots(SlotType.UPGRADE, 1)
            .disallowCrystal()
            .saveSalvage(arsConsumer, wrap(TCIntegrationsModifiers.ARS_MODIFIER.getId(), compatSalvage, "_level_1"))
            .save(arsConsumer, wrap(TCIntegrationsModifiers.ARS_MODIFIER.getId(), compatFolder, "_level_1"));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ARS_MODIFIER)
            .setTools(TinkerTags.Items.WORN_ARMOR)
            .addInput(ModIntegration.BLAZE_FIBER)
            .addInput(ModIntegration.BLAZE_FIBER)
            .addInput(ModIntegration.BLAZE_FIBER)
            .addInput(ModIntegration.BLAZE_FIBER)
            .addInput(ModIntegration.BLAZE_FIBER)
            .exactLevel(2)
            .setSlots(SlotType.UPGRADE, 1)
            .disallowCrystal()
            .saveSalvage(arsConsumer, wrap(TCIntegrationsModifiers.ARS_MODIFIER.getId(), compatSalvage, "_level_2"))
            .save(arsConsumer, wrap(TCIntegrationsModifiers.ARS_MODIFIER.getId(), compatFolder, "_level_2"));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ARS_MODIFIER)
            .setTools(TinkerTags.Items.WORN_ARMOR)
            .addInput(ModIntegration.END_FIBER)
            .addInput(ModIntegration.END_FIBER)
            .addInput(ModIntegration.END_FIBER)
            .addInput(ModIntegration.END_FIBER)
            .addInput(ModIntegration.END_FIBER)
            .exactLevel(3)
            .setSlots(SlotType.UPGRADE, 1)
            .disallowCrystal()
            .saveSalvage(arsConsumer, wrap(TCIntegrationsModifiers.ARS_MODIFIER.getId(), compatSalvage, "_level_3"))
            .save(arsConsumer, wrap(TCIntegrationsModifiers.ARS_MODIFIER.getId(), compatFolder, "_level_3"));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.PYROMANCER_MODIFIER)
            .setTools(TinkerTags.Items.WORN_ARMOR)
            .setMaxLevel(1).checkTraitLevel()
            .setSlots(SlotType.ABILITY,1).addInput(ModIntegration.ARS_ELEMENTAL_MASTER_CORE)
            .addInput(Tags.Items.INGOTS_NETHERITE)
            .addInput(ModIntegration.ARS_FIRE_ESSENCE)
            .addInput(ModIntegration.ARS_FIRE_ESSENCE)
            .addInput(ModIntegration.ARS_FIRE_ESSENCE)
            .saveSalvage(aeConsumer, prefix(TCIntegrationsModifiers.PYROMANCER_MODIFIER, compatSalvage))
            .save(aeConsumer, prefix(TCIntegrationsModifiers.PYROMANCER_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.AQUAMANCER_MODIFIER)
            .setTools(TinkerTags.Items.WORN_ARMOR)
            .setMaxLevel(1).checkTraitLevel()
            .setSlots(SlotType.ABILITY,1).addInput(ModIntegration.ARS_ELEMENTAL_MASTER_CORE)
            .addInput(Tags.Items.INGOTS_NETHERITE)
            .addInput(ModIntegration.ARS_WATER_ESSENCE)
            .addInput(ModIntegration.ARS_WATER_ESSENCE)
            .addInput(ModIntegration.ARS_WATER_ESSENCE)
            .saveSalvage(aeConsumer, prefix(TCIntegrationsModifiers.AQUAMANCER_MODIFIER, compatSalvage))
            .save(aeConsumer, prefix(TCIntegrationsModifiers.AQUAMANCER_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.GEOMANCER_MODIFIER)
            .setTools(TinkerTags.Items.WORN_ARMOR)
            .setMaxLevel(1).checkTraitLevel()
            .setSlots(SlotType.ABILITY,1).addInput(ModIntegration.ARS_ELEMENTAL_MASTER_CORE)
            .addInput(Tags.Items.INGOTS_NETHERITE)
            .addInput(ModIntegration.ARS_EARTH_ESSENCE)
            .addInput(ModIntegration.ARS_EARTH_ESSENCE)
            .addInput(ModIntegration.ARS_EARTH_ESSENCE)
            .saveSalvage(aeConsumer, prefix(TCIntegrationsModifiers.GEOMANCER_MODIFIER, compatSalvage))
            .save(aeConsumer, prefix(TCIntegrationsModifiers.GEOMANCER_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.AEROMANCER_MODIFIER)
            .setTools(TinkerTags.Items.WORN_ARMOR)
            .setMaxLevel(1).checkTraitLevel()
            .setSlots(SlotType.ABILITY,1).addInput(ModIntegration.ARS_ELEMENTAL_MASTER_CORE)
            .addInput(Tags.Items.INGOTS_NETHERITE)
            .addInput(ModIntegration.ARS_AIR_ESSENCE)
            .addInput(ModIntegration.ARS_AIR_ESSENCE)
            .addInput(ModIntegration.ARS_AIR_ESSENCE)
            .saveSalvage(aeConsumer, prefix(TCIntegrationsModifiers.AEROMANCER_MODIFIER, compatSalvage))
            .save(aeConsumer, prefix(TCIntegrationsModifiers.AEROMANCER_MODIFIER, compatFolder));



        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ENCHANTERS_SHIELD_MODIFIER)
            .setTools(TinkerTags.Items.CHESTPLATES)
            .addInput(ModIntegration.SOURCE_GEM_BLOCK)
            .addInput(ModIntegration.SOURCE_GEM_BLOCK)
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
            .addInput(ModIntegration.ROADRUNNER_FEATHER)
            .addInput(ModIntegration.ROADRUNNER_FEATHER)
            .addInput(ModIntegration.ROADRUNNER_FEATHER)
            .addInput(ModIntegration.ROADRUNNER_FEATHER)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(3)
            .saveSalvage(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.ROADRUNNER_MODIFIER.getId() + "_boots"), compatSalvage))
            .save(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.ROADRUNNER_MODIFIER.getId() + "_boots"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.FRONTIER_CAP_MODIFIER)
            .setTools(TinkerTags.Items.HELMETS)
            .addInput(ModIntegration.BEAR_FUR)
            .addInput(ModIntegration.BEAR_FUR)
            .addInput(ModIntegration.BEAR_FUR)
            .addInput(ModIntegration.BEAR_FUR)
            .addInput(ModIntegration.RACCOON_TAIL)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(3)
            .saveSalvage(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.FRONTIER_CAP_MODIFIER.getId() + "_helmets"), compatSalvage))
            .save(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.FRONTIER_CAP_MODIFIER.getId() + "_helmets"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.TURTLE_SHELL_MODIFIER)
            .setTools(TinkerTags.Items.HELMETS)
            .addInput(ModIntegration.SPIKED_SCUTE)
            .addInput(ModIntegration.SPIKED_SCUTE)
            .addInput(ModIntegration.SPIKED_SCUTE)
            .addInput(ModIntegration.SPIKED_SCUTE)
            .addInput(ModIntegration.SPIKED_SCUTE)
            .setSlots(SlotType.ABILITY, 1)
            .setMaxLevel(1)
            .saveSalvage(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TURTLE_SHELL_MODIFIER.getId() + "_helmets"), compatSalvage))
            .save(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.TURTLE_SHELL_MODIFIER.getId() + "_helmets"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.BISON_FUR_MODIFIER)
            .setTools(TinkerTags.Items.BOOTS)
            .addInput(ModIntegration.BISON_FUR)
            .addInput(ModIntegration.BISON_FUR)
            .addInput(ModIntegration.BISON_FUR)
            .addInput(ModIntegration.BISON_FUR)
            .addInput(ModIntegration.BISON_FUR)
            .setSlots(SlotType.ABILITY, 1)
            .setMaxLevel(1)
            .saveSalvage(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.BISON_FUR_MODIFIER.getId() + "_boots"), compatSalvage))
            .save(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.BISON_FUR_MODIFIER.getId() + "_boots"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.SHIELD_OF_THE_DEEP_MODIFIER)
            .setTools(TinkerTags.Items.CHESTPLATES)
            .addInput(Items.HEART_OF_THE_SEA)
            .addInput(Items.PRISMARINE_BRICKS)
            .addInput(ModIntegration.SERRATED_SHARK_TOOTH)
            .addInput(ModIntegration.SHARK_TOOTH)
            .addInput(ModIntegration.SHARK_TOOTH)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.SHIELD_OF_THE_DEEP_MODIFIER.getId() + "_chestplates"), compatSalvage))
            .save(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.SHIELD_OF_THE_DEEP_MODIFIER.getId() + "_chestplates"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.MOSQUITO_MODIFIER)
            .setTools(TinkerTags.Items.BOOTS)
            .addInput(ModIntegration.MOSQUITO_PROBOSCIS)
            .addInput(ModIntegration.MOSQUITO_PROBOSCIS)
            .addInput(ModIntegration.MOSQUITO_PROBOSCIS)
            .addInput(ModIntegration.MOSQUITO_PROBOSCIS)
            .addInput(ModIntegration.MOSQUITO_PROBOSCIS)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(3)
            .saveSalvage(alexConsumer, prefix(TCIntegrationsModifiers.MOSQUITO_MODIFIER, compatSalvage))
            .save(alexConsumer, prefix(TCIntegrationsModifiers.MOSQUITO_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.CROCODILE_MODIFIER)
            .setTools(TinkerTags.Items.CHESTPLATES)
            .addInput(ModIntegration.CROCODILE_SCUTE)
            .addInput(ModIntegration.CROCODILE_SCUTE)
            .addInput(ModIntegration.CROCODILE_SCUTE)
            .addInput(ModIntegration.CROCODILE_SCUTE)
            .addInput(ModIntegration.CROCODILE_SCUTE)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.CROCODILE_MODIFIER.getId() + "_chestplates"), compatSalvage))
            .save(alexConsumer, prefix(new ResourceLocation(TCIntegrationsModifiers.CROCODILE_MODIFIER.getId() + "_chestplates"), compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.SOUL_STAINED_MODIFIER)
            .setTools(TinkerTags.Items.WORN_ARMOR)
            .addInput(ModIntegration.SOUL_STAINED_STEEL_INGOT)
            .addInput(ModIntegration.SOUL_STAINED_STEEL_INGOT)
            .addInput(ModIntegration.SOUL_STAINED_STEEL_INGOT)
            .addInput(ModIntegration.SOUL_STAINED_STEEL_INGOT)
            .addInput(ModIntegration.SOUL_STAINED_STEEL_INGOT)
            .setSlots(SlotType.DEFENSE, 1)
            .setMaxLevel(1)
            .saveSalvage(malumConsumer, prefix(TCIntegrationsModifiers.SOUL_STAINED_MODIFIER, compatSalvage))
            .save(malumConsumer, prefix(TCIntegrationsModifiers.SOUL_STAINED_MODIFIER, compatFolder));

        IncrementalModifierRecipeBuilder.modifier(TciModifierIds.masticate)
            .setTools(TinkerTags.Items.ARMOR)
            .setInput(ModIntegration.MASTICATOR_SCALES, 1, 2)
            .setMaxLevel(3)
            .setSlots(SlotType.UPGRADE, 1)
            .saveSalvage(undergardenConsumer, prefix(TCIntegrationsModifiers.MASTICATE_MODIFIER, compatSalvage))
            .save(undergardenConsumer, prefix(TCIntegrationsModifiers.MASTICATE_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.UTHERIUM_MODIFIER)
            .setTools(TinkerTags.Items.MELEE)
            .addInput(ModIntegration.UTHERIUM_CRYSTAL)
            .addInput(ModIntegration.UTHERIUM_CRYSTAL)
            .addInput(ModIntegration.UTHERIUM_CRYSTAL)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(undergardenConsumer, prefix(TCIntegrationsModifiers.UTHERIUM_MODIFIER, compatSalvage))
            .save(undergardenConsumer, prefix(TCIntegrationsModifiers.UTHERIUM_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.FROSTSTEEL_MODIFIER)
            .setTools(ingredientFromTags(TinkerTags.Items.MELEE, TinkerTags.Items.HARVEST))
            .addInput(ModIntegration.FROSTSTEEL_INGOT)
            .addInput(ModIntegration.FROSTSTEEL_INGOT)
            .addInput(ModIntegration.FROSTSTEEL_INGOT)
            .addInput(ModIntegration.FROSTSTEEL_INGOT)
            .addInput(ModIntegration.FROSTSTEEL_INGOT)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(undergardenConsumer, prefix(TCIntegrationsModifiers.FROSTSTEEL_MODIFIER, compatSalvage))
            .save(undergardenConsumer, prefix(TCIntegrationsModifiers.FROSTSTEEL_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.FORGOTTEN_MODIFIER)
            .setTools(ingredientFromTags(TinkerTags.Items.MELEE, TinkerTags.Items.HARVEST))
            .addInput(ModIntegration.FORGOTTEN_INGOT)
            .addInput(ModIntegration.CLOGGRUM_INGOT)
            .addInput(ModIntegration.CLOGGRUM_INGOT)
            .addInput(ModIntegration.CLOGGRUM_INGOT)
            .addInput(ModIntegration.CLOGGRUM_INGOT)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(undergardenConsumer, prefix(TCIntegrationsModifiers.FORGOTTEN_MODIFIER, compatSalvage))
            .save(undergardenConsumer, prefix(TCIntegrationsModifiers.FORGOTTEN_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.CHEESY_MODIFIER)
            .setTools(ingredientFromTags(TinkerTags.Items.MELEE, TinkerTags.Items.HARVEST))
            .addInput(TagManager.Items.CHEESE)
            .addInput(TagManager.Items.CHEESE)
            .addInput(TagManager.Items.CHEESE)
            .addInput(TagManager.Items.CHEESE)
            .addInput(TagManager.Items.CHEESE)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(3)
            .saveSalvage(cheeseConsumer, prefix(TCIntegrationsModifiers.CHEESY_MODIFIER, compatSalvage))
            .save(cheeseConsumer, prefix(TCIntegrationsModifiers.CHEESY_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TciModifierIds.multiVision)
            .setTools(TinkerTags.Items.HELMETS)
            .addInput(ModIntegration.VOLTMETER)
            .setSlots(SlotType.ABILITY, 1)
            .setMaxLevel(1)
            .saveSalvage(ieConsumer, prefix(TciModifierIds.multiVision, compatSalvage))
            .save(ieConsumer, prefix(TciModifierIds.multiVision, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.GLOWUP_MODIFIER)
            .setTools(ingredientFromTags(TinkerTags.Items.MELEE, TinkerTags.Items.HARVEST))
            .addInput(ModIntegration.INGOT_REFINED_GLOWSTONE)
            .addInput(ModIntegration.INGOT_REFINED_GLOWSTONE)
            .addInput(ModIntegration.INGOT_REFINED_GLOWSTONE)
            .addInput(ModIntegration.INGOT_REFINED_GLOWSTONE)
            .addInput(ModIntegration.INGOT_REFINED_GLOWSTONE)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(3)
            .saveSalvage(mekanismConsumer, prefix(TCIntegrationsModifiers.GLOWUP_MODIFIER, compatSalvage))
            .save(mekanismConsumer, prefix(TCIntegrationsModifiers.GLOWUP_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.FLAMED_MODIFIER)
            .setTools(ingredientFromTags(TinkerTags.Items.MELEE, TinkerTags.Items.RANGED))
            .addInput(ModIntegration.DRAGON_BONE)
            .addInput(ModIntegration.DRAGON_BONE)
            .addInput(TagManager.Items.WITHER_BONES)
            .addInput(ModIntegration.FIRE_DRAGON_BLOOD)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(ifdConsumer, prefix(TCIntegrationsModifiers.FLAMED_MODIFIER, compatSalvage))
            .save(ifdConsumer, prefix(TCIntegrationsModifiers.FLAMED_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ICED_MODIFIER)
            .setTools(ingredientFromTags(TinkerTags.Items.MELEE, TinkerTags.Items.RANGED))
            .addInput(ModIntegration.DRAGON_BONE)
            .addInput(ModIntegration.DRAGON_BONE)
            .addInput(TagManager.Items.WITHER_BONES)
            .addInput(ModIntegration.ICE_DRAGON_BLOOD)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(ifdConsumer, prefix(TCIntegrationsModifiers.ICED_MODIFIER, compatSalvage))
            .save(ifdConsumer, prefix(TCIntegrationsModifiers.ICED_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.ZAPPED_MODIFIER)
            .setTools(ingredientFromTags(TinkerTags.Items.MELEE, TinkerTags.Items.RANGED))
            .addInput(ModIntegration.DRAGON_BONE)
            .addInput(ModIntegration.DRAGON_BONE)
            .addInput(TagManager.Items.WITHER_BONES)
            .addInput(ModIntegration.LIGHTNING_DRAGON_BLOOD)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(ifdConsumer, prefix(TCIntegrationsModifiers.ZAPPED_MODIFIER, compatSalvage))
            .save(ifdConsumer, prefix(TCIntegrationsModifiers.ZAPPED_MODIFIER, compatFolder));

        ModifierRecipeBuilder.modifier(TCIntegrationsModifiers.PHANTASMAL_MODIFIER)
            .setTools(TinkerTags.Items.SWORD)
            .addInput(ModIntegration.DRAGON_BONE)
            .addInput(ModIntegration.DRAGON_BONE)
            .addInput(TagManager.Items.WITHER_BONES)
            .addInput(ModIntegration.GHOST_INGOT)
            .setSlots(SlotType.UPGRADE, 1)
            .setMaxLevel(1)
            .saveSalvage(ifdConsumer, prefix(TCIntegrationsModifiers.PHANTASMAL_MODIFIER, compatSalvage))
            .save(ifdConsumer, prefix(TCIntegrationsModifiers.PHANTASMAL_MODIFIER, compatFolder));
    }

    public ResourceLocation prefix(LazyModifier modifier, String prefix) {
        return prefix(modifier.getId(), prefix);
    }
    public ResourceLocation wrap(LazyModifier modifier, String prefix, String suffix) {
        return wrap(modifier.getId(), prefix, suffix);
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
