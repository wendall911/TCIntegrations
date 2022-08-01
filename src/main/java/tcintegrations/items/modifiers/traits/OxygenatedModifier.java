package tcintegrations.items.modifiers.traits;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import tcintegrations.data.integration.ModIntegration;

public class OxygenatedModifier extends Modifier {

    @Override
    public void afterBlockBreak(IToolStackView tool, int level, ToolHarvestContext context) {
        final ServerPlayer sp = context.getPlayer();

        if (sp != null && !sp.level.isClientSide) {
            sp.addEffect(new MobEffectInstance(ModIntegration.OXYGEN_EFFECT.get(), 20 * level, 0, false, false));
        }
    }

}
