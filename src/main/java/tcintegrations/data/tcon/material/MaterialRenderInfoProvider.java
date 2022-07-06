package tcintegrations.data.tcon.material;

import net.minecraft.data.DataGenerator;

import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;

public class MaterialRenderInfoProvider extends AbstractMaterialRenderInfoProvider {

    public MaterialRenderInfoProvider(DataGenerator gen, AbstractMaterialSpriteProvider spriteProvider) {
        super(gen, spriteProvider);
    }

    @Override
    public String getName() {
        return "TCIntegrations - TCon Material Render Info Provider";
    }

    @Override
    protected void addMaterialRenderInfo() {
        buildRenderInfo(MaterialIds.livingWood).color(0x5E2409).fallbacks("wood", "stick", "primitive");
        buildRenderInfo(MaterialIds.livingRock).color(0xDFE2D4).fallbacks("rock");
        buildRenderInfo(MaterialIds.manaSteel).color(0x3389FF).fallbacks("metal");
    }

}
