package tcintegrations.common;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.common.Tags.IOptionalNamedTag;

import tcintegrations.TCIntegrations;

public final class TagManager {

    public static final class Items {
        public static final IOptionalNamedTag<Item> BRONZE = forgeTag("storage_blocks/bronze");
        public static final IOptionalNamedTag<Item> BRONZE_INGOTS = forgeTag("ingots/bronze");
        public static final IOptionalNamedTag<Item> BRONZE_NUGGETS = forgeTag("nuggets/bronze");

        private static Tag.Named<Item> create(String id) {
            return ItemTags.createOptional(identifier(id));
        }

        private static IOptionalNamedTag<Item> forgeTag(String name) {
            return ItemTags.createOptional(forgeLoc(name));
        }
    }

    public static final class Blocks {
        public static final IOptionalNamedTag<Block> BRONZE = forgeTag("storage_blocks/bronze");

        private static Tag.Named<Block> create(String id) {
            return BlockTags.createOptional(identifier(id));
        }

        private static IOptionalNamedTag<Block> forgeTag(String name) {
            return BlockTags.createOptional(forgeLoc(name));
        }
    }

    public static ResourceLocation identifier(String path) {
        return new ResourceLocation(TCIntegrations.MODID, path);
    }

    public static ResourceLocation forgeLoc(String path) {
        return new ResourceLocation("forge", path);
    }

}
