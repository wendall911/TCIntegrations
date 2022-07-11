package tcintegrations.items.armor.modifiers;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraftforge.network.PacketDistributor;

import org.jetbrains.annotations.NotNull;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import vazkii.botania.api.mana.ManaItemHandler;

import tcintegrations.common.capabilities.CapabilityRegistry;
import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.network.BotaniaSetData;
import tcintegrations.network.NetworkHandler;
import tcintegrations.util.BotaniaHelper;

public class TerrestrialModifier extends Modifier {

    private static final int MANA_PER_DAMAGE = 70;

    public int getManaPerDamage(Player player) {
        return BotaniaHelper.getManaPerDamageBonus(player, MANA_PER_DAMAGE);
    }

    @Override
    public @NotNull Component getDisplayName(int level) {
        return applyStyle(new TranslatableComponent(getTranslationKey()));
    }

    @Override
    public MutableComponent applyStyle(MutableComponent component) {
        if (BotaniaHelper.hasTerrestrialArmorSet()) {
            return component.withStyle(style -> style.withColor(getTextColor()));
        }
        else {
            return component.withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.STRIKETHROUGH);
        }
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
                data.setTerrestrial(false);

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
                // Only fire for one armor item
                && itemSlot == EquipmentSlot.HEAD.getIndex()) {
            final ServerPlayer sp = (ServerPlayer) player;

            if (BotaniaHelper.hasTerrestrialArmorSet(sp)) {
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

}
