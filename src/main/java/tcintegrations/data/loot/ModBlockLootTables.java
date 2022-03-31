package tcintegrations.data.loot;

import java.util.function.Function;
import java.util.stream.Collectors;

import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import net.minecraftforge.registries.ForgeRegistries;

import tcintegrations.common.TagManager;
import tcintegrations.config.ConfigHandler;
import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.TCIntegrations;

public class ModBlockLootTables extends BlockLoot {

    @Override
    protected void addTables() {
        this.dropSelf(TCIntegrationsItems.BRONZE.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> TCIntegrations.MODID.equals(block.getRegistryName().getNamespace()))
                .collect(Collectors.toSet());
    }

}
