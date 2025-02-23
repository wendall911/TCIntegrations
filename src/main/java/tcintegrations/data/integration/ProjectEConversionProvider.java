package tcintegrations.data.integration;

import java.util.concurrent.CompletableFuture;

import moze_intel.projecte.api.data.CustomConversionProvider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;

import slimeknights.tconstruct.shared.TinkerMaterials;

import tcintegrations.TCIntegrations;

import static tcintegrations.util.ResourceLocationHelper.location;

public class ProjectEConversionProvider extends CustomConversionProvider {

    public ProjectEConversionProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(packOutput, lookupProvider);
    }

    @Override
    public String getName() {
        return "TCIntegrations - ProjectE Conversion Provider";
    }

    @Override
    protected void addCustomConversions(HolderLookup.Provider provider) {
        createConversionBuilder(location(TCIntegrations.MODID, "metals"))
            .before(TinkerMaterials.cobalt.getIngot(), 6_144);
    }

}
