package tcintegrations.data.loot;

import java.util.Set;

import com.google.common.collect.ImmutableList;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

import tcintegrations.items.TCIntegrationsItems;

public class ModBlockLootTables extends BlockLootSubProvider {

    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(TCIntegrationsItems.BRONZE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ImmutableList.of(TCIntegrationsItems.BRONZE.get());
    }

}
