package tcintegrations.items.modifiers.tool;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import net.minecraftforge.common.ForgeSpawnEggItem;

import shadows.apotheosis.spawn.SpawnerModule;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ProcessLootModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class CapturingModifier extends Modifier implements ProcessLootModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addHook(this, ModifierHooks.PROCESS_LOOT);
    }

    @Override
    public void processLoot(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier, @NotNull List<ItemStack> generatedLoot, @NotNull LootContext context) {
        if (!context.hasParam(LootContextParams.DAMAGE_SOURCE)) {
            return;
        }

        Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);

        if (entity != null) {
            float level = modifier.getEffectiveLevel();

            if (SpawnerModule.bannedMobs.contains(EntityType.getKey(entity.getType()))) return;

            if (entity.level.random.nextFloat() < level / 250F) {
                Item eggItem = ForgeSpawnEggItem.fromEntityType(entity.getType());

                if (eggItem == null) return;

                ItemStack egg = new ItemStack(eggItem);

                generatedLoot.add(egg);
            }
        }
    }

}
