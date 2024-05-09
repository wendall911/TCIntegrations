package tcintegrations.items.modifiers.tool;

import org.jetbrains.annotations.Nullable;

import com.github.alexthe666.iceandfire.entity.EntityGhostSword;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;

import com.mojang.math.Vector3f;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.Vec3;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.TinkerHooks;
import slimeknights.tconstruct.library.modifiers.hook.ProjectileLaunchModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierHookMap.Builder;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.library.utils.Util;

public class PhantasmalModifier extends NoLevelsModifier implements ProjectileLaunchModifierHook {

    @Override
    protected void registerHooks(Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, TinkerHooks.PROJECTILE_LAUNCH);
    }

    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter, Projectile projectile, @Nullable AbstractArrow arrow, NamespacedNBT persistentData, boolean primary) {
        if (shooter instanceof Player player) {
            float damage = ToolAttackUtil.getAttributeAttackDamage(tool, player, Util.getSlotType(player.getUsedItemHand()));
            EntityGhostSword ghostSword = new EntityGhostSword(IafEntityRegistry.GHOST_SWORD.get(), player.level, player, damage * 0.5F);
            Vec3 vector3d = player.getViewVector(1.0F);
            Vector3f vector3f = new Vector3f(vector3d);

            ghostSword.shoot(vector3f.x(), vector3f.y(), vector3f.z(), 1.0F, 0.5F);
            player.level.addFreshEntity(ghostSword);
            ToolDamageUtil.directDamage(tool, 1, null, tool.getItem().getDefaultInstance());
            player.getCooldowns().addCooldown(tool.getItem(), 10);
        }
    }

}
