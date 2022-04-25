package tcintegrations.data;

import java.util.Arrays;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.Tags;

import tcintegrations.common.TagManager;
import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.TCIntegrations;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagsProvider, TCIntegrations.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "TCIntegrations - Item Tags";
    }

    @Override
    protected void addTags() {
        this.copy(TagManager.Blocks.BRONZE, TagManager.Items.BRONZE);

        this.tag(Tags.Items.INGOTS).add(TCIntegrationsItems.BRONZE.getIngot());
        this.tag(Tags.Items.NUGGETS).add(TCIntegrationsItems.BRONZE.getNugget());

        getBuilder(TagManager.Items.BRONZE_INGOTS).add(TCIntegrationsItems.BRONZE.getIngot());
        getBuilder(TagManager.Items.BRONZE_NUGGETS).add(TCIntegrationsItems.BRONZE.getNugget());
    }

    private void builder(TagKey<Item> tag) {
        getBuilder(tag);
    }

    private void builder(TagKey<Item> tag, ItemLike... items) {
        getBuilder(tag).add(Arrays.stream(items).map(ItemLike::asItem).toArray(Item[]::new));
    }

    protected TagsProvider.TagAppender<Item> getBuilder(TagKey<Item> tag) {
        return tag(tag);
    }

}
