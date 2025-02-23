package tcintegrations.data.client;

import java.util.Objects;

import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;

import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

import tcintegrations.TCIntegrations;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput packOutput, ExistingFileHelper existingFileHelper) {
        super(packOutput, TCIntegrations.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "TCIntegrations - Item Models";
    }

    @Override
    protected void registerModels() {
        ModelFile itemGenerated = getExistingFile(mcLoc("item/generated"));
        ModelFile itemHandheld = getExistingFile(mcLoc("item/handheld"));
    }

    private ItemModelBuilder builder(ModelFile itemGenerated, Item item) {
        String name = Objects.requireNonNull(item.toString());

        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

}
