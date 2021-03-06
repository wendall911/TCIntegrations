package tcintegrations.items.modifiers.armor;

import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class ShieldOfTheDeepModifier extends NoLevelsModifier {

    @Override
    public void onAttacked(IToolStackView tool, int level, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (context.getEntity() instanceof Player player
                && !player.level.isClientSide
                && source.getEntity() instanceof LivingEntity attacker
                && isDirectDamage) {
            final ServerPlayer sp = (ServerPlayer) player;

            if (sp.distanceTo(attacker) <= 4
                    && !attacker.hasEffect(AMEffectRegistry.EXSANGUINATION)) {
                attacker.addEffect(new MobEffectInstance(AMEffectRegistry.EXSANGUINATION, 60, 2));
            }
            if (sp.isInWaterOrBubble()) {
                sp.setAirSupply(Math.min(sp.getMaxAirSupply(), sp.getMaxAirSupply() + 150));
            }
        }
    }

}
