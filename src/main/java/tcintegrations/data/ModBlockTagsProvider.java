package tcintegrations.data;

import java.util.Arrays;

import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import slimeknights.mantle.registration.object.MetalItemObject;

import slimeknights.tconstruct.common.TinkerTags;

import tcintegrations.data.integration.ModIntegration;
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
        this.tag(TagManager.Blocks.SOUL_STAINED_STEEL).addOptional(ModIntegration.malumLoc("block_of_soul_stained_steel"));
        this.tag(Tags.Blocks.STORAGE_BLOCKS)
            .addTag(TagManager.Blocks.BRONZE)
            .addTag(TagManager.Blocks.SOUL_STAINED_STEEL);
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE).addTag(TagManager.Blocks.BRONZE);
        this.tag(BlockTags.NEEDS_STONE_TOOL).addTag(TagManager.Blocks.BRONZE);
        this.tag(TagManager.Blocks.DRAGONSTEEL_FIRE).addOptional(ModIntegration.ifdLoc("dragonsteel_fire_block"));
        this.tag(TagManager.Blocks.DRAGONSTEEL_ICE).addOptional(ModIntegration.ifdLoc("dragonsteel_ice_block"));
        this.tag(TagManager.Blocks.DRAGONSTEEL_LIGHTNING).addOptional(ModIntegration.ifdLoc("dragonsteel_lightning_block"));
        this.tag(TagManager.Blocks.SOURCE_GEM_BLOCK).addOptional(ModIntegration.arsLoc("source_gem_block"));

        TagsProvider.TagAppender<Block> builder = this.tag(TinkerTags.Blocks.ANVIL_METAL);

        for (SmelteryCompat compat : SmelteryCompat.values()) {
            builder.addOptionalTag(new ResourceLocation("forge", "storage_blocks/" + compat.getName()));
        }

        addMetalTags(TCIntegrationsItems.BRONZE);
    }

    private void builder(TagKey<Block> tag, IBlockProvider... items) {
        getBuilder(tag).add(Arrays.stream(items).map(IBlockProvider::asBlock).toArray(Block[]::new));
    }

    protected TagsProvider.TagAppender<Block> getBuilder(TagKey<Block> tag) {
        return tag(tag);
    }

    private void addMetalTags(MetalItemObject metal) {
        this.tag(Tags.Blocks.STORAGE_BLOCKS).addTag(metal.getBlockTag());
    }

}
