package tcintegrations.data;

import java.util.Arrays;

import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import slimeknights.mantle.registration.object.MetalItemObject;

import slimeknights.tconstruct.common.TinkerTags;

import tcintegrations.data.tcon.SmelteryCompat;
import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.TCIntegrations;
import tcintegrations.util.IBlockProvider;
import tcintegrations.common.TagManager;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public ModBlockTagsProvider(DataGenerator generatorIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, TCIntegrations.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "TCIntegrations - Block Tags";
    }

    @Override 
    protected void addTags() {
        this.tag(TagManager.Blocks.BRONZE).add(TCIntegrationsItems.BRONZE.get());
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(TagManager.Blocks.BRONZE);

        TagsProvider.TagAppender<Block> builder = this.tag(TinkerTags.Blocks.ANVIL_METAL);

        for (SmelteryCompat compat : SmelteryCompat.values()) {
            builder.addOptionalTag(new ResourceLocation("forge", "storage_blocks/" + compat.getName()));
        }

        addMetalTags(TCIntegrationsItems.BRONZE);
    }

    private void builder(Tag.Named<Block> tag, IBlockProvider... items) {
        getBuilder(tag).add(Arrays.stream(items).map(IBlockProvider::asBlock).toArray(Block[]::new));
    }

    protected TagsProvider.TagAppender<Block> getBuilder(Tag.Named<Block> tag) {
        return tag(tag);
    }

    private void addMetalTags(MetalItemObject metal) {
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(metal.getBlockTag());
    }

}
