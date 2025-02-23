package tcintegrations.items;

import java.util.function.Function;

import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DispensibleContainerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;

import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import net.minecraftforge.registries.RegistryObject;
import slimeknights.mantle.item.BlockTooltipItem;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.MetalItemObject;

import slimeknights.tconstruct.world.TinkerWorld;
import tcintegrations.common.TCIntegrationsModule;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.TCIntegrations;

import static slimeknights.tconstruct.fluids.block.BurningLiquidBlock.createBurning;

public final class TCIntegrationsItems extends TCIntegrationsModule {

    public static RegistryObject<CreativeModeTab> ITEM_TAB_GROUP;
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
    public static FlowingFluidObject<ForgeFlowingFluid> MOLTEN_DESH;
    public static FlowingFluidObject<ForgeFlowingFluid> MOLTEN_OSTRUM;
    public static FlowingFluidObject<ForgeFlowingFluid> MOLTEN_CALORITE;
    public static FlowingFluidObject<ForgeFlowingFluid> MOLTEN_DRAGONSTEEL_FIRE;
    public static FlowingFluidObject<ForgeFlowingFluid> MOLTEN_DRAGONSTEEL_ICE;
    public static FlowingFluidObject<ForgeFlowingFluid> MOLTEN_DRAGONSTEEL_LIGHTNING;

    public static MetalItemObject BRONZE;

    public static void init() {
        ITEM_TAB_GROUP = CREATIVE_TABS.register(
            TCIntegrations.MODID + ".items",
                () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup." + TCIntegrations.MODID + ".items"))
                    .icon(() -> new ItemStack(BRONZE.getNugget()))
                    .displayItems(TCIntegrationsItems::addTabItems)
                    .withTabsBefore(TinkerWorld.tabWorld.getId())
                    .build()
        );
        GENERAL_TOOLTIP_BLOCK_ITEM = (b) -> new BlockTooltipItem(b, new Item.Properties());

        // Fluids
        if (ModIntegration.canLoad(ModIntegration.BOTANIA_MODID)) {
            MOLTEN_MANASTEEL = FLUID_REGISTRY.register("manasteel").type(hot("manasteel")
                .temperature(1250).lightLevel(13)).block(createBurning(MapColor.RAW_IRON, 13, 10, 5f)).bucket().flowing();
        }

        if (ModIntegration.canLoad(ModIntegration.AQUACULTURE_MODID)) {
            MOLTEN_NEPTUNIUM = FLUID_REGISTRY.register("neptunium").type(hot("neptunium")
                .temperature(1250).lightLevel(14)).block(createBurning(MapColor.EMERALD, 14, 10, 5f)).bucket().flowing();
        }

        if (ModIntegration.canLoad(ModIntegration.ARS_MODID)) {
            MOLTEN_SOURCE_GEM = FLUID_REGISTRY.register("source_gem").type(hot("source_gem")
                .temperature(1280).lightLevel(14)).block(createBurning(MapColor.COLOR_PURPLE, 14, 10, 5f)).bucket().flowing();
        }

        if (ModIntegration.canLoad(ModIntegration.MALUM_MODID)) {
            MOLTEN_SOUL_STAINED_STEEL = FLUID_REGISTRY.register("soul_stained_steel").type(hot("soul_stained_steel")
                .temperature(1250).lightLevel(12)).block(createBurning(MapColor.COLOR_MAGENTA, 12, 10, 5f)).bucket().flowing();
        }

        if (ModIntegration.canLoad(ModIntegration.UNDERGARDEN_MODID)) {
            MOLTEN_CLOGGRUM = FLUID_REGISTRY.register("cloggrum").type(hot("cloggrum")
                .temperature(1200).lightLevel(8)).block(createBurning(MapColor.TERRACOTTA_BROWN, 8, 10, 5f)).bucket().flowing();
            MOLTEN_FROSTSTEEL = FLUID_REGISTRY.register("froststeel").type(hot("froststeel")
                .temperature(1200).lightLevel(11)).block(createBurning(MapColor.WATER, 11, 10, 6f)).bucket().flowing();
            MOLTEN_FORGOTTEN = FLUID_REGISTRY.register("forgotten").type(hot("forgotten")
                .temperature(1200).lightLevel(14)).block(createBurning(MapColor.EMERALD, 14, 10, 6f)).bucket().flowing();
        }

        if (ModIntegration.canLoad(ModIntegration.BYG_MODID)) {
            MOLTEN_PENDORITE = FLUID_REGISTRY.register("pendorite").type(hot("pendorite")
                .temperature(1200).lightLevel(14)).block(createBurning(MapColor.TERRACOTTA_PURPLE, 14, 10, 5f)).bucket().flowing();
            MOLTEN_PENDORITE_ALLOY = FLUID_REGISTRY.register("pendorite_alloy").type(hot("pendorite_alloy")
                .temperature(1200).lightLevel(14)).block(createBurning(MapColor.COLOR_PURPLE, 14, 10, 5f)).bucket().flowing();
        }

        if (ModIntegration.canLoad(ModIntegration.IFD_MODID)) {
            MOLTEN_DRAGONSTEEL_FIRE = FLUID_REGISTRY.register("dragonsteel_fire").type(hot("dragonsteel_fire")
                .temperature(1750).lightLevel(12)).block(createBurning(MapColor.TERRACOTTA_RED, 12, 10, 5f)).bucket().flowing();
            MOLTEN_DRAGONSTEEL_ICE = FLUID_REGISTRY.register("dragonsteel_ice").type(hot("dragonsteel_ice")
                .temperature(1750).lightLevel(11)).block(createBurning(MapColor.ICE, 11, 10, 5f)).bucket().flowing();
            MOLTEN_DRAGONSTEEL_LIGHTNING = FLUID_REGISTRY.register("dragonsteel_lightning").type(hot("dragonsteel_lightning")
                .temperature(1750).lightLevel(14)).block(createBurning(MapColor.TERRACOTTA_YELLOW, 14, 10, 5f)).bucket().flowing();
        }

        // Space trash
        MOLTEN_DESH = FLUID_REGISTRY.register("desh").type(hot("desh")
            .temperature(800).lightLevel(4)).block(createBurning(MapColor.TERRACOTTA_GREEN, 4, 8, 3f)).bucket().flowing();
        MOLTEN_OSTRUM = FLUID_REGISTRY.register("ostrum").type(hot("ostrum")
            .temperature(800).lightLevel(4)).block(createBurning(MapColor.TERRACOTTA_PURPLE, 4, 8, 3f)).bucket().flowing();
        MOLTEN_CALORITE = FLUID_REGISTRY.register("calorite").type(hot("calorite")
            .temperature(800).lightLevel(4)).block(createBurning(MapColor.TERRACOTTA_RED, 4, 8, 3f)).bucket().flowing();
        
        // Metals
        BRONZE = METAL_BLOCK_REGISTRY.registerMetal(
            "bronze",
            metalBuilder(MapColor.WOOD),
            GENERAL_TOOLTIP_BLOCK_ITEM,
            new Item.Properties()
        );
    }

    public static FluidType.Properties hot(String name) {
        return FluidType.Properties.create().density(2000).viscosity(10000).temperature(1000)
            .descriptionId(makeDescriptionId("fluid", name))
            .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
            .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA);
    }

    private static BlockBehaviour.Properties builder(SoundType soundType) {
        return Block.Properties.of().sound(soundType);
    }

    private static BlockBehaviour.Properties builder(MapColor color, SoundType soundType) {
        return builder(soundType).mapColor(color);
    }

    private static BlockBehaviour.Properties metalBuilder(MapColor color) {
        return builder(color, SoundType.METAL).instrument(NoteBlockInstrument.IRON_XYLOPHONE).requiresCorrectToolForDrops().strength(5.0f);
    }

    public static String makeDescriptionId(String type, String name) {
        return type + "." + TCIntegrations.MODID + "." + name;
    }

    public static void setup(FMLCommonSetupEvent event) {
        DispenseItemBehavior dispenseItemBehavior = new DefaultDispenseItemBehavior() {
            private final DefaultDispenseItemBehavior defaultDispenseItemBehavior = new DefaultDispenseItemBehavior();

            @Override
            public ItemStack execute(BlockSource source, ItemStack stack) {
                DispensibleContainerItem container = (DispensibleContainerItem)stack.getItem();
                BlockPos blockPos = source.getPos().relative(source.getBlockState().getValue(DispenserBlock.FACING));
                Level level = source.getLevel();

                if (container.emptyContents(null, level, blockPos, null)) {
                    container.checkExtraContent(null, level, stack, blockPos);

                    return new ItemStack(Items.BUCKET);
                }
                else {
                    return this.defaultDispenseItemBehavior.dispense(source, stack);
                }
            }
        };

        event.enqueueWork(() -> {
            DispenserBlock.registerBehavior(MOLTEN_CLOGGRUM, dispenseItemBehavior);
            DispenserBlock.registerBehavior(MOLTEN_FORGOTTEN, dispenseItemBehavior);
            DispenserBlock.registerBehavior(MOLTEN_FROSTSTEEL, dispenseItemBehavior);
            DispenserBlock.registerBehavior(MOLTEN_MANASTEEL, dispenseItemBehavior);
            DispenserBlock.registerBehavior(MOLTEN_NEPTUNIUM, dispenseItemBehavior);
            DispenserBlock.registerBehavior(MOLTEN_PENDORITE, dispenseItemBehavior);
            DispenserBlock.registerBehavior(MOLTEN_PENDORITE_ALLOY, dispenseItemBehavior);
            DispenserBlock.registerBehavior(MOLTEN_SOUL_STAINED_STEEL, dispenseItemBehavior);
            DispenserBlock.registerBehavior(MOLTEN_SOURCE_GEM, dispenseItemBehavior);
            DispenserBlock.registerBehavior(MOLTEN_DESH, dispenseItemBehavior);
            DispenserBlock.registerBehavior(MOLTEN_OSTRUM, dispenseItemBehavior);
            DispenserBlock.registerBehavior(MOLTEN_CALORITE, dispenseItemBehavior);
        });
    }

    private static void addTabItems(CreativeModeTab.ItemDisplayParameters itemDisplayParameters, CreativeModeTab.Output output) {
        output.accept(BRONZE.getNugget());
        output.accept(BRONZE.getIngot());
        output.accept(BRONZE.get());
        output.accept(GENERAL_TOOLTIP_BLOCK_ITEM.apply(BRONZE.get()));
        output.accept(MOLTEN_MANASTEEL);
        output.accept(MOLTEN_NEPTUNIUM);
        output.accept(MOLTEN_SOURCE_GEM);
        output.accept(MOLTEN_SOUL_STAINED_STEEL);
        output.accept(MOLTEN_CLOGGRUM);
        output.accept(MOLTEN_FROSTSTEEL);
        output.accept(MOLTEN_FORGOTTEN);
        output.accept(MOLTEN_PENDORITE);
        output.accept(MOLTEN_PENDORITE_ALLOY);
        output.accept(MOLTEN_DESH);
        output.accept(MOLTEN_OSTRUM);
        output.accept(MOLTEN_CALORITE);
        output.accept(MOLTEN_DRAGONSTEEL_FIRE);
        output.accept(MOLTEN_DRAGONSTEEL_ICE);
        output.accept(MOLTEN_DRAGONSTEEL_LIGHTNING);
    }

}
