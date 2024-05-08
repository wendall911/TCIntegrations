package tcintegrations.data.tcon.fluid;

import net.minecraft.data.DataGenerator;

import net.minecraft.resources.ResourceLocation;

import slimeknights.mantle.fluid.texture.AbstractFluidTextureProvider;
import slimeknights.mantle.fluid.texture.FluidTexture;
import slimeknights.mantle.registration.object.FluidObject;

import tcintegrations.TCIntegrations;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.TCIntegrationsItems;

@SuppressWarnings({"UnusedReturnValue", "SameParameterValue"})
public class FluidTextureProvider extends AbstractFluidTextureProvider {

    public FluidTextureProvider(DataGenerator generator) {
        super(generator, TCIntegrations.MODID);
    }

    @Override
    public String getName() {
        return "TCIntegrations - TCon Fluid Textures";
    }

    @Override
    public void addTextures() {
        tintedStone(TCIntegrationsItems.MOLTEN_CLOGGRUM).color(0xFFB79A7A);
        compatAlloy(TCIntegrationsItems.MOLTEN_FORGOTTEN); // metal_still - metal_flow
        compatAlloy(TCIntegrationsItems.MOLTEN_FROSTSTEEL); // metal_still - metal_flow
        compatAlloy(TCIntegrationsItems.MOLTEN_MANASTEEL); // metal_shiny_still - metal_shiny_flow
        compatAlloy(TCIntegrationsItems.MOLTEN_NEPTUNIUM); // metal_still - metal_flow
        compatAlloy(TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY); // metal_still - metal_flow
        tintedStone(TCIntegrationsItems.MOLTEN_PENDORITE).color(0xFF604FA0);
        compatAlloy(TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL); // metal_still - metal_flow
        compatOre(TCIntegrationsItems.MOLTEN_SOURCE_GEM); // crystal_still - crystal_flow
    }

    public static ResourceLocation getResource(String name) {
        return new ResourceLocation(TCIntegrations.MODID, name);
    }

    private FluidTexture.Builder named(FluidObject<?> fluid, String name) {
        return texture(fluid).textures(getResource("fluid/" + name + "/"), false, false);
    }

    public static ResourceLocation getTconResource(String name) {
        return new ResourceLocation(ModIntegration.TCON_MODID, name);
    }

    private FluidTexture.Builder namedTcon(FluidObject<?> fluid, String name) {
        return texture(fluid).textures(getTconResource("fluid/" + name + "/"), false, false);
    }

    private FluidTexture.Builder moltenFolder(FluidObject<?> fluid, String folder) {
        return named(fluid, "molten/" + folder + "/" + fluid.getId().getPath());
    }

    private FluidTexture.Builder compatAlloy(FluidObject<?> fluid) {
        return moltenFolder(fluid, "compat_alloy");
    }

    private FluidTexture.Builder compatOre(FluidObject<?> fluid) {
        return moltenFolder(fluid, "compat_ore");
    }

    private FluidTexture.Builder tintedStone(FluidObject<?> fluid) {
        return namedTcon(fluid, "molten/stone");
    }

}
