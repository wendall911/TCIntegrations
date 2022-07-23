package tcintegrations.items.modifiers.armor;

import com.hollingsworth.arsnouveau.common.potions.ModPotions;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import tcintegrations.items.modifiers.ArsNouveauBaseModifier;

public class EnchantersShieldModifier extends ArsNouveauBaseModifier {

    @Override
    public void onAttacked(IToolStackView tool, int level, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (context.getEntity() instanceof Player player
                && !player.level.isClientSide
                && player.isBlocking()
                && source.getEntity() instanceof LivingEntity attacker
                && isDirectDamage) {
            final ServerPlayer sp = (ServerPlayer) player;

            sp.addEffect(new MobEffectInstance(ModPotions.MANA_REGEN_EFFECT, 200, 1));
            sp.addEffect(new MobEffectInstance(ModPotions.SPELL_DAMAGE_EFFECT, 200, 1));
        }
    }

}
