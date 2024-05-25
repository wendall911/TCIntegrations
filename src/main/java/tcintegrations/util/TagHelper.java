package tcintegrations.util;

import java.util.Objects;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;

public class TagHelper {

    public static ITag<Item> getTag(ResourceLocation loc) {
        return getTag(TagKey.create(Registry.ITEM_REGISTRY, loc));
    }

    public static ITag<Item> getTag(TagKey<Item> name) {
        return Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).getTag(name);
    }

}
