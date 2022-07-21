package tcintegrations.data.tcon;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;

import net.minecraftforge.common.data.ExistingFileHelper;

import slimeknights.mantle.registration.object.FluidObject;

import slimeknights.tconstruct.common.TinkerTags;

import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.TCIntegrations;

@SuppressWarnings("unchecked")
public class FluidTagProvider extends FluidTagsProvider {

    public FluidTagProvider(DataGenerator generatorIn, ExistingFileHelper helper) {
        super(generatorIn, TCIntegrations.MODID, helper);
    }

    @Override
    public String getName() {
        return "TCIntegrations - TCon Fluid Tags";
    }

    @Override
    public void addTags() {
        tagAll(TCIntegrationsItems.MANASTEEL);
        tagAll(TCIntegrationsItems.NEPTUNIUM);

        this.tag(TinkerTags.Fluids.METAL_TOOLTIPS)
            .addOptionalTag(TCIntegrationsItems.MANASTEEL.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.NEPTUNIUM.getForgeTag().location());

        this.tag(TinkerTags.Fluids.AVERAGE_METAL_SPILLING)
            .addOptionalTag(TCIntegrationsItems.MANASTEEL.getForgeTag().location());

        this.tag(TinkerTags.Fluids.EXPENSIVE_METAL_SPILLING)
            .addOptionalTag(TCIntegrationsItems.NEPTUNIUM.getForgeTag().location());
    }

    /** Tags this fluid using local tags */
    private void tagLocal(FluidObject<?> fluid) {
        tag(fluid.getLocalTag()).addOptional(fluid.getStill().getRegistryName()).addOptional(fluid.getFlowing().getRegistryName());
    }

    private void tagAll(FluidObject<?> fluid) {
        tagLocal(fluid);
        tag(fluid.getForgeTag()).addOptionalTag(fluid.getLocalTag().location());
    }

}
