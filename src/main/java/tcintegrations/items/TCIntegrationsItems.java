package tcintegrations.items;

import java.util.function.Function;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import net.minecraftforge.fml.ModList;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.mantle.registration.ModelFluidAttributes;
import slimeknights.mantle.registration.object.FluidObject;
import slimeknights.mantle.registration.object.MetalItemObject;

import tcintegrations.client.CreativeTabBase;
import tcintegrations.common.TCIntegrationsModule;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.TCIntegrations;

public final class TCIntegrationsItems extends TCIntegrationsModule {

    public static CreativeTabBase ITEM_TAB_GROUP;
    public static Function<Block, ? extends BlockItem> GENERAL_TOOLTIP_BLOCK_ITEM;

    public static FluidObject<ForgeFlowingFluid> MOLTEN_MANASTEEL;
    public static FluidObject<ForgeFlowingFluid> MOLTEN_NEPTUNIUM;
    public static FluidObject<ForgeFlowingFluid> MOLTEN_SOURCE_GEM;
    public static FluidObject<ForgeFlowingFluid> MOLTEN_SOUL_STAINED_STEEL;
    public static FluidObject<ForgeFlowingFluid> MOLTEN_CLOGGRUM;
    public static FluidObject<ForgeFlowingFluid> MOLTEN_FROSTSTEEL;
    public static FluidObject<ForgeFlowingFluid> MOLTEN_FORGOTTEN;
    public static FluidObject<ForgeFlowingFluid> MOLTEN_PENDORITE;
    public static FluidObject<ForgeFlowingFluid> MOLTEN_PENDORITE_ALLOY;

    public static MetalItemObject BRONZE;

    public static void init() {
        ITEM_TAB_GROUP = new CreativeTabBase(TCIntegrations.MODID + ".items", () -> new ItemStack(BRONZE.getNugget()));
        GENERAL_TOOLTIP_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, new Item.Properties().tab(ITEM_TAB_GROUP));

        // Fluids
        if (ModList.get().isLoaded(ModIntegration.BOTANIA_MODID)) {
            MOLTEN_MANASTEEL = FLUID_REGISTRY.register(
                "manasteel", hotBuilder().temperature(1250), Material.LAVA, 13);
        }

        if (ModList.get().isLoaded(ModIntegration.AQUACULTURE_MODID)) {
            MOLTEN_NEPTUNIUM = FLUID_REGISTRY.register(
                "neptunium", hotBuilder().temperature(1250), Material.LAVA, 14);
        }

        if (ModList.get().isLoaded(ModIntegration.ARS_MODID)) {
            MOLTEN_SOURCE_GEM = FLUID_REGISTRY.register(
                "source_gem", hotBuilder().temperature(1780), Material.LAVA, 14);
        }

        if (ModList.get().isLoaded(ModIntegration.MALUM_MODID)) {
            MOLTEN_SOUL_STAINED_STEEL = FLUID_REGISTRY.register(
                "soul_stained_steel", hotBuilder().temperature(1250), Material.LAVA, 12);
        }

        if (ModList.get().isLoaded(ModIntegration.UNDERGARDEN_MODID)) {
            MOLTEN_CLOGGRUM = FLUID_REGISTRY.register(
                "cloggrum", hotBuilder().temperature(1200), Material.LAVA, 8);
            MOLTEN_FROSTSTEEL = FLUID_REGISTRY.register(
                "froststeel", hotBuilder().temperature(1200), Material.LAVA, 11);
            MOLTEN_FORGOTTEN = FLUID_REGISTRY.register(
                "forgotten", hotBuilder().temperature(1200), Material.LAVA, 14);
        }

        if (ModList.get().isLoaded(ModIntegration.BYG_MODID)) {
            MOLTEN_PENDORITE = FLUID_REGISTRY.register(
                "pendorite", hotBuilder().temperature(1475), Material.LAVA, 14);
            MOLTEN_PENDORITE_ALLOY = FLUID_REGISTRY.register(
                "pendorite_alloy", hotBuilder().temperature(1550), Material.LAVA, 14);
        }
        
        // Metals
        BRONZE = METAL_BLOCK_REGISTRY.registerMetal(
            "bronze",
            metalBuilder(MaterialColor.WOOD),
            GENERAL_TOOLTIP_BLOCK_ITEM,
            new Item.Properties().tab(ITEM_TAB_GROUP)
        );
    }

    private static FluidAttributes.Builder hotBuilder() {
        return ModelFluidAttributes.builder().density(2000).viscosity(10000).temperature(1000).sound(
            SoundEvents.BUCKET_FILL_LAVA,
            SoundEvents.BUCKET_EMPTY_LAVA
        );
    }

    private static BlockBehaviour.Properties builder(Material material, MaterialColor color, SoundType soundType) {
        return Block.Properties.of(material, color).sound(soundType);
    }

    private static BlockBehaviour.Properties metalBuilder(MaterialColor color) {
        return builder(Material.METAL, color, SoundType.METAL).requiresCorrectToolForDrops().strength(5.0f);
    }

}
