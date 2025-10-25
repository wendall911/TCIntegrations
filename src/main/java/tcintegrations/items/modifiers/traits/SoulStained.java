package tcintegrations.items.modifiers.traits;

import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import com.sammy.malum.registry.common.AttributeRegistry;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.phys.EntityHitResult;

import slimeknights.mantle.client.TooltipKey;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap.Builder;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.tools.TinkerTools;

import team.lodestar.lodestone.registry.common.LodestoneAttributeRegistry;

import static net.minecraft.world.item.ArmorItem.ARMOR_MODIFIER_UUID_PER_TYPE;

import static tcintegrations.util.ResourceLocationHelper.resource;

public class SoulStained extends NoLevelsModifier implements ProjectileHitModifierHook, EquipmentChangeModifierHook, MeleeHitModifierHook, TooltipModifierHook {

    private static final AttributeModifier HELMET_SOUL_WARD_CAP = new AttributeModifier(
        ARMOR_MODIFIER_UUID_PER_TYPE.get(ArmorItem.Type.HELMET),
        "Helmet Soul Ward Cap",
        3.0F,
        AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier HELMET_SOUL_WARD_RECOVERY = new AttributeModifier(
        ARMOR_MODIFIER_UUID_PER_TYPE.get(ArmorItem.Type.HELMET),
        "Helmet Soul Ward Recovery",
        0.15F,
        AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier CHESTPLATE_SOUL_WARD_CAP = new AttributeModifier(
        ARMOR_MODIFIER_UUID_PER_TYPE.get(ArmorItem.Type.CHESTPLATE),
        "Chestplate Soul Ward Cap",
        3.0F,
        AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier CHESTPLATE_SOUL_WARD_RECOVERY = new AttributeModifier(
        ARMOR_MODIFIER_UUID_PER_TYPE.get(ArmorItem.Type.CHESTPLATE),
        "Chestplate Soul Ward Recovery",
        0.15F,
        AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier LEGGINGS_SOUL_WARD_CAP = new AttributeModifier(
        ARMOR_MODIFIER_UUID_PER_TYPE.get(ArmorItem.Type.LEGGINGS),
        "Leggings Soul Ward Cap",
        3.0F,
        AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier LEGGINGS_SOUL_WARD_RECOVERY = new AttributeModifier(
        ARMOR_MODIFIER_UUID_PER_TYPE.get(ArmorItem.Type.LEGGINGS),
        "Leggings Soul Ward Recovery",
        0.15F,
        AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier BOOTS_SOUL_WARD_CAP = new AttributeModifier(
        ARMOR_MODIFIER_UUID_PER_TYPE.get(ArmorItem.Type.BOOTS),
        "Boots Soul Ward Cap",
        3.0F,
        AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier BOOTS_SOUL_WARD_RECOVERY = new AttributeModifier(
        ARMOR_MODIFIER_UUID_PER_TYPE.get(ArmorItem.Type.BOOTS),
        "Boots Soul Ward Recovery",
        0.15F,
        AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier MELEE_PRIMARY_MAGIC_DAMAGE = new AttributeModifier(
        UUID.fromString("cd509ae0-3479-4665-b402-04e66c0ef3fd"),
        "Primary Magic Damage",
        3,
        AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier AXE_MAGIC_DAMAGE = new AttributeModifier(
        UUID.fromString("353ec372-c40f-452b-8152-abace67ca5fd"),
        "Axe Magic Damage",
        4,
        AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier HARVEST_MAGIC_DAMAGE = new AttributeModifier(
        UUID.fromString("4afa8ad7-bbb3-4c75-89c9-e488190790ba"),
        "Harvest Magic Damage",
        2,
        AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier OFFHAND_MELEE_PRIMARY_MAGIC_DAMAGE = new AttributeModifier(
        UUID.fromString("39dc8581-cbc0-4e3b-a7cd-2a016685c7a7"),
        "Offhand Primary Magic Damage",
        3,
        AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier OFFHAND_AXE_MAGIC_DAMAGE = new AttributeModifier(
        UUID.fromString("1d4c724e-3797-463f-9e82-3e881f3511c7"),
        "Offhand Axe Magic Damage",
        4,
        AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier OFFHAND_HARVEST_MAGIC_DAMAGE = new AttributeModifier(
        UUID.fromString("8c109bf8-ed6e-4949-92cb-470c5e7b446f"),
        "Offhand Harvest Magic Damage",
        2,
        AttributeModifier.Operation.ADDITION
    );
    private static final Component SOUL_WARD_CAPACITY = Component.translatable(
        Util.makeDescriptionId("modifier", resource("soul_stained.soul_ward_capacity")));
    private static final Component SOUL_WARD_RECOVERY_RATE = Component.translatable(
        Util.makeDescriptionId("modifier", resource("soul_stained.soul_ward_recovery_rate")));
    private static final Component PRIMARY_MAGIC_DAMAGE = Component.translatable(
        Util.makeDescriptionId("modifier", resource("soul_stained.primary_magic_damage")));
    private static final Component OFFHAND_MAGIC_DAMAGE = Component.translatable(
        Util.makeDescriptionId("modifier", resource("soul_stained.offhand_magic_damage")));

    @Override
    protected void registerHooks(@NotNull Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.PROJECTILE_HIT, ModifierHooks.EQUIPMENT_CHANGE, ModifierHooks.MELEE_HIT, ModifierHooks.TOOLTIP);
    }

    @Override
    public void onEquip(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier, EquipmentChangeContext context) {
        final Player player = context.getEntity() instanceof Player ? (Player) context.getEntity() : null;

        if (player != null && !player.level().isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;

            changeEquipment(sp, context, false);
        }
    }

    @Override
    public void onUnequip(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier, EquipmentChangeContext context) {
        final Player player = context.getEntity() instanceof Player ? (Player) context.getEntity() : null;

        if (player != null && !player.level().isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;

            changeEquipment(sp, context, true);
        }
    }

    @Override
    public float beforeMeleeHit(@NotNull IToolStackView tool, @NotNull ModifierEntry modifier,
            ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        applyExposedSoulDuration(context.getLivingTarget());

        return MeleeHitModifierHook.super.beforeMeleeHit(tool, modifier, context, damage, baseKnockback, knockback);
    }

    @Override
    public boolean onProjectileHitEntity(@NotNull ModifierNBT modifiers, @NotNull ModDataNBT persistentData,
            @NotNull ModifierEntry modifier, @NotNull Projectile projectile,
            EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (hit.getEntity() instanceof LivingEntity living) {
            applyExposedSoulDuration(living);
        }

        return false;
    }

    private void applyExposedSoulDuration(LivingEntity entity) {
        if (entity != null) {
            MalumLivingEntityDataCapability.getCapability(entity).soulData.exposedSoulDuration = 200;
        }
    }

    @Override
    public void addTooltip(IToolStackView tool, @NotNull ModifierEntry modifier, @Nullable Player player,
            @NotNull List<Component> tooltip, @NotNull TooltipKey tooltipKey, @NotNull TooltipFlag tooltipFlag) {
        double soulWardCap = 0.0;
        double soulWardRecoveryRate = 0.0;
        double primaryMagicDamage = 0.0;
        double offhandMagicDamage = 0.0;

        if (tool.hasTag(TinkerTags.Items.HELMETS)) {
            soulWardCap = HELMET_SOUL_WARD_CAP.getAmount();
            soulWardRecoveryRate = HELMET_SOUL_WARD_RECOVERY.getAmount();
        }
        else if (tool.hasTag(TinkerTags.Items.CHESTPLATES)) {
            soulWardCap = CHESTPLATE_SOUL_WARD_CAP.getAmount();
            soulWardRecoveryRate = CHESTPLATE_SOUL_WARD_RECOVERY.getAmount();
        }
        else if (tool.hasTag(TinkerTags.Items.LEGGINGS)) {
            soulWardCap = LEGGINGS_SOUL_WARD_CAP.getAmount();
            soulWardRecoveryRate = LEGGINGS_SOUL_WARD_RECOVERY.getAmount();
        }
        else if (tool.hasTag(TinkerTags.Items.BOOTS)) {
            soulWardCap = BOOTS_SOUL_WARD_CAP.getAmount();
            soulWardRecoveryRate = BOOTS_SOUL_WARD_RECOVERY.getAmount();
        }
        else if (tool.hasTag(TinkerTags.Items.MELEE) || tool.hasTag(TinkerTags.Items.HARVEST)) {
            if (tool.getItem().equals(TinkerTools.broadAxe.asItem())) {
                offhandMagicDamage = OFFHAND_AXE_MAGIC_DAMAGE.getAmount();
                primaryMagicDamage = AXE_MAGIC_DAMAGE.getAmount();
            }
            else if (tool.hasTag(TinkerTags.Items.MELEE_PRIMARY)) {
                offhandMagicDamage = OFFHAND_MELEE_PRIMARY_MAGIC_DAMAGE.getAmount();
                primaryMagicDamage = MELEE_PRIMARY_MAGIC_DAMAGE.getAmount();
            }
            else if (tool.hasTag(TinkerTags.Items.MELEE) || tool.hasTag(TinkerTags.Items.HARVEST)) {
                offhandMagicDamage = OFFHAND_HARVEST_MAGIC_DAMAGE.getAmount();
                primaryMagicDamage = HARVEST_MAGIC_DAMAGE.getAmount();
            }
        }

        if (soulWardCap != 0.0) {
            TooltipModifierHook.addFlatBoost(modifier.getModifier(), SOUL_WARD_CAPACITY, soulWardCap, tooltip);
        }
        if (soulWardRecoveryRate != 0.0) {
            TooltipModifierHook.addPercentBoost(modifier.getModifier(), SOUL_WARD_RECOVERY_RATE, soulWardRecoveryRate, tooltip);
        }
        if (primaryMagicDamage != 0.0) {
            TooltipModifierHook.addFlatBoost(modifier.getModifier(), PRIMARY_MAGIC_DAMAGE, primaryMagicDamage, tooltip);
        }
        if (offhandMagicDamage != 0.0) {
            TooltipModifierHook.addFlatBoost(modifier.getModifier(), OFFHAND_MAGIC_DAMAGE, offhandMagicDamage, tooltip);
        }
    }

    public void changeEquipment(ServerPlayer sp, EquipmentChangeContext context, boolean remove) {
        final AttributeInstance soulWardCap = sp.getAttribute(AttributeRegistry.SOUL_WARD_CAP.get());
        final AttributeInstance soulWardRecovery = sp.getAttribute(AttributeRegistry.SOUL_WARD_RECOVERY_RATE.get());
        final AttributeInstance magicDamage = sp.getAttribute(LodestoneAttributeRegistry.MAGIC_DAMAGE.get());
        ItemStack stack;
        AttributeModifier soulWardCapModifier = null;
        AttributeModifier soulWardRecoveryModifier = null;
        AttributeModifier magicDamageModifier = null;
        boolean isArmor = false;
        boolean isTool = false;

        if (remove) {
            stack = context.getOriginal();
        }
        else {
            stack = context.getReplacement();
        }

        switch(context.getChangedSlot()) {
            case FEET -> {
                isArmor = true;
                soulWardCapModifier = BOOTS_SOUL_WARD_CAP;
                soulWardRecoveryModifier = BOOTS_SOUL_WARD_RECOVERY;
            }
            case LEGS -> {
                isArmor = true;
                soulWardCapModifier = LEGGINGS_SOUL_WARD_CAP;
                soulWardRecoveryModifier = LEGGINGS_SOUL_WARD_RECOVERY;
            }
            case CHEST -> {
                isArmor = true;
                soulWardCapModifier = CHESTPLATE_SOUL_WARD_CAP;
                soulWardRecoveryModifier = CHESTPLATE_SOUL_WARD_RECOVERY;
            }
            case HEAD -> {
                isArmor = true;
                soulWardCapModifier = HELMET_SOUL_WARD_CAP;
                soulWardRecoveryModifier = HELMET_SOUL_WARD_RECOVERY;
            }
            case OFFHAND, MAINHAND -> {
                if (stack.is(TinkerTags.Items.MELEE) || stack.is(TinkerTags.Items.HARVEST)) {
                    isTool = true;
                }
            }
        }

        if (isArmor) {
            if (soulWardCap != null) {
                if (remove && soulWardCap.hasModifier(soulWardCapModifier)) {
                    soulWardCap.removeModifier(soulWardCapModifier);
                }
                else if (!soulWardCap.hasModifier(soulWardCapModifier)){
                    soulWardCap.addPermanentModifier(soulWardCapModifier);
                }
            }
            if (soulWardRecovery != null) {
                if (remove && soulWardRecovery.hasModifier(soulWardRecoveryModifier)) {
                    soulWardRecovery.removeModifier(soulWardRecoveryModifier);
                }
                else if (!soulWardRecovery.hasModifier(soulWardRecoveryModifier)){
                    soulWardRecovery.addPermanentModifier(soulWardRecoveryModifier);
                }
            }
        }
        else if (isTool) {
            if (stack.is(TinkerTools.broadAxe.asItem())) {
                if (context.getChangedSlot() == EquipmentSlot.OFFHAND) {
                    magicDamageModifier = OFFHAND_AXE_MAGIC_DAMAGE;
                }
                else {
                    magicDamageModifier = AXE_MAGIC_DAMAGE;
                }
            }
            else if (stack.is(TinkerTags.Items.MELEE_PRIMARY)) {
                if (context.getChangedSlot() == EquipmentSlot.OFFHAND) {
                    magicDamageModifier = OFFHAND_MELEE_PRIMARY_MAGIC_DAMAGE;
                }
                else {
                    magicDamageModifier = MELEE_PRIMARY_MAGIC_DAMAGE;
                }
            }
            else if (stack.is(TinkerTags.Items.MELEE) || stack.is(TinkerTags.Items.HARVEST)) {
                if (context.getChangedSlot() == EquipmentSlot.OFFHAND) {
                    magicDamageModifier = OFFHAND_HARVEST_MAGIC_DAMAGE;
                }
                else {
                    magicDamageModifier = HARVEST_MAGIC_DAMAGE;
                }
            }

            if (magicDamage != null && magicDamageModifier != null) {
                if (remove && magicDamage.hasModifier(magicDamageModifier)) {
                    magicDamage.removeModifier(magicDamageModifier);
                }
                else if (!magicDamage.hasModifier(magicDamageModifier)) {
                    magicDamage.addPermanentModifier(magicDamageModifier);
                }
            }
        }
    }

}
