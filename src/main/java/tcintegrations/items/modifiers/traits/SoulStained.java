package tcintegrations.items.modifiers.traits;

import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import com.sammy.malum.registry.common.AttributeRegistry;

import net.minecraft.Util;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
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

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.TinkerHooks;
import slimeknights.tconstruct.library.modifiers.hook.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierHookMap.Builder;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.tools.TinkerTools;

import team.lodestar.lodestone.setup.LodestoneAttributeRegistry;

import tcintegrations.TCIntegrations;

public class SoulStained extends NoLevelsModifier implements ProjectileHitModifierHook {

    private static final AttributeModifier HELMET_MAGIC_RESISTANCE = new AttributeModifier(
            ArmorItem.ARMOR_MODIFIER_UUID_PER_SLOT[EquipmentSlot.HEAD.getIndex()],
            "Helmet Magic Resistance",
            1.0F,
            AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier CHESTPLATE_MAGIC_RESISTANCE = new AttributeModifier(
            ArmorItem.ARMOR_MODIFIER_UUID_PER_SLOT[EquipmentSlot.CHEST.getIndex()],
            "Chestplate Magic Resistance",
            1.0F,
            AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier LEGGINGS_MAGIC_RESISTANCE = new AttributeModifier(
            ArmorItem.ARMOR_MODIFIER_UUID_PER_SLOT[EquipmentSlot.LEGS.getIndex()],
            "Leggings Magic Resistance",
            1.0F,
            AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier BOOTS_MAGIC_RESISTANCE = new AttributeModifier(
            ArmorItem.ARMOR_MODIFIER_UUID_PER_SLOT[EquipmentSlot.FEET.getIndex()],
            "Boots Magic Resistance",
            1.0F,
            AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier HELMET_SOUL_WARD_CAP = new AttributeModifier(
            ArmorItem.ARMOR_MODIFIER_UUID_PER_SLOT[EquipmentSlot.HEAD.getIndex()],
            "Helmet Soul Ward Cap",
            3.0F,
            AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier CHESTPLATE_SOUL_WARD_CAP = new AttributeModifier(
            ArmorItem.ARMOR_MODIFIER_UUID_PER_SLOT[EquipmentSlot.CHEST.getIndex()],
            "Chestplate Soul Ward Cap",
            3.0F,
            AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier LEGGINGS_SOUL_WARD_CAP = new AttributeModifier(
            ArmorItem.ARMOR_MODIFIER_UUID_PER_SLOT[EquipmentSlot.LEGS.getIndex()],
            "Leggings Soul Ward Cap",
            3.0F,
            AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier BOOTS_SOUL_WARD_CAP = new AttributeModifier(
            ArmorItem.ARMOR_MODIFIER_UUID_PER_SLOT[EquipmentSlot.FEET.getIndex()],
            "Boots Soul Ward Cap",
            3.0F,
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
    private static final Component MAGIC_RESISTANCE = new TranslatableComponent(
            Util.makeDescriptionId("modifier", new ResourceLocation(TCIntegrations.MODID, "soul_stained.magic_resistance")));
    private static final Component SOUL_WARD_CAPACITY = new TranslatableComponent(
            Util.makeDescriptionId("modifier", new ResourceLocation(TCIntegrations.MODID, "soul_stained.soul_ward_capacity")));
    private static final Component PRIMARY_MAGIC_DAMAGE = new TranslatableComponent(
            Util.makeDescriptionId("modifier", new ResourceLocation(TCIntegrations.MODID, "soul_stained.primary_magic_damage")));
    private static final Component OFFHAND_MAGIC_DAMAGE = new TranslatableComponent(
            Util.makeDescriptionId("modifier", new ResourceLocation(TCIntegrations.MODID, "soul_stained.offhand_magic_damage")));

    @Override
    protected void registerHooks(Builder builder) {
        builder.addHook(this, TinkerHooks.PROJECTILE_HIT);
    }

    @Override
    public void onEquip(IToolStackView tool, int level, EquipmentChangeContext context) {
        final Player player = context.getEntity() instanceof Player ? (Player) context.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;

            changeEquipment(sp, context, false);
        }
    }

    @Override
    public void onUnequip(IToolStackView tool, int level, EquipmentChangeContext context) {
        final Player player = context.getEntity() instanceof Player ? (Player) context.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;

            changeEquipment(sp, context, true);
        }
    }

    @Override
    public float beforeEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        if (context.getLivingTarget() != null) {
            MalumLivingEntityDataCapability.getCapability(context.getLivingTarget()).soulData.exposedSoulDuration = 200;
        }

        return super.beforeEntityHit(tool, level, context, damage, baseKnockback, knockback);
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (target != null) {
            MalumLivingEntityDataCapability.getCapability(target).soulData.exposedSoulDuration = 200;
        }

        return false;
    }

    @Override
    public void addInformation(IToolStackView tool, int level, @Nullable Player player, List<Component> tooltip, slimeknights.tconstruct.library.utils.TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        double magicResistance = 0.0;
        double soulWardCap = 0.0;
        double primaryMagicDamage = 0.0;
        double offhandMagicDamage = 0.0;

        if (tool.hasTag(TinkerTags.Items.HELMETS)) {
            magicResistance = HELMET_MAGIC_RESISTANCE.getAmount();
            soulWardCap = HELMET_SOUL_WARD_CAP.getAmount();
        }
        else if (tool.hasTag(TinkerTags.Items.CHESTPLATES)) {
            magicResistance = CHESTPLATE_MAGIC_RESISTANCE.getAmount();
            soulWardCap = CHESTPLATE_SOUL_WARD_CAP.getAmount();
        }
        else if (tool.hasTag(TinkerTags.Items.LEGGINGS)) {
            magicResistance = LEGGINGS_MAGIC_RESISTANCE.getAmount();
            soulWardCap = LEGGINGS_SOUL_WARD_CAP.getAmount();
        }
        else if (tool.hasTag(TinkerTags.Items.BOOTS)) {
            magicResistance = BOOTS_MAGIC_RESISTANCE.getAmount();
            soulWardCap = BOOTS_SOUL_WARD_CAP.getAmount();
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

        if (magicResistance != 0.0) {
            addFlatBoost(MAGIC_RESISTANCE, magicResistance, tooltip);
        }
        if (soulWardCap != 0.0) {
            addFlatBoost(SOUL_WARD_CAPACITY, soulWardCap, tooltip);
        }
        if (primaryMagicDamage != 0.0) {
            addFlatBoost(PRIMARY_MAGIC_DAMAGE, primaryMagicDamage, tooltip);
        }
        if (offhandMagicDamage != 0.0) {
            addFlatBoost(OFFHAND_MAGIC_DAMAGE, offhandMagicDamage, tooltip);
        }
    }

    public void changeEquipment(ServerPlayer sp, EquipmentChangeContext context, boolean remove) {
        final AttributeInstance magicResistance = sp.getAttribute(LodestoneAttributeRegistry.MAGIC_RESISTANCE.get());
        final AttributeInstance soulWardCap = sp.getAttribute(AttributeRegistry.SOUL_WARD_CAP.get());
        final AttributeInstance magicDamage = sp.getAttribute(LodestoneAttributeRegistry.MAGIC_DAMAGE.get());
        ItemStack stack;
        AttributeModifier magicResistanceModifier = null;
        AttributeModifier soulWardCapModifier = null;
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
                magicResistanceModifier = BOOTS_MAGIC_RESISTANCE;
                soulWardCapModifier = BOOTS_SOUL_WARD_CAP;
            }
            case LEGS -> {
                isArmor = true;
                magicResistanceModifier = LEGGINGS_MAGIC_RESISTANCE;
                soulWardCapModifier = LEGGINGS_SOUL_WARD_CAP;
            }
            case CHEST -> {
                isArmor = true;
                magicResistanceModifier = CHESTPLATE_MAGIC_RESISTANCE;
                soulWardCapModifier = CHESTPLATE_SOUL_WARD_CAP;
            }
            case HEAD -> {
                isArmor = true;
                magicResistanceModifier = HELMET_MAGIC_RESISTANCE;
                soulWardCapModifier = HELMET_SOUL_WARD_CAP;
            }
            case OFFHAND, MAINHAND -> {
                if (stack.is(TinkerTags.Items.MELEE) || stack.is(TinkerTags.Items.HARVEST)) {
                    isTool = true;
                }
            }
        }

        if (isArmor) {
            if (magicResistance != null) {
                if (remove && magicResistance.hasModifier(magicResistanceModifier)) {
                    magicResistance.removeModifier(magicResistanceModifier);
                }
                else if (!magicResistance.hasModifier(magicResistanceModifier)) {
                    magicResistance.addPermanentModifier(magicResistanceModifier);
                }
            }
            if (soulWardCap != null) {
                if (remove && soulWardCap.hasModifier(soulWardCapModifier)) {
                    soulWardCap.removeModifier(soulWardCapModifier);
                }
                else if (!soulWardCap.hasModifier(soulWardCapModifier)){
                    soulWardCap.addPermanentModifier(soulWardCapModifier);
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
