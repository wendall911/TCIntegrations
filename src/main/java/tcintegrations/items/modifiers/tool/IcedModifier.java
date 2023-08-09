package tcintegrations.items.modifiers.tool;

import org.jetbrains.annotations.Nullable;

import com.github.alexthe666.iceandfire.entity.EntityFireDragon;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.phys.Vec3;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.TinkerHooks;
import slimeknights.tconstruct.library.modifiers.hook.ProjectileHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.modifiers.util.ModifierHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class IcedModifier extends NoLevelsModifier implements ProjectileHitModifierHook {

    @Override
    protected void registerHooks(ModifierHookMap.Builder builder) {
        builder.addHook(this, TinkerHooks.PROJECTILE_HIT);
    }

    @Override
    public float beforeEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        return knockback + 1F;
    }

    @Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        doSecondaryDamage(context.getTarget(), context.getLivingTarget());

        return 0;
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        // Apply knockback
        if (hit.getEntity() instanceof LivingEntity living) {
            if (projectile instanceof AbstractArrow arrow) {
                Vec3 knockbackVec = arrow.getDeltaMovement().multiply(1.0D, 0.0D, 1.0D).normalize().scale(1F * 0.6D);

                if (knockbackVec.lengthSqr() > 0.0D) {
                    living.push(knockbackVec.x(), 0.1D, knockbackVec.z());
                }
            }
            doSecondaryDamage(hit.getEntity(), living);
        }

        return false;
    }

    private void doSecondaryDamage(Entity entity, LivingEntity target) {
        if (target instanceof EntityFireDragon) {
            ToolAttackUtil.attackEntitySecondary(DamageSource.DROWN, 13.5F, entity, target, false);
        }
        if (target != null) {
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100, 2));
            target.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 100, 2));
        }
    }

}
