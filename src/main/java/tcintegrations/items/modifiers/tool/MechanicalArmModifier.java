package tcintegrations.items.modifiers.tool;

import slimeknights.tconstruct.library.modifiers.util.ModifierHookMap;
import slimeknights.tconstruct.tools.modifiers.ability.tool.OffhandAttackModifier;

public class MechanicalArmModifier extends OffhandAttackModifier {

    @Override
    protected void registerHooks(ModifierHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this);
    }

    @Override
    public boolean shouldDisplay(boolean advanced) {
        return true;
    }

}
