package tcintegrations.data.tcon.sprite;

import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;
import slimeknights.tconstruct.library.client.data.spritetransformer.GreyToColorMapping;
import slimeknights.tconstruct.tools.data.sprite.TinkerPartSpriteProvider;

import tcintegrations.data.tcon.material.MaterialIds;

public class TinkerMaterialSpriteProvider extends AbstractMaterialSpriteProvider {

    @Override
    public String getName() {
        return "TCIntegrations - TCon Materials";
    }

    @Override
    protected void addAllMaterials() {
        // tier 1
        buildMaterial(MaterialIds.livingWood)
            .meleeHarvest()
            .fallbacks("wood", "stick", "primitive")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF330704).addARGB(102, 0xFF380A04).addARGB(140, 0xFF4B190A).addARGB(178, 0xFF511E0B).addARGB(216, 0xFF54210D).addARGB(255, 0xFF5E2409).build());
        buildMaterial(MaterialIds.livingRock)
            .meleeHarvest()
            .fallbacks("rock")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF9B7E64).addARGB(102, 0xFFA89C78).addARGB(140, 0xFFB1A283).addARGB(178, 0xFFB9AA97).addARGB(216, 0xFFCDCBC1).addARGB(255, 0xFFF4F2EC).build());
        // tier 3
        buildMaterial(MaterialIds.manaSteel)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF001944).addARGB(102, 0xFF00296D).addARGB(140, 0xFF0043A5).addARGB(178, 0xFF186ACE).addARGB(216, 0xFF3389FF).addARGB(255, 0xFF59A6EF).build());
        buildMaterial(MaterialIds.neptunium)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF01140F).addARGB(102, 0xFF03503A).addARGB(140, 0xFF056B50).addARGB(178, 0xFF0AE2A7).addARGB(216, 0xFF17F4B8).addARGB(255, 0xFF8CFBDC).build());
        buildMaterial(MaterialIds.soulStainedSteel)
            .meleeHarvest().statType(TinkerPartSpriteProvider.PLATE)
            .fallbacks("metal")
            .colorMapper(GreyToColorMapping.builderFromBlack().addARGB(63, 0xFF2D2349).addARGB(102, 0xFF40365B).addARGB(140, 0xFF593b7C).addARGB(178, 0xFF8A5EAE).addARGB(216, 0xFFA96EC7).addARGB(255, 0xFFEE8FFF).build());
    }

}