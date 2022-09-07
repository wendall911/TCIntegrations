package tcintegrations.items.modifiers.tool;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class GlowUpModifier extends Modifier {

    @Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        final ServerPlayer sp = (ServerPlayer) context.getPlayerAttacker();
        final LivingEntity entity = context.getLivingTarget();

        if (sp != null && entity != null) {
            MobEffectInstance effect = new MobEffectInstance(MobEffects.GLOWING, (30 * 20) * level);

            entity.addEffect(effect);
        }

        return 0;
    }

}
