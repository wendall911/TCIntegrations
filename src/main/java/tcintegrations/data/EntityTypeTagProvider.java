package tcintegrations.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;

import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;

import tcintegrations.TCIntegrations;
import tcintegrations.common.TagManager;
import tcintegrations.data.integration.ModIntegration;

public class EntityTypeTagProvider extends EntityTypeTagsProvider {

    public EntityTypeTagProvider(DataGenerator dataGenerator, ExistingFileHelper existingFileHelper) {
        super(dataGenerator, TCIntegrations.MODID, existingFileHelper);
    }

    @Override
    public String getName() {
        return "TCIntegrations - EntityType Tags";
    }

    @Override
    public void addTags() {
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
