package tcintegrations.items.modifiers.tool;

import net.minecraft.world.entity.LivingEntity;

import quek.undergarden.registry.UGTags;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap.Builder;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class UtheriumModifier extends NoLevelsModifier implements MeleeDamageModifierHook {

    @Override
    protected void registerHooks(Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE);
    }

    private static float damageMultiplier(LivingEntity target) {
        if (target.getType().is(UGTags.Entities.ROTSPAWN)) {
            return 1.5F;
        }
        return 1;
    }

    @Override
    public float getMeleeDamage(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        LivingEntity target = context.getLivingTarget();

        if (target != null) {
            return damage * damageMultiplier(target);
        }

        return damage;
    }

}
