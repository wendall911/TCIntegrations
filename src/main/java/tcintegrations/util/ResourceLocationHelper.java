package tcintegrations.util;

import net.minecraft.resources.ResourceLocation;

import tcintegrations.TCIntegrations;

public class ResourceLocationHelper {

    public static ResourceLocation resource(String path) {
        return location(TCIntegrations.MODID, path);
    }

    public static ResourceLocation location(String modid, String name) {
        return new ResourceLocation(modid, name);
    }

}
