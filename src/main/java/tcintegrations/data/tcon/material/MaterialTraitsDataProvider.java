package tcintegrations.data.tcon.material;

import net.minecraft.data.PackOutput;

import slimeknights.tconstruct.library.data.material.AbstractMaterialDataProvider;
import slimeknights.tconstruct.library.data.material.AbstractMaterialTraitDataProvider;
import slimeknights.tconstruct.tools.TinkerModifiers;
import slimeknights.tconstruct.tools.data.ModifierIds;

import static slimeknights.tconstruct.library.materials.MaterialRegistry.ARMOR;
import static slimeknights.tconstruct.library.materials.MaterialRegistry.MELEE_HARVEST;

public class MaterialTraitsDataProvider extends AbstractMaterialTraitDataProvider {

    public MaterialTraitsDataProvider(PackOutput packOutput, AbstractMaterialDataProvider materials) {
        super(packOutput, materials);
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
        addTraits(MaterialIds.dragonsteelFire, ARMOR, ModifierIds.ductile, TciModifierIds.dragonScales);
        addTraits(MaterialIds.dragonsteelIce, ARMOR, ModifierIds.ductile, TciModifierIds.dragonScales);
        addTraits(MaterialIds.dragonsteelLightning, ARMOR, ModifierIds.ductile, TciModifierIds.dragonScales);
        addTraits(MaterialIds.dragonsteelFire, MELEE_HARVEST, TciModifierIds.flamed);
        addTraits(MaterialIds.dragonsteelIce, MELEE_HARVEST, TciModifierIds.iced);
        addTraits(MaterialIds.dragonsteelLightning, MELEE_HARVEST, TciModifierIds.zapped);
    }

}
