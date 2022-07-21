package tcintegrations.data.integration;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.IForgeRegistry;

import tcintegrations.common.CreativeTabs;
import tcintegrations.common.TCIntegrationsModule;

public final class ModIntegration extends TCIntegrationsModule {

    public static final String BOTANIA_MODID = "botania";
    public static final String IE_MODID = "immersiveengineering";
    public static final String TCON_MODID = "tconstruct";
    public static final String CREATE_MODID = "create";
    public static final String AQUACULTURE_MODID = "aquaculture";

    public static Item BOTANIA_LIVINGWOOD_PLANKS;
    public static Item AQUACULTURE_NEPTUNIUM_INGOT;
    public static Item AQUACULTURE_TIN_CAN;
    public static Item AQUACULTURE_REDSTONE_HOOK;

    public static IForgeRegistry<Item> ITEM_REGISTRY;

    public static void init(IForgeRegistry<Item> registry) {
        String dataGen = System.getenv("DATA_GEN");

        ITEM_REGISTRY = registry;

        if (dataGen != null && dataGen.contains("all")) {
            BOTANIA_LIVINGWOOD_PLANKS = registerItem(botaniaLoc("livingwood_planks"));
            AQUACULTURE_NEPTUNIUM_INGOT = registerItem(aquacultureLoc("neptunium_ingot"));
            AQUACULTURE_TIN_CAN = registerItem(aquacultureLoc("tin_can"));
            AQUACULTURE_REDSTONE_HOOK = registerItem(aquacultureLoc("redstone_hook"));
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

    public static ResourceLocation tconLoc(String name) {
        return getLoc(TCON_MODID, name);
    }

    public static ResourceLocation ieLoc(String name) {
        return getLoc(IE_MODID, name);
    }

    public static ResourceLocation aquacultureLoc(String name) {
        return getLoc(AQUACULTURE_MODID, name);
    }

    private static ResourceLocation getLoc(String modid, String name) {
        return new ResourceLocation(modid, name);
    }

}
