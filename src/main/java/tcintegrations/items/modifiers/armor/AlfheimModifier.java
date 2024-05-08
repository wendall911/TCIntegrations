package tcintegrations.items.modifiers.armor;

import org.jetbrains.annotations.NotNull;

import mythicbotany.config.MythicConfig;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import net.minecraftforge.common.ForgeMod;
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
import tcintegrations.items.modifiers.hooks.IArmorJumpModifier;
import tcintegrations.items.TCIntegrationHooks;
import tcintegrations.items.TCIntegrationsModifiers;
import tcintegrations.network.BotaniaSetData;
import tcintegrations.network.NetworkHandler;
import tcintegrations.util.BotaniaClientHelper;
import tcintegrations.util.BotaniaHelper;

public class AlfheimModifier extends Modifier implements IArmorJumpModifier, EquipmentChangeModifierHook, InventoryTickModifierHook {

    private static final int MANA_PER_DAMAGE = 110;

    private static final AttributeModifier HELMET_REACH_DISTANCE = new AttributeModifier(
            ArmorItem.ARMOR_MODIFIER_UUID_PER_SLOT[EquipmentSlot.HEAD.getIndex()],
            "Helmet Reach Distance",
            MythicConfig.alftools.reach_modifier,
            AttributeModifier.Operation.ADDITION
    );

    private static final AttributeModifier CHESTPLATE_KNOCKBACK_RESISTANCE = new AttributeModifier(
            ArmorItem.ARMOR_MODIFIER_UUID_PER_SLOT[EquipmentSlot.CHEST.getIndex()],
            "Chestplate Knockback Resistance",
            MythicConfig.alftools.knockback_resistance_modifier,
            AttributeModifier.Operation.ADDITION
    );

    private static final AttributeModifier LEGGINGS_MOVEMENT_SPEED = new AttributeModifier(
            ArmorItem.ARMOR_MODIFIER_UUID_PER_SLOT[EquipmentSlot.LEGS.getIndex()],
            "Leggings Movement Speed",
            MythicConfig.alftools.speed_modifier,
            AttributeModifier.Operation.ADDITION
    );

    private static final AttributeModifier LEGGINGS_SWIM_SPEED = new AttributeModifier(
            ArmorItem.ARMOR_MODIFIER_UUID_PER_SLOT[EquipmentSlot.LEGS.getIndex()],
            "Leggings Swim Speed",
            MythicConfig.alftools.speed_modifier,
            AttributeModifier.Operation.ADDITION
    );
    @Override
    protected void registerHooks(ModifierHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, TCIntegrationHooks.JUMP, TinkerHooks.EQUIPMENT_CHANGE, TinkerHooks.INVENTORY_TICK);
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
        if (BotaniaClientHelper.hasAlfheimArmorSet()) {
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

                data.setAlfheim(hasSet);

                NetworkHandler.INSTANCE.send(
                    PacketDistributor.PLAYER.with(() -> sp),
                    new BotaniaSetData(data.hasTerrestrial(), data.hasGreatFairy(), hasSet)
                );
            });

            changeEquipment(sp, context, false);
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
                        new BotaniaSetData(data.hasTerrestrial(), data.hasGreatFairy(), false)
                );
            });

            changeEquipment(sp, context, true);
        }
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        final Player player = holder instanceof Player ? (Player) holder : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;

            // Heal armor if damaged and has mana source
            if (sp.tickCount % 20 == 0
                    && tool.getDamage() > 0
                    && ManaItemHandler.instance().requestManaExactForTool(stack, sp, getManaPerDamage(sp), true)) {
                tool.setDamage(tool.getDamage() - 1);
            }
        }
    }

    public boolean hasArmorSetItem(ServerPlayer sp, EquipmentSlot slot) {
        ItemStack stack = sp.getItemBySlot(slot);

        if (stack.isEmpty()) return false;

        ToolStack armor = ToolStack.from(stack);

        if (armor.isBroken()) return false;

        return armor.getUpgrades().getLevel(TCIntegrationsModifiers.ALFHEIM_MODIFIER.getId()) > 0;
    }

    public void changeEquipment(ServerPlayer sp, EquipmentChangeContext context, boolean remove) {
        final AttributeInstance reachDistance = sp.getAttribute(ForgeMod.REACH_DISTANCE.get());
        final AttributeInstance knockbackResistance = sp.getAttribute(Attributes.KNOCKBACK_RESISTANCE);
        final AttributeInstance movementSpeed = sp.getAttribute(Attributes.MOVEMENT_SPEED);
        final AttributeInstance swimSpeed = sp.getAttribute(ForgeMod.SWIM_SPEED.get());

        if (context.getChangedSlot() == EquipmentSlot.LEGS && movementSpeed != null) {
            if (remove && movementSpeed.hasModifier(LEGGINGS_MOVEMENT_SPEED)) {
                movementSpeed.removeModifier(LEGGINGS_MOVEMENT_SPEED);
            }
            else if (!movementSpeed.hasModifier(LEGGINGS_MOVEMENT_SPEED)) {
                movementSpeed.addPermanentModifier(LEGGINGS_MOVEMENT_SPEED);
            }
        }

        if (context.getChangedSlot() == EquipmentSlot.LEGS && swimSpeed != null) {
            if (remove && swimSpeed.hasModifier(LEGGINGS_SWIM_SPEED)) {
                swimSpeed.removeModifier(LEGGINGS_SWIM_SPEED);
            }
            else if (!swimSpeed.hasModifier(LEGGINGS_SWIM_SPEED)) {
                swimSpeed.addPermanentModifier(LEGGINGS_SWIM_SPEED);
            }
        }

        if (context.getChangedSlot() == EquipmentSlot.CHEST && knockbackResistance != null) {
            if (remove && knockbackResistance.hasModifier(CHESTPLATE_KNOCKBACK_RESISTANCE)) {
                knockbackResistance.removeModifier(CHESTPLATE_KNOCKBACK_RESISTANCE);
            }
            else if (!knockbackResistance.hasModifier(CHESTPLATE_KNOCKBACK_RESISTANCE)) {
                knockbackResistance.addPermanentModifier(CHESTPLATE_KNOCKBACK_RESISTANCE);
            }
        }
        if (context.getChangedSlot() == EquipmentSlot.HEAD && reachDistance != null) {
            if (remove && reachDistance.hasModifier(HELMET_REACH_DISTANCE)) {
                reachDistance.removeModifier(HELMET_REACH_DISTANCE);
            }
            else if (!reachDistance.hasModifier(HELMET_REACH_DISTANCE)) {
                reachDistance.addPermanentModifier(HELMET_REACH_DISTANCE);
            }
        }
    }

    @Override
    public void onJump(IToolStackView tool, LivingEntity living) {
        float rot = living.getYRot() * ((float)Math.PI / 180F);
        float xzFactor = living.isSprinting() ? MythicConfig.alftools.jump_modifier : 0;

        living.setDeltaMovement(living.getDeltaMovement().add(-Mth.sin(rot) * xzFactor, MythicConfig.alftools.jump_modifier, Mth.cos(rot) * xzFactor));
    }

}