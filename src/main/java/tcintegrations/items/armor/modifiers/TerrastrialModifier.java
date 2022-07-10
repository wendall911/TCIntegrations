package tcintegrations.items.armor.modifiers;

import java.util.concurrent.atomic.AtomicBoolean;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraftforge.network.PacketDistributor;

import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import vazkii.botania.api.mana.ManaItemHandler;

import tcintegrations.common.capabilities.CapabilityRegistry;
import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.network.BotaniaSetData;
import tcintegrations.network.NetworkHandler;
import tcintegrations.util.BotaniaHelper;

public class TerrastrialModifier extends NoLevelsModifier {

    private static final int MANA_PER_DAMAGE = 70;

    public int getManaPerDamage(Player player) {
        return BotaniaHelper.getManaPerDamageBonus(player, MANA_PER_DAMAGE);
    }

    @Override
    public void onEquip(IToolStackView tool, int level, EquipmentChangeContext context) {
        final Player player = context.getEntity() instanceof Player ? (Player) context.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;

            sp.getCapability(CapabilityRegistry.BOTANIA_SET_CAPABILITY).ifPresent(data -> {
                boolean hasSet =
                        hasArmorSetItem(sp, EquipmentSlot.HEAD) &&
                        hasArmorSetItem(sp, EquipmentSlot.CHEST) &&
                        hasArmorSetItem(sp, EquipmentSlot.LEGS) &&
                        hasArmorSetItem(sp, EquipmentSlot.FEET);

                data.setTerrestrial(hasSet);

                NetworkHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> sp),
                        new BotaniaSetData(hasSet, data.hasGreatFairy())
                );
            });
        }
    }

    @Override
    public void onUnequip(IToolStackView tool, int level, EquipmentChangeContext context) {
        final Player player = context.getEntity() instanceof Player ? (Player) context.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;

            sp.getCapability(CapabilityRegistry.BOTANIA_SET_CAPABILITY).ifPresent(data -> {
                NetworkHandler.INSTANCE.send(
                        PacketDistributor.PLAYER.with(() -> sp),
                        new BotaniaSetData(false, data.hasGreatFairy())
                );
            });
        }
    }

    @Override
    public void onInventoryTick(IToolStackView tool, int level, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide
                && holder instanceof Player player
                && itemSlot == EquipmentSlot.HEAD.getIndex() // Only fire for one armor item
        ) {
            final ServerPlayer sp = (ServerPlayer) player;

            if (hasArmorSet(sp)) {
                // Heal player
                if (sp.tickCount % 80 == 0) {
                    int food = sp.getFoodData().getFoodLevel();

                    if (food > 0 && food < 18 && sp.isHurt()) {
                        sp.heal(1F);
                    }
                }
            }

            // Mana generation
            if (player.tickCount % 10 == 0) {
                BotaniaHelper.dispatchManaExact(player, 10);
            }
        }

        // Heal armor if damaged and has mana source
        if (!world.isClientSide
                && holder.tickCount % 20 == 0
                && holder instanceof Player player
                && tool.getDamage() > 0
                && ManaItemHandler.instance().requestManaExactForTool(stack, player, getManaPerDamage(player) * 2, true)) {
            tool.setDamage(tool.getDamage() - 1);
        }
    }

    public boolean hasArmorSetItem(ServerPlayer sp, EquipmentSlot slot) {
        ItemStack stack = sp.getItemBySlot(slot);

        if (stack.isEmpty()) return false;

        ToolStack armor = ToolStack.from(stack);

        if (armor.isBroken()) return false;

        return armor.getUpgrades().getLevel(TCIntegrationsItems.TERRESTRIAL_MODIFIER.getId()) > 0;
    }

    public static boolean hasArmorSet(ServerPlayer sp) {
        AtomicBoolean hasSet = new AtomicBoolean(false);

        sp.getCapability(CapabilityRegistry.BOTANIA_SET_CAPABILITY).ifPresent(data -> {
            hasSet.set(data.hasTerrestrial());
        });

        return hasSet.get();
    }

}
