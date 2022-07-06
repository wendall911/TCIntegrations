package tcintegrations.data.tcon.material;

import net.minecraft.data.DataGenerator;

import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

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
        addMaterial(MaterialIds.livingWood, 1, ORDER_GENERAL, true);
        addMaterial(MaterialIds.livingRock, 1, ORDER_GENERAL, true);
        addCompatMetalMaterial(MaterialIds.manaSteel, 3, ORDER_COMPAT + ORDER_GENERAL);
    }

}
