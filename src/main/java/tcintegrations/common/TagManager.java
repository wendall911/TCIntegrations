package tcintegrations.common;

import java.util.Collections;
import java.util.Objects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import net.minecraftforge.registries.ForgeRegistries;

import tcintegrations.TCIntegrations;

public final class TagManager {

    public static final class Items {
        public static final TagKey<Item> BRONZE = forgeTag("storage_blocks/bronze");
        public static final TagKey<Item> BRONZE_INGOTS = forgeTag("ingots/bronze");
        public static final TagKey<Item> BRONZE_NUGGETS = forgeTag("nuggets/bronze");

        // Botania
        public static final TagKey<Item> BOTANIA_LIVINGWOOD_LOGS = create("livingwood_logs");

        private static TagKey<Item> create(String id) {
            return Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).createOptionalTagKey(identifier(id), Collections.emptySet());
        }

        private static TagKey<Item> forgeTag(String name) {
            return Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).createOptionalTagKey(forgeLoc(name), Collections.emptySet());
        }


    }

    public static final class Blocks {
        public static final TagKey<Block> BRONZE = forgeTag("storage_blocks/bronze");

        private static TagKey<Block> create(String id) {
            return Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).createOptionalTagKey(identifier(id), Collections.emptySet());
        }

        private static TagKey<Block> forgeTag(String name) {
            return Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).createOptionalTagKey(forgeLoc(name), Collections.emptySet());
        }
    }

    public static ResourceLocation identifier(String path) {
        return new ResourceLocation(TCIntegrations.MODID, path);
    }

    public static ResourceLocation forgeLoc(String path) {
        return new ResourceLocation("forge", path);
    }

}
