package tcintegrations.data.tcon;

import java.util.function.Consumer;

import org.jetbrains.annotations.NotNull;

import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.material.Fluid;

import net.minecraftforge.common.crafting.conditions.OrCondition;
import net.minecraftforge.common.crafting.conditions.TagEmptyCondition;

import slimeknights.mantle.recipe.data.ICommonRecipeHelper;
import slimeknights.mantle.recipe.helper.ItemOutput;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.tconstruct.fluids.TinkerFluids;
import slimeknights.tconstruct.library.data.recipe.ISmelteryRecipeHelper;
import slimeknights.tconstruct.library.data.recipe.SmelteryRecipeBuilder;
import slimeknights.tconstruct.library.recipe.alloying.AlloyRecipeBuilder;
import slimeknights.tconstruct.library.recipe.FluidValues;
import slimeknights.tconstruct.library.recipe.casting.ItemCastingRecipeBuilder;
import slimeknights.tconstruct.library.recipe.melting.IMeltingContainer;
import slimeknights.tconstruct.library.recipe.melting.IMeltingRecipe;
import slimeknights.tconstruct.library.recipe.melting.MeltingRecipeBuilder;
import slimeknights.tconstruct.smeltery.TinkerSmeltery;

import tcintegrations.data.BaseRecipeProvider;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.util.ResourceLocationHelper;

import static slimeknights.tconstruct.library.data.recipe.SmelteryRecipeBuilder.itemTag;

public class SmelteryRecipeProvider extends BaseRecipeProvider implements ISmelteryRecipeHelper, ICommonRecipeHelper {

    public SmelteryRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "TCIntegrations - Smeltery Recipes";
    }

    @Override
    protected void buildRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        this.addMeltingRecipes(consumer);
        this.addAlloyRecipes(consumer);
    }

    private void addMeltingRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/melting/";

        // ores
        String metalFolder = folder + "metal/";
        Consumer<FinishedRecipe> botaniaConsumer = withCondition(consumer, modLoaded(ModIntegration.BOTANIA_MODID));
        Consumer<FinishedRecipe> aquacultureConsumer = withCondition(consumer, modLoaded(ModIntegration.AQUACULTURE_MODID));
        Consumer<FinishedRecipe> malumConsumer = withCondition(consumer, modLoaded(ModIntegration.MALUM_MODID));
        Consumer<FinishedRecipe> undergardenConsumer = withCondition(consumer, modLoaded(ModIntegration.UNDERGARDEN_MODID));
        Consumer<FinishedRecipe> adAstraConsumer = withCondition(consumer, new OrCondition(modLoaded(ModIntegration.AD_ASTRA_MODID), modLoaded(ModIntegration.BEYOND_EARTH_MODID)));
        Consumer<FinishedRecipe> ifdConsumer = withCondition(consumer, modLoaded(ModIntegration.IFD_MODID));
        Consumer<FinishedRecipe> arsConsumer = withCondition(consumer, modLoaded(ModIntegration.ARS_MODID));

        molten(arsConsumer, TCIntegrationsItems.MOLTEN_SOURCE_GEM).smallGem();
        metal(botaniaConsumer, TCIntegrationsItems.MOLTEN_MANASTEEL).metal();
        metal(aquacultureConsumer, TCIntegrationsItems.MOLTEN_NEPTUNIUM).metal();
        metal(malumConsumer, TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL).metal();
        metal(undergardenConsumer, TCIntegrationsItems.MOLTEN_CLOGGRUM).ore().metal();
        metal(undergardenConsumer, TCIntegrationsItems.MOLTEN_FROSTSTEEL).ore().metal();
        metal(undergardenConsumer, TCIntegrationsItems.MOLTEN_FORGOTTEN_METAL).metal();
        metal(adAstraConsumer, TCIntegrationsItems.MOLTEN_DESH).ore().metal();
        metal(adAstraConsumer, TCIntegrationsItems.MOLTEN_CALORITE).ore().metal();
        metal(adAstraConsumer, TCIntegrationsItems.MOLTEN_OSTRUM).ore().metal();
        metalWithoutNugget(ifdConsumer, TCIntegrationsItems.MOLTEN_DRAGONSTEEL_FIRE);
        metalWithoutNugget(ifdConsumer, TCIntegrationsItems.MOLTEN_DRAGONSTEEL_ICE);
        metalWithoutNugget(ifdConsumer, TCIntegrationsItems.MOLTEN_DRAGONSTEEL_LIGHTNING);

        // IFD Silver Items
        // armor
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_SILVER_METAL_HELMET), TinkerFluids.moltenSilver.get(), FluidValues.INGOT * 5)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "silver/helmet"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_SILVER_METAL_CHESTPLATE), TinkerFluids.moltenSilver.get(), FluidValues.INGOT * 8)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "silver/chestplate"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_SILVER_METAL_LEGGINGS), TinkerFluids.moltenSilver.get(), FluidValues.INGOT * 7)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "silver/leggings"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_SILVER_METAL_BOOTS), TinkerFluids.moltenSilver.get(), FluidValues.INGOT * 4)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "silver/boots"));
        // tools
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_SILVER_AXE, ModIntegration.IFD_SILVER_PICKAXE), TinkerFluids.moltenSilver.get(), FluidValues.INGOT * 3)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "silver/axes"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_SILVER_SWORD, ModIntegration.IFD_SILVER_HOE), TinkerFluids.moltenSilver.get(), FluidValues.INGOT * 2)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "silver/weapon"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_SILVER_SHOVEL), TinkerFluids.moltenSilver.get(), FluidValues.INGOT)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "silver/small"));
        // Dragon Armor
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_DRAGONARMOR_SILVER_HEAD, ModIntegration.IFD_DRAGONARMOR_SILVER_NECK), TinkerFluids.moltenSilver.get(), FluidValues.METAL_BLOCK * 5)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "silver/dragon_armor_head_neck"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_DRAGONARMOR_SILVER_BODY), TinkerFluids.moltenSilver.get(), FluidValues.METAL_BLOCK * 8)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "silver/dragon_armor_head_body"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_DRAGONARMOR_SILVER_TAIL), TinkerFluids.moltenSilver.get(), FluidValues.METAL_BLOCK * 3)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "silver/dragon_armor_head_tail"));

        // IFD Copper Items
        // armor
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_COPPER_METAL_HELMET), TinkerFluids.moltenCopper.get(), FluidValues.INGOT * 5)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "copper/helmet"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_COPPER_METAL_CHESTPLATE), TinkerFluids.moltenCopper.get(), FluidValues.INGOT * 8)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "copper/chestplate"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_COPPER_METAL_LEGGINGS), TinkerFluids.moltenCopper.get(), FluidValues.INGOT * 7)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "copper/leggings"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_COPPER_METAL_BOOTS), TinkerFluids.moltenCopper.get(), FluidValues.INGOT * 4)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "copper/boots"));
        // tools
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_COPPER_AXE, ModIntegration.IFD_COPPER_PICKAXE), TinkerFluids.moltenCopper.get(), FluidValues.INGOT * 3)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "copper/axes"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_COPPER_SWORD, ModIntegration.IFD_COPPER_HOE), TinkerFluids.moltenCopper.get(), FluidValues.INGOT * 2)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "copper/weapon"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_COPPER_SHOVEL), TinkerFluids.moltenCopper.get(), FluidValues.INGOT)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "copper/small"));
        // Dragon Armor
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_DRAGONARMOR_COPPER_HEAD, ModIntegration.IFD_DRAGONARMOR_COPPER_NECK), TinkerFluids.moltenCopper.get(), FluidValues.METAL_BLOCK * 5)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "copper/dragon_armor_head_neck"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_DRAGONARMOR_COPPER_BODY), TinkerFluids.moltenCopper.get(), FluidValues.METAL_BLOCK * 8)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "copper/dragon_armor_head_body"));
        MeltingRecipeBuilder.melting(Ingredient.of(ModIntegration.IFD_DRAGONARMOR_COPPER_TAIL), TinkerFluids.moltenCopper.get(), FluidValues.METAL_BLOCK * 3)
            .setDamagable(FluidValues.NUGGET)
            .save(ifdConsumer, location(metalFolder + "copper/dragon_armor_head_tail"));
    }

    private void addAlloyRecipes(Consumer<FinishedRecipe> consumer) {
        String folder = "smeltery/alloys/";

        // Update Recipe to use Obsidian instead of Quartz to not interfere with Hepatizon
        Consumer<FinishedRecipe> wrapped = withCondition(consumer, new TagEmptyCondition(ResourceLocationHelper.location("forge", "ingots/tin")));

        AlloyRecipeBuilder.alloy(TinkerFluids.moltenBronze.get(), FluidValues.INGOT * 4)
            .addInput(TinkerFluids.moltenCopper.ingredient(FluidValues.INGOT * 3))
            .addInput(TinkerFluids.moltenObsidian.ingredient(FluidValues.GLASS_BLOCK))
            .save(wrapped, prefix(TinkerFluids.moltenBronze, folder));
    }

    /** Creates a metal from a tag */
    public SmelteryRecipeBuilder metal(Consumer<FinishedRecipe> consumer, String name, TagKey<Fluid> fluid) {
        return SmelteryRecipeBuilder.fluid(consumer, location(name), fluid).castingFolder("smeltery/casting/metal").meltingFolder("smeltery/melting/metal");
    }

    /** Creates a smeltery builder for a metal fluid */
    public SmelteryRecipeBuilder metal(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid) {
        return molten(consumer, fluid).castingFolder("smeltery/casting/metal").meltingFolder("smeltery/melting/metal");
    }

    private void metalWithoutNugget(Consumer<FinishedRecipe> consumer, FluidObject<?> fluid) {
        SmelteryRecipeBuilder builder = molten(consumer, fluid).castingFolder("smeltery/casting/metal").meltingFolder("smeltery/melting/metal");
        ResourceLocation name = this.location(fluid.getId().getPath().substring("molten_".length()));

        builder.oreRate(IMeltingContainer.OreRateType.METAL);
        builder.baseUnit(90);
        builder.damageUnit(10);
        builder.melting(9.0F, "block", "storage_blocks", 3.0F, false, false);
        basinMetalCasting(builder, consumer, fluid,  name);
        builder.meltingCasting(1.0F, TinkerSmeltery.ingotCast, 1.0F, false);
    }

    private void basinMetalCasting(SmelteryRecipeBuilder builder, Consumer<FinishedRecipe> consumer, FluidObject<?> fluid, ResourceLocation name) {
        String castingFolder = "smeltery/casting/metal/";
        String tagName = "storage_blocks/" + name.getPath();

        ItemCastingRecipeBuilder.basinRecipe(ItemOutput.fromTag(itemTag(tagName))).setFluid(fluid.ingredient(810)).setCoolingTime(IMeltingRecipe.getTemperature(fluid), 810).save(consumer, location(name, castingFolder, "block"));
    }

    private ResourceLocation location(ResourceLocation name, String folder, String variant) {
        return name.withPath(folder + name.getPath() + "/" + variant);
    }

}
