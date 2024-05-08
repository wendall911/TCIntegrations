package tcintegrations.data.client;

import net.minecraft.data.DataGenerator;

import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

import tcintegrations.TCIntegrations;

public class ModBlockStateProvider extends BlockStateProvider {

    public ModBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, TCIntegrations.MODID, exFileHelper);
    }

    @Override
    public String getName() {
        return "TCIntegrations - Block State and Models";
    }

    @Override
    protected void registerStatesAndModels() {
        generateBronzeModels();
    }

    private void generateBronzeModels() {
    }

}
