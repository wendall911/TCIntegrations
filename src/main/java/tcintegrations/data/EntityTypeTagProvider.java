package tcintegrations.data;

import java.util.concurrent.CompletableFuture;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;

import net.minecraftforge.common.data.ExistingFileHelper;

import tcintegrations.TCIntegrations;
import tcintegrations.common.TagManager;
import tcintegrations.data.integration.ModIntegration;

public class EntityTypeTagProvider extends EntityTypeTagsProvider {

    public EntityTypeTagProvider(PackOutput packOutput, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
        super(packOutput, lookupProvider, TCIntegrations.MODID, existingFileHelper);
    }

    @Override
    public @NotNull String getName() {
        return "TCIntegrations - EntityType Tags";
    }

    @Override
    public void addTags(HolderLookup.@NotNull Provider provider) {
        this.tag(TagManager.EntityTypes.ELEMENTAL_SEVERING_MOBS)
            .add(EntityType.WITHER_SKELETON)
            .add(EntityType.SKELETON)
            .add(EntityType.ZOMBIE)
            .add(EntityType.ZOMBIE_VILLAGER)
            .add(EntityType.HUSK)
            .add(EntityType.DROWNED)
            .add(EntityType.CREEPER)
            .addOptional(ModIntegration.botaniaLoc("doppleganger"));

        // Beyond Earth
        this.tag(TagManager.EntityTypes.MILK_PRODUCER).add(EntityType.COW, EntityType.GOAT, EntityType.HORSE);
    }

}
