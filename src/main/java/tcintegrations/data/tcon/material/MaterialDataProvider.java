package tcintegrations.data.tcon.material;

import net.minecraft.data.DataGenerator;

import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;

import tcintegrations.data.integration.ModIntegration;

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
        ICondition botaniaLoadedCondition = new ModLoadedCondition(ModIntegration.BOTANIA_MODID);

        addMaterial(MaterialIds.livingWood, 1, ORDER_GENERAL, true, false, botaniaLoadedCondition);
        addMaterial(MaterialIds.livingRock, 1, ORDER_GENERAL, true, false, botaniaLoadedCondition);
        addCompatMetalMaterial(MaterialIds.desh, 2, ORDER_COMPAT + ORDER_GENERAL);
        addCompatMetalMaterial(MaterialIds.calorite, 2, ORDER_COMPAT + ORDER_GENERAL);
        addCompatMetalMaterial(MaterialIds.ostrum, 2, ORDER_COMPAT + ORDER_GENERAL);
        addCompatMetalMaterial(MaterialIds.manaSteel, 3, ORDER_COMPAT + ORDER_GENERAL);
        addCompatMetalMaterial(MaterialIds.neptunium, 3, ORDER_COMPAT + ORDER_GENERAL);
        addCompatMetalMaterial(MaterialIds.soulStainedSteel, 3, ORDER_COMPAT + ORDER_GENERAL);
        addCompatMetalMaterial(MaterialIds.pendoriteAlloy, 4, ORDER_COMPAT + ORDER_GENERAL);
    }

}
