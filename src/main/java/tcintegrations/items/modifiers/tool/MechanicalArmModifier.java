package tcintegrations.items.modifiers.tool;

import slimeknights.tconstruct.library.module.ModuleHookMap.Builder;
import slimeknights.tconstruct.tools.modifiers.ability.tool.OffhandAttackModifier;

public class MechanicalArmModifier extends OffhandAttackModifier {

    @Override
    protected void registerHooks(Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this);
    }

    @Override
    public boolean shouldDisplay(boolean advanced) {
        return true;
    }

}
