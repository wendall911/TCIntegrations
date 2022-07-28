package tcintegrations.items.modifiers.tool;

import net.minecraft.world.entity.LivingEntity;

import quek.undergarden.registry.UGTags;

import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class UtheriumModifier extends NoLevelsModifier {

    private static float damageMultiplier(LivingEntity target) {
        if (target.getType().is(UGTags.Entities.ROTSPAWN)) {
            return 1.5F;
        }
        return 1;
    }

    @Override
    public float getEntityDamage(IToolStackView tool, int level, ToolAttackContext context, float baseDamage, float damage) {
        return damage * damageMultiplier(context.getLivingTarget());
    }

}
