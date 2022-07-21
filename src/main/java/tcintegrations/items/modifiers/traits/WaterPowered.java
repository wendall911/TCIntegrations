package tcintegrations.items.modifiers.traits;

import javax.annotation.Nullable;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import tcintegrations.TCIntegrations;

public class WaterPowered extends NoLevelsModifier {

    @Override
    public int getPriority() {
        return 180;
    }

    @Override
    public int onDamageTool(IToolStackView tool, int level, int amount, @Nullable LivingEntity holder) {
        final Player player = holder instanceof Player ? (Player) holder : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;
            boolean isPartialSubmersion = !sp.isUnderWater() && sp.isInWater() && sp.isInWaterRainOrBubble();
            boolean isSubmerged = sp.isUnderWater() && sp.isInWater() && sp.isInWaterRainOrBubble();
            float chance = 0.0F;

            if (isSubmerged) {
                chance = 0.75F;
            }
            else if (isPartialSubmersion) {
                chance = 0.50F;
            }
            else if (sp.isInWaterRainOrBubble()) {
                chance = 0.30F;
            }

            if (chance > 0.0F) {
                int maxDamage = amount;

                for (int i = 0; i < maxDamage; i++) {
                    if (TCIntegrations.RANDOM.nextFloat() <= chance) {
                        amount--;
                    }
                }
            }
        }

        return amount;
    }

}
