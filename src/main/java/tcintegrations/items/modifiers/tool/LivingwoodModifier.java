package tcintegrations.items.modifiers.tool;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class LivingwoodModifier extends Modifier {

    @Override
    public float getRepairFactor(IToolStackView toolStack, int level, float factor) {
        // +75% repair per level
        return (factor * (1 + (level * 0.75F)));
    }

}
