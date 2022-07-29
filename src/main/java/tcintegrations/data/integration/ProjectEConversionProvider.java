package tcintegrations.data.integration;

import moze_intel.projecte.api.data.CustomConversionProvider;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

import slimeknights.tconstruct.shared.TinkerMaterials;
import tcintegrations.TCIntegrations;

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
        createConversionBuilder(new ResourceLocation(TCIntegrations.MODID, "metals"))
            .before(TinkerMaterials.cobalt.getIngot(), 6_144);
    }
}
