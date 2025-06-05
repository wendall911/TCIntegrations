package tcintegrations.items.modifiers.armor;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;

import slimeknights.tconstruct.tools.modules.armor.ThornsModule;

public class MasticateModifier extends Modifier {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addModule(ThornsModule.builder().constantFlat(2).randomFlat(3).build());
    }

}
