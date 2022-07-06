package tcintegrations.data.tcon.material;

import net.minecraft.data.DataGenerator;

import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.data.ModifierIds;

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
        addDefaultTraits(MaterialIds.livingWood, TciModifierIds.livingwood);
        addDefaultTraits(MaterialIds.livingRock, TinkerModifiers.stonebound);
        addDefaultTraits(MaterialIds.manaSteel, ModifierIds.ductile, TciModifierIds.mana);
    }

}
