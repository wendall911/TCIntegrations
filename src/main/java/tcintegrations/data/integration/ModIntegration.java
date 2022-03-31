package tcintegrations.data.integration;

import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import tcintegrations.common.TCIntegrationsModule;

public final class ModIntegration extends TCIntegrationsModule {

    public static final String BOTANIA_MODID = "botania";
    public static final String IE_MODID = "immersiveengineering";
    public static final String TCON_MODID = "tconstruct";

    public static void init() {
        String dataGen = System.getenv("DATA_GEN");

        if (dataGen != null && dataGen.contains("all")) {
        }

    }

    private static RegistryObject<Item> registerItem(String name, DeferredRegister<Item> registry) {
        return registerItem(name, () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_BUILDING_BLOCKS)), registry);
    }

    private static <T extends Item> RegistryObject<T> registerItem(String name, Supplier<T> item, DeferredRegister<Item> registry) {
        return registry.register(name, item);
    }

    public static ResourceLocation tconLoc(String name) {
        return getLoc(TCON_MODID, name);
    }

    public static ResourceLocation ieLoc(String name) {
        return getLoc(IE_MODID, name);
    }

    private static ResourceLocation getLoc(String modid, String name) {
        return new ResourceLocation(modid, name);
    }

}
