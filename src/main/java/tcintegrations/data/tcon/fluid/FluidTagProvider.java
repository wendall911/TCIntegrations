package tcintegrations.data.tcon.fluid;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.FluidTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.material.Fluid;

import net.minecraftforge.common.data.ExistingFileHelper;

import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.FluidObject;

import slimeknights.tconstruct.common.TinkerTags;

import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.TCIntegrations;

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
        fluidTag(TCIntegrationsItems.MOLTEN_MANASTEEL);
        fluidTag(TCIntegrationsItems.MOLTEN_NEPTUNIUM);
        fluidTag(TCIntegrationsItems.MOLTEN_SOURCE_GEM);
        fluidTag(TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL);
        fluidTag(TCIntegrationsItems.MOLTEN_CLOGGRUM);
        fluidTag(TCIntegrationsItems.MOLTEN_FROSTSTEEL);
        fluidTag(TCIntegrationsItems.MOLTEN_FORGOTTEN_METAL);
        fluidTag(TCIntegrationsItems.MOLTEN_DESH);
        fluidTag(TCIntegrationsItems.MOLTEN_CALORITE);
        fluidTag(TCIntegrationsItems.MOLTEN_OSTRUM);
        fluidTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_FIRE);
        fluidTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_ICE);
        fluidTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_LIGHTNING);

        this.tag(TinkerTags.Fluids.METAL_TOOLTIPS)
            .addOptionalTag(TCIntegrationsItems.MOLTEN_MANASTEEL.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_NEPTUNIUM.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_CLOGGRUM.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_FROSTSTEEL.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_FORGOTTEN_METAL.getTag().location())
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
            .addOptionalTag(TCIntegrationsItems.MOLTEN_FORGOTTEN_METAL.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_FIRE.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_ICE.getTag().location())
            .addOptionalTag(TCIntegrationsItems.MOLTEN_DRAGONSTEEL_LIGHTNING.getTag().location());

        this.tag(TinkerTags.Fluids.SMALL_GEM_TOOLTIPS)
            .addOptionalTag(TCIntegrationsItems.MOLTEN_SOURCE_GEM.getId());
    }

    private void fluidTag(FluidObject<?> fluid) {
        tag(Objects.requireNonNull(fluid.getCommonTag())).add(fluid.get());
    }

    /** Adds tags for a placable fluid */
    private void fluidTag(FlowingFluidObject<?> fluid) {
        tag(fluid.getLocalTag()).add(fluid.getStill(), fluid.getFlowing());
        TagKey<Fluid> tag = fluid.getCommonTag();

        if (tag != null) {
            tag(tag).addTag(fluid.getLocalTag());
        }
    }

}
