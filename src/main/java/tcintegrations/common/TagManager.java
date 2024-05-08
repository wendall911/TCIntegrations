package tcintegrations.common;

import java.util.Collections;
import java.util.Objects;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
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

        // Malum
        public static final TagKey<Item> SOUL_STAINED_STEEL = forgeTag("storage_blocks/soul_stained_steel");
        public static final TagKey<Item> SOUL_STAINED_STEEL_INGOTS = forgeTag("ingots/soul_stained_steel");
        public static final TagKey<Item> SOUL_STAINED_STEEL_NUGGETS = forgeTag("nuggets/soul_stained_steel");

        // BYG
        public static final TagKey<Item> EMERALDITE_SHARDS = create("emeraldite_shards");
        public static final TagKey<Item> EMERALDITE_ORE = create("emeraldite_ore");
        public static final TagKey<Item> PENDORITE_ALLOY_INGOTS = forgeTag("ingots/pendorite_alloy");

        // Ice and Fire: Dragons
        public static final TagKey<Item> WITHER_BONES = forgeTag("bones/wither");

        private static TagKey<Item> create(String id) {
            return Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).createOptionalTagKey(identifier(id), Collections.emptySet());
        }

        private static TagKey<Item> forgeTag(String name) {
            return Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).createOptionalTagKey(forgeLoc(name), Collections.emptySet());
        }

    }

    public static final class Blocks {
        public static final TagKey<Block> BRONZE = forgeTag("storage_blocks/bronze");

        // Malum
        public static final TagKey<Block> SOUL_STAINED_STEEL = forgeTag("storage_blocks/soul_stained_steel");

        private static TagKey<Block> create(String id) {
            return Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).createOptionalTagKey(identifier(id), Collections.emptySet());
        }

        private static TagKey<Block> forgeTag(String name) {
            return Objects.requireNonNull(ForgeRegistries.BLOCKS.tags()).createOptionalTagKey(forgeLoc(name), Collections.emptySet());
        }
    }

    public static final class EntityTypes {
        public static final TagKey<EntityType<?>> ELEMENTAL_SEVERING_MOBS = create("elemental_severing_mods");
        public static final TagKey<EntityType<?>> MILK_PRODUCER = create("milk_producer");

        private static TagKey<EntityType<?>> create(String id) {
            return Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags()).createOptionalTagKey(identifier(id), Collections.emptySet());
        }
    }

    public static ResourceLocation identifier(String path) {
        return new ResourceLocation(TCIntegrations.MODID, path);
    }

    public static ResourceLocation forgeLoc(String path) {
        return new ResourceLocation("forge", path);
    }

}
