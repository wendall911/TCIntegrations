package tcintegrations.items.modifiers.armor;

import com.github.alexthe666.alexsmobs.effect.AMEffectRegistry;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.OnAttackedModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class ShieldOfTheDeepModifier extends NoLevelsModifier implements OnAttackedModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.ON_ATTACKED);
    }

    @Override
    public void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (context.getEntity() instanceof Player player
                && !player.level.isClientSide
                && source.getEntity() instanceof LivingEntity attacker
                && isDirectDamage) {
            final ServerPlayer sp = (ServerPlayer) player;

            if (sp.distanceTo(attacker) <= 4
                    && !attacker.hasEffect(AMEffectRegistry.EXSANGUINATION.get())) {
                attacker.addEffect(new MobEffectInstance(AMEffectRegistry.EXSANGUINATION.get(), 60, 2));
            }
            if (sp.isInWaterOrBubble()) {
                sp.setAirSupply(Math.min(sp.getMaxAirSupply(), sp.getMaxAirSupply() + 150));
            }
        }
    }

}
