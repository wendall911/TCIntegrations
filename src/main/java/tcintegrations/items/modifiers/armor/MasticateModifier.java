package tcintegrations.items.modifiers.armor;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.tools.modifiers.upgrades.armor.ThornsModifier;

import tcintegrations.TCIntegrations;

public class MasticateModifier extends ThornsModifier {

    @Override
    public void onAttacked(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        Entity attacker = source.getEntity();

        if (attacker != null && isDirectDamage) {
            float scaledLevel = modifier.getEffectiveLevel();

            if (TCIntegrations.RANDOM.nextFloat() < (scaledLevel * 0.45F)) {
                float damage = scaledLevel > 10 ? scaledLevel - 10 : 1 + TCIntegrations.RANDOM.nextInt(4);
                LivingEntity user = context.getEntity();

                attacker.hurt(DamageSource.thorns(user), damage);
                ToolDamageUtil.damageAnimated(tool, 1, user, slotType);
            }
        }
    }

}
