package tcintegrations.data.tcon.fluid;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.FluidTagsProvider;

import net.minecraftforge.common.data.ExistingFileHelper;

import slimeknights.mantle.registration.object.FlowingFluidObject;

import slimeknights.tconstruct.common.TinkerTags;

import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.TCIntegrations;

import static tcintegrations.util.ResourceLocationHelper.resource;

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
        tagAll(TCIntegrationsItems.MOLTEN_MANASTEEL);
        tagAll(TCIntegrationsItems.MOLTEN_NEPTUNIUM);
        tagLocal(TCIntegrationsItems.MOLTEN_SOURCE_GEM);
        tagAll(TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL);
        tagAll(TCIntegrationsItems.MOLTEN_CLOGGRUM);
        tagAll(TCIntegrationsItems.MOLTEN_FROSTSTEEL);
        tagAll(TCIntegrationsItems.MOLTEN_FORGOTTEN);
        tagAll(TCIntegrationsItems.MOLTEN_PENDORITE);
        tagAll(TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY);
        tagAll(TCIntegrationsItems.MOLTEN_DESH);
        tagAll(TCIntegrationsItems.MOLTEN_CALORITE);
        tagAll(TCIntegrationsItems.MOLTEN_OSTRUM);
        tagAll(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_FIRE);
        tagAll(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_ICE);
        tagAll(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_LIGHTNING);

        this.tag(TinkerTags.Fluids.METAL_TOOLTIPS)
            .addOptionalTag(TCIntegrationsItems.MOLTEN_MANASTEEL.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_NEPTUNIUM.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_CLOGGRUM.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_FROSTSTEEL.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_FORGOTTEN.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_PENDORITE.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DESH.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_CALORITE.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_OSTRUM.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_FIRE.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_ICE.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_LIGHTNING.getForgeTag().location());

        this.tag(TinkerTags.Fluids.AVERAGE_METAL_SPILLING)
            .addOptionalTag(TCIntegrationsItems.MOLTEN_MANASTEEL.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_CLOGGRUM.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_FROSTSTEEL.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_FIRE.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_ICE.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_LIGHTNING.getForgeTag().location());

        this.tag(TinkerTags.Fluids.EXPENSIVE_METAL_SPILLING)
            .addOptionalTag(TCIntegrationsItems.MOLTEN_NEPTUNIUM.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_FORGOTTEN.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_PENDORITE.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_FIRE.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_ICE.getForgeTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_LIGHTNING.getForgeTag().location());

        this.tag(TinkerTags.Fluids.SMALL_GEM_TOOLTIPS)
            .addOptionalTag(TCIntegrationsItems.MOLTEN_SOURCE_GEM.getId());
    }

    /** Tags this fluid using local tags */
    private void tagLocal(FlowingFluidObject<?> fluid) {
        String name = fluid.getLocalTag().location().getPath();

        tag(fluid.getLocalTag()).addOptional(resource(name)).addOptional(resource("flowing_" + name));
    }

    private void tagAll(FlowingFluidObject<?> fluid) {
        tagLocal(fluid);
        tag(fluid.getForgeTag()).addOptionalTag(fluid.getLocalTag().location());
    }

}
