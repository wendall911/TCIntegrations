package tcintegrations.items.modifiers.traits;

import net.minecraft.world.entity.LivingEntity;

import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class HellishModifier extends NoLevelsModifier {

    @Override
    public float beforeEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        LivingEntity target = context.getLivingTarget();

        if (target != null && !target.isOnFire()) {
            target.setRemainingFireTicks(1);
        }

        return knockback;
    }

    @Override
    public void failedEntityHit(IToolStackView tool, int level, ToolAttackContext context) {
        LivingEntity target = context.getLivingTarget();

        if (target != null && target.isOnFire()) {
            target.clearFire();
        }
    }

    @Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        LivingEntity target = context.getLivingTarget();

        if (target != null) {
            target.setSecondsOnFire(20);
        }

        return 0;
    }

}
