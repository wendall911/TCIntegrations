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

import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.ModList;

import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.MetalItemObject;

import tcintegrations.client.CreativeTabBase;
import tcintegrations.common.TCIntegrationsModule;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.TCIntegrations;

public final class TCIntegrationsItems extends TCIntegrationsModule {

    public static CreativeTabBase ITEM_TAB_GROUP;
    public static Function<Block, ? extends BlockItem> GENERAL_TOOLTIP_BLOCK_ITEM;

    public static FlowingFluidObject<ForgeFlowingFluid> MOLTEN_MANASTEEL;
    public static FlowingFluidObject<ForgeFlowingFluid> MOLTEN_NEPTUNIUM;
    public static FlowingFluidObject<ForgeFlowingFluid> MOLTEN_SOURCE_GEM;
    public static FlowingFluidObject<ForgeFlowingFluid> MOLTEN_SOUL_STAINED_STEEL;
    public static FlowingFluidObject<ForgeFlowingFluid> MOLTEN_CLOGGRUM;
    public static FlowingFluidObject<ForgeFlowingFluid> MOLTEN_FROSTSTEEL;
    public static FlowingFluidObject<ForgeFlowingFluid> MOLTEN_FORGOTTEN;
    public static FlowingFluidObject<ForgeFlowingFluid> MOLTEN_PENDORITE;
    public static FlowingFluidObject<ForgeFlowingFluid> MOLTEN_PENDORITE_ALLOY;

    public static MetalItemObject BRONZE;

    public static void init() {
        ITEM_TAB_GROUP = new CreativeTabBase(TCIntegrations.MODID + ".items", () -> new ItemStack(BRONZE.getNugget()));
        GENERAL_TOOLTIP_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, new Item.Properties().tab(ITEM_TAB_GROUP));

        // Fluids
        if (ModList.get().isLoaded(ModIntegration.BOTANIA_MODID)) {
            MOLTEN_MANASTEEL = FLUID_REGISTRY.register("manasteel").type(hot("manasteel")
                    .temperature(1250).lightLevel(13)).block(Material.LAVA, 13).bucket().flowing();
        }

        if (ModList.get().isLoaded(ModIntegration.AQUACULTURE_MODID)) {
            MOLTEN_NEPTUNIUM = FLUID_REGISTRY.register("neptunium").type(hot("neptunium")
                    .temperature(1250).lightLevel(14)).block(Material.LAVA, 14).bucket().flowing();
        }

        if (ModList.get().isLoaded(ModIntegration.ARS_MODID)) {
            MOLTEN_SOURCE_GEM = FLUID_REGISTRY.register("source_gem").type(hot("source_gem")
                    .temperature(1780).lightLevel(14)).block(Material.LAVA, 14).bucket().flowing();
        }

        if (ModList.get().isLoaded(ModIntegration.MALUM_MODID)) {
            MOLTEN_SOUL_STAINED_STEEL = FLUID_REGISTRY.register("soul_stained_steel").type(hot("soul_stained_steel")
                    .temperature(1250).lightLevel(12)).block(Material.LAVA, 12).bucket().flowing();
        }

        if (ModList.get().isLoaded(ModIntegration.UNDERGARDEN_MODID)) {
            MOLTEN_CLOGGRUM = FLUID_REGISTRY.register("cloggrum").type(hot("cloggrum")
                    .temperature(1200).lightLevel(8)).block(Material.LAVA, 8).bucket().flowing();
            MOLTEN_FROSTSTEEL = FLUID_REGISTRY.register("froststeel").type(hot("froststeel")
                    .temperature(1200).lightLevel(11)).block(Material.LAVA, 11).bucket().flowing();
            MOLTEN_FORGOTTEN = FLUID_REGISTRY.register("forgotten").type(hot("forgotten")
                    .temperature(1200).lightLevel(14)).block(Material.LAVA, 14).bucket().flowing();
        }

        if (ModList.get().isLoaded(ModIntegration.BYG_MODID)) {
            MOLTEN_PENDORITE = FLUID_REGISTRY.register("pendorite").type(hot("pendorite")
                    .temperature(1200).lightLevel(14)).block(Material.LAVA, 14).bucket().flowing();
            MOLTEN_PENDORITE_ALLOY = FLUID_REGISTRY.register("pendorite_alloy").type(hot("pendorite_alloy")
                    .temperature(1200).lightLevel(14)).block(Material.LAVA, 14).bucket().flowing();
        }
        
        // Metals
        BRONZE = METAL_BLOCK_REGISTRY.registerMetal(
            "bronze",
            metalBuilder(MaterialColor.WOOD),
            GENERAL_TOOLTIP_BLOCK_ITEM,
            new Item.Properties().tab(ITEM_TAB_GROUP)
        );
    }

    public static FluidType.Properties hot(String name) {
        return FluidType.Properties.create().density(2000).viscosity(10000).temperature(1000)
                .descriptionId(makeDescriptionId("fluid", name))
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA);
    }

    private static BlockBehaviour.Properties builder(Material material, MaterialColor color, SoundType soundType) {
        return Block.Properties.of(material, color).sound(soundType);
    }

    private static BlockBehaviour.Properties metalBuilder(MaterialColor color) {
        return builder(Material.METAL, color, SoundType.METAL).requiresCorrectToolForDrops().strength(5.0f);
    }

    public static String makeDescriptionId(String type, String name) {
        return type + "." + TCIntegrations.MODID + "." + name;
    }

}
