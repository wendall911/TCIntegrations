package tcintegrations.items.modifiers.armor;

import org.jetbrains.annotations.NotNull;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraftforge.network.PacketDistributor;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.TinkerHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.util.ModifierHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import vazkii.botania.api.mana.ManaItemHandler;

import tcintegrations.common.capabilities.CapabilityRegistry;
import tcintegrations.items.TCIntegrationsModifiers;
import tcintegrations.network.BotaniaSetData;
import tcintegrations.network.NetworkHandler;
import tcintegrations.util.BotaniaClientHelper;
import tcintegrations.util.BotaniaHelper;

public class TerrestrialModifier extends Modifier implements InventoryTickModifierHook, EquipmentChangeModifierHook {

    private static final int MANA_PER_DAMAGE = 70;

    @Override
    protected void registerHooks(ModifierHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, TinkerHooks.INVENTORY_TICK, TinkerHooks.EQUIPMENT_CHANGE);
    }

    public int getManaPerDamage(ServerPlayer sp) {
        return BotaniaHelper.getManaPerDamageBonus(sp, MANA_PER_DAMAGE);
    }

    @Override
    public @NotNull Component getDisplayName(int level) {
        return applyStyle(Component.translatable(getTranslationKey()));
    }

    @Override
    public MutableComponent applyStyle(MutableComponent component) {
        if (BotaniaClientHelper.hasTerrestrialArmorSet()) {
            return component.withStyle(style -> style.withColor(getTextColor()));
        }
        else {
            return component.withStyle(ChatFormatting.GRAY).withStyle(ChatFormatting.STRIKETHROUGH);
        }
    }

    @Override
    public void onEquip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
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
                    new BotaniaSetData(hasSet, data.hasGreatFairy(), data.hasAlfheim())
                );
            });
        }
    }

    @Override
    public void onUnequip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        final Player player = context.getEntity() instanceof Player ? (Player) context.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;

            sp.getCapability(CapabilityRegistry.BOTANIA_SET_CAPABILITY).ifPresent(data -> {
                data.setTerrestrial(false);

                NetworkHandler.INSTANCE.send(
                    PacketDistributor.PLAYER.with(() -> sp),
                    new BotaniaSetData(false, data.hasGreatFairy(), data.hasAlfheim())
                );
            });
        }
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        final Player player = holder instanceof Player ? (Player) holder : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;

            // Only fire for one armor item
            if (itemSlot == EquipmentSlot.HEAD.getIndex()) {

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
                if (sp.tickCount % 10 == 0) {
                    BotaniaHelper.dispatchManaExact(sp, 10);
                }
            }

            // Heal armor if damaged and has mana source
            if (sp.tickCount % 20 == 0
                    && tool.getDamage() > 0
                    && ManaItemHandler.instance().requestManaExactForTool(stack, sp, getManaPerDamage(sp) * 2, true)) {
                tool.setDamage(tool.getDamage() - 1);
            }
        }
    }

    public boolean hasArmorSetItem(ServerPlayer sp, EquipmentSlot slot) {
        ItemStack stack = sp.getItemBySlot(slot);

        if (stack.isEmpty()) return false;

        ToolStack armor = ToolStack.from(stack);

        if (armor.isBroken()) return false;

        return armor.getUpgrades().getLevel(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId()) > 0;
    }

}