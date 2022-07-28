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
    public static final String ARS_MODID = "ars_nouveau";
    public static final String ALEX_MODID = "alexsmobs";
    public static final String MALUM_MODID = "malum";
    public static final String UNDERGARDEN_MODID = "undergarden";

    public static Item BOTANIA_LIVINGWOOD_PLANKS;

    public static IForgeRegistry<Item> ITEM_REGISTRY;

    public static void init(IForgeRegistry<Item> registry) {
        String dataGen = System.getenv("DATA_GEN");

        ITEM_REGISTRY = registry;

        if (dataGen != null && dataGen.contains("all")) {
            BOTANIA_LIVINGWOOD_PLANKS = registerItem(botaniaLoc("livingwood_planks"));
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

    private static ResourceLocation getLoc(String modid, String name) {
        return new ResourceLocation(modid, name);
    }

}
