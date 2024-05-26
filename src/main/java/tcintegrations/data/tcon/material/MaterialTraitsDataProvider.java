package tcintegrations.data.tcon.material;

import net.minecraft.data.DataGenerator;

import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.data.ModifierIds;

import static slimeknights.tconstruct.tools.data.material.MaterialIds.osmium;

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
        addDefaultTraits(MaterialIds.manaString, TciModifierIds.mana);
        addDefaultTraits(MaterialIds.brass, TciModifierIds.moderate);
        addDefaultTraits(MaterialIds.neptunium, TciModifierIds.waterPowered);
        addDefaultTraits(MaterialIds.soulStainedSteel, TciModifierIds.soulStained);
        addDefaultTraits(MaterialIds.desh, TciModifierIds.oxygenated);
        addDefaultTraits(MaterialIds.calorite, TciModifierIds.oxygenated);
        addDefaultTraits(MaterialIds.ostrum, TciModifierIds.oxygenated);
        addDefaultTraits(MaterialIds.pendoriteAlloy, TciModifierIds.hellish);
        addDefaultTraits(osmium, ModifierIds.dense, TciModifierIds.kinetic);
        addDefaultTraits(MaterialIds.dragonsteelFire, ModifierIds.ductile, TciModifierIds.dragonScales);
        addDefaultTraits(MaterialIds.dragonsteelIce, ModifierIds.ductile, TciModifierIds.dragonScales);
        addDefaultTraits(MaterialIds.dragonsteelLightning, ModifierIds.ductile, TciModifierIds.dragonScales);
    }

}
