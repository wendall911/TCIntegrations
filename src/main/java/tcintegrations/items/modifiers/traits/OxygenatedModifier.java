package tcintegrations.items.modifiers.traits;

import net.minecraft.server.level.ServerPlayer;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockBreakModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap.Builder;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

/*
 * This should be mod independent. Just increases AirSupply by 20 if mining with an Oxygenated tool.
 */

public class OxygenatedModifier extends Modifier implements BlockBreakModifierHook {

    @Override
    protected void registerHooks(Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.BLOCK_BREAK);
    }

    @Override
    public void afterBlockBreak(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context) {
        final ServerPlayer sp = context.getPlayer();

        if (sp != null && !sp.level.isClientSide) {
            sp.setAirSupply(Math.min(sp.getMaxAirSupply(), sp.getAirSupply() + 20));
        }
    }

}
