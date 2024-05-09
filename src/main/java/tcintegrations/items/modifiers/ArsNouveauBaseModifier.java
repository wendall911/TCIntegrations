package tcintegrations.items.modifiers;

import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap.Builder;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class ArsNouveauBaseModifier extends NoLevelsModifier implements InventoryTickModifierHook {

    @Override
    protected void registerHooks(Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK);
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        final Player player = holder instanceof Player ? (Player) holder : null;

        if (player != null
                && !player.level.isClientSide
                && holder.tickCount % 200 == 0
                && tool.getDamage() > 0) {
            final ServerPlayer sp = (ServerPlayer) player;

            sp.getCapability(CapabilityRegistry.MANA_CAPABILITY).ifPresent(mana -> {
                if (mana.getCurrentMana() > 20) {
                    mana.removeMana(20);
                    tool.setDamage(tool.getDamage() - 1);
                }
            });
        }
    }

}
