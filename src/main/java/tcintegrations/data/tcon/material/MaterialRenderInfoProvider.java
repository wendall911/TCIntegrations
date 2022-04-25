package tcintegrations.data.tcon.material;

import net.minecraft.data.DataGenerator;

import net.minecraft.data.HashCache;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialRenderInfoProvider;
import slimeknights.tconstruct.library.client.data.material.AbstractMaterialSpriteProvider;

import tcintegrations.data.tcon.material.MaterialIds;

import java.io.IOException;

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

        buildRenderInfo(MaterialIds.manaSteel).color(0x3389FF).fallbacks("metal");
    }

}
