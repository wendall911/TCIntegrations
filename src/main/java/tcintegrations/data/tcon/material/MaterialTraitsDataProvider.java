package tcintegrations.data.tcon.material;

import net.minecraft.data.DataGenerator;

import net.minecraft.data.HashCache;
import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.tools.data.ModifierIds;

import tcintegrations.data.tcon.material.MaterialIds;
import tcintegrations.data.tcon.material.TciModifierIds;
import tcintegrations.items.TCIntegrationsItems;

import java.io.IOException;

public class MaterialTraitsDataProvider extends AbstractMaterialTraitDataProvider {

    public MaterialTraitsDataProvider(DataGenerator gen, AbstractMaterialDataProvider materials) {
        super(gen, materials);
    }

    @Override
    public String getName() {
        return "TCIntegrations - TCon Material Traits";
    }

    @Override
    protected void addMaterialTraits() {
        addDefaultTraits(MaterialIds.manaSteel, ModifierIds.ductile, TciModifierIds.mana);
    }

}
