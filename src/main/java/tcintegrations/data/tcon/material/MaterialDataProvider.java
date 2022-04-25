package tcintegrations.data.tcon.material;

import net.minecraft.data.DataGenerator;

import net.minecraft.data.HashCache;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

import tcintegrations.data.tcon.material.MaterialIds;
import tcintegrations.items.TCIntegrationsItems;

import java.io.IOException;

public class MaterialDataProvider extends AbstractMaterialDataProvider {

    public MaterialDataProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    public String getName() {
        return "TCIntegrations - TCon Materials";
    }

    @Override
    protected void addMaterials() {
        addCompatMetalMaterial(MaterialIds.manaSteel, 3, ORDER_COMPAT + ORDER_GENERAL);
    }

}
