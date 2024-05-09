package tcintegrations.items.modifiers.traits;

import org.jetbrains.annotations.Nullable;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.ToolDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap.Builder;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import tcintegrations.TCIntegrations;

public class WaterPowered extends NoLevelsModifier implements ToolDamageModifierHook {

    @Override
    protected void registerHooks(Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.TOOL_DAMAGE);
    }

    @Override
    public int getPriority() {
        return 180;
    }

    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifier, int amount, @Nullable LivingEntity holder) {
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
