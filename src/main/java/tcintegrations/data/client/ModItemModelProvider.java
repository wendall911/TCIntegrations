package tcintegrations.data.client;

import java.util.function.Supplier;
import java.util.Optional;

import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

import slimeknights.tconstruct.common.registration.CastItemObject;

import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.TCIntegrations;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, TCIntegrations.MODID, existingFileHelper);
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
        String name = item.getRegistryName().getPath().toString();

        return getBuilder(name).parent(itemGenerated).texture("layer0", "item/" + name);
    }

}
