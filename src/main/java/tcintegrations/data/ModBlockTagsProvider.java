package tcintegrations.data;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.TCIntegrations;
import tcintegrations.common.TagManager;

public class ModBlockTagsProvider extends IntrinsicHolderTagsProvider<Block> {

    public ModBlockTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(output, Registries.BLOCK, lookupProvider, (block) -> block.builtInRegistryHolder().key(), TCIntegrations.MODID, existingFileHelper);
    }

    @Override
    public @NotNull String getName() {
        return "TCIntegrations - Block Tags";
    }

    @Override 
    protected void addTags(HolderLookup.@NotNull Provider provider) {
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
    }

}
