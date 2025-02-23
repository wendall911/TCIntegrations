package tcintegrations.data.tcon.fluid;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;

import net.minecraftforge.common.data.ExistingFileHelper;

import slimeknights.mantle.registration.object.FlowingFluidObject;

import slimeknights.tconstruct.common.TinkerTags;

import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.TCIntegrations;

import static tcintegrations.util.ResourceLocationHelper.resource;

@SuppressWarnings("unchecked")
public class FluidTagProvider extends FluidTagsProvider {

    public FluidTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper helper) {
        super(packOutput, lookupProvider, TCIntegrations.MODID, helper);
    }

    @Override
    public String getName() {
        return "TCIntegrations - TCon Fluid Tags";
    }

    @Override
    public void addTags(HolderLookup.@NotNull Provider provider) {
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
            .addOptionalTag(TCIntegrationsItems.MOLTEN_MANASTEEL.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_NEPTUNIUM.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_CLOGGRUM.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_FROSTSTEEL.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_FORGOTTEN.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_PENDORITE.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DESH.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_CALORITE.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_OSTRUM.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_FIRE.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_ICE.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_LIGHTNING.getTag().location());

        this.tag(TinkerTags.Fluids.AVERAGE_METAL_SPILLING)
            .addOptionalTag(TCIntegrationsItems.MOLTEN_MANASTEEL.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_CLOGGRUM.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_FROSTSTEEL.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_FIRE.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_ICE.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_LIGHTNING.getTag().location());

        this.tag(TinkerTags.Fluids.EXPENSIVE_METAL_SPILLING)
            .addOptionalTag(TCIntegrationsItems.MOLTEN_NEPTUNIUM.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_FORGOTTEN.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_PENDORITE.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_FIRE.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_ICE.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_LIGHTNING.getTag().location());

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
        tag(fluid.getTag()).addOptionalTag(fluid.getLocalTag().location());
    }

}
