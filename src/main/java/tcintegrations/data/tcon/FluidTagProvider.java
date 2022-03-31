package tcintegrations.data.tcon;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;

import net.minecraftforge.common.data.ExistingFileHelper;

import slimeknights.mantle.registration.object.FluidObject;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.common.TinkerTags.Fluids;
import slimeknights.tconstruct.fluids.TinkerFluids;

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

        this.tag(TinkerTags.Fluids.METAL_TOOLTIPS).addTags(
            TCIntegrationsItems.MANASTEEL.getForgeTag()
        );

        this.tag(TinkerTags.Fluids.AVERAGE_METAL_SPILLING)
            .addTag(TCIntegrationsItems.MANASTEEL.getForgeTag());
    }

    /** Tags this fluid using local tags */
    private void tagLocal(FluidObject<?> fluid) {
        tag(fluid.getLocalTag()).add(fluid.getStill(), fluid.getFlowing());
    }

    private void tagAll(FluidObject<?> fluid) {
        tagLocal(fluid);
        tag(fluid.getForgeTag()).addTag(fluid.getLocalTag());
    }

}
