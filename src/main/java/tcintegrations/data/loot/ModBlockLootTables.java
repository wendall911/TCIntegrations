package tcintegrations.data.loot;

import com.google.common.collect.ImmutableList;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.level.block.Block;

import tcintegrations.items.TCIntegrationsItems;

public class ModBlockLootTables extends BlockLoot {

    @Override
    protected void addTables() {
        this.dropSelf(TCIntegrationsItems.BRONZE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ImmutableList.of(TCIntegrationsItems.BRONZE.get());
    }

}
