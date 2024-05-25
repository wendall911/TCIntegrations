package tcintegrations.data.integration;

import moze_intel.projecte.api.data.CustomConversionProvider;

import net.minecraft.data.DataGenerator;

import slimeknights.tconstruct.shared.TinkerMaterials;

import tcintegrations.TCIntegrations;

import static tcintegrations.util.ResourceLocationHelper.location;

public class ProjectEConversionProvider extends CustomConversionProvider {

    public ProjectEConversionProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "TCIntegrations - ProjectE Conversion Provider";
    }

    @Override
    protected void addCustomConversions() {
        createConversionBuilder(location(TCIntegrations.MODID, "metals"))
            .before(TinkerMaterials.cobalt.getIngot(), 6_144);
    }
}
