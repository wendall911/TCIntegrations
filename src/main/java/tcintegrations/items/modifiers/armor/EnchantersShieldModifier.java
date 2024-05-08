package tcintegrations.items.modifiers.armor;

import com.hollingsworth.arsnouveau.common.potions.ModPotions;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.TinkerHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.util.ModifierHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import tcintegrations.items.modifiers.ArsNouveauBaseModifier;

public class EnchantersShieldModifier extends ArsNouveauBaseModifier implements OnAttackedModifierHook {

    @Override
    protected void registerHooks(ModifierHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, TinkerHooks.ON_ATTACKED);
    }

    @Override
    public void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (context.getEntity() instanceof Player player
                && !player.level.isClientSide
                && player.isBlocking()
                && source.getEntity() instanceof LivingEntity
                && isDirectDamage) {
            final ServerPlayer sp = (ServerPlayer) player;

            sp.addEffect(new MobEffectInstance(ModPotions.MANA_REGEN_EFFECT.get(), 200, 1));
            sp.addEffect(new MobEffectInstance(ModPotions.SPELL_DAMAGE_EFFECT.get(), 200, 1));
        }
    }

}
