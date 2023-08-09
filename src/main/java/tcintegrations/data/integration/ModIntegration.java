package tcintegrations.data.integration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Material;

import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;

import slimeknights.mantle.registration.ModelFluidAttributes;
import slimeknights.mantle.registration.object.FluidObject;

import tcintegrations.common.CreativeTabs;
import tcintegrations.common.TCIntegrationsModule;

public final class ModIntegration extends TCIntegrationsModule {

    public static final String BOTANIA_MODID = "botania";
    public static final String IE_MODID = "immersiveengineering";
    public static final String TCON_MODID = "tconstruct";
    public static final String CREATE_MODID = "create";
    public static final String AQUACULTURE_MODID = "aquaculture";
    public static final String ARS_MODID = "ars_nouveau";
    public static final String ALEX_MODID = "alexsmobs";
    public static final String MALUM_MODID = "malum";
    public static final String UNDERGARDEN_MODID = "undergarden";
    public static final String BEYOND_EARTH_MODID = "beyond_earth";
    public static final String BYG_MODID = "byg";
    public static final String MEKANISM_MODID = "mekanism";
    public static final String MYTHIC_BOTANY_MODID = "mythicbotany";
    public static final String IFD_MODID = "iceandfire";

    public static Item BOTANIA_LIVINGWOOD_PLANKS;
    public static Item BEYOND_EARTH_CHEESE;

    public static FluidObject<ForgeFlowingFluid> MOLTEN_DESH;
    public static FluidObject<ForgeFlowingFluid> MOLTEN_OSTRUM;
    public static FluidObject<ForgeFlowingFluid> MOLTEN_CALORITE;

    public static RegistryObject<MobEffect> OXYGEN_EFFECT;

    public static IForgeRegistry<Item> ITEM_REGISTRY;

    public static void init(IForgeRegistry<Item> registry) {
        String dataGen = System.getenv("DATA_GEN");

        ITEM_REGISTRY = registry;

        if (dataGen != null && dataGen.contains("all")) {
            BOTANIA_LIVINGWOOD_PLANKS = registerItem(botaniaLoc("livingwood_planks"));
            BEYOND_EARTH_CHEESE = registerItem(beyondEarthLoc("cheese"));
        }

    }

    public static void setup() {
        String dataGen = System.getenv("DATA_GEN");

        if (dataGen != null && dataGen.contains("all")) {
            MOLTEN_DESH = BEYOND_EARTH_FLUID_REGISTRY.register("molten_desh", hotBuilder().temperature(800), Material.LAVA, 12);
            MOLTEN_OSTRUM = BEYOND_EARTH_FLUID_REGISTRY.register("molten_ostrum", hotBuilder().temperature(800), Material.LAVA, 12);
            MOLTEN_CALORITE = BEYOND_EARTH_FLUID_REGISTRY.register("molten_calorite", hotBuilder().temperature(800), Material.LAVA, 12);
        }

        if (ModList.get().isLoaded(ModIntegration.BEYOND_EARTH_MODID)) {
            OXYGEN_EFFECT = BEYOND_EARTH_EFFECTS_REGISTRY.register("oxygen_bubble_effect", () -> new OxygenEffect(MobEffectCategory.BENEFICIAL, 3035801));
        }
    }

    private static class OxygenEffect extends MobEffect {

        public OxygenEffect(MobEffectCategory typeIn, int color) {
            super(typeIn, color);
        }

        @Override
        public void applyEffectTick(LivingEntity entity, int amplifier) {
            entity.setAirSupply(300);
        }

    }

    private static Item registerItem(ResourceLocation loc) {
        Item item = (new Item(new Item.Properties().tab(CreativeTabs.INTEGRATION_TAB_GROUP))).setRegistryName(loc);

        ITEM_REGISTRY.register(item);

        return item;
    }

    public static ResourceLocation botaniaLoc(String name) {
        return getLoc(BOTANIA_MODID, name);
    }

    public static ResourceLocation malumLoc(String name) {
        return getLoc(MALUM_MODID, name);
    }

    public static ResourceLocation beyondEarthLoc(String name) {
        return getLoc(BEYOND_EARTH_MODID, name);
    }

    public static ResourceLocation bygLoc(String name) {
        return getLoc(BYG_MODID, name);
    }

    public static ResourceLocation mbLoc(String name) {
        return getLoc(MYTHIC_BOTANY_MODID, name);
    }

    private static ResourceLocation getLoc(String modid, String name) {
        return new ResourceLocation(modid, name);
    }

    private static FluidAttributes.Builder hotBuilder() {
        return ModelFluidAttributes.builder().density(2000).viscosity(10000).temperature(1000).sound(SoundEvents.BUCKET_FILL_LAVA, SoundEvents.BUCKET_EMPTY_LAVA);
    }

}
