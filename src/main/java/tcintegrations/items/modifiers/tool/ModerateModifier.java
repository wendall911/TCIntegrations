package tcintegrations.items.modifiers.tool;

import javax.annotation.Nullable;

import java.util.List;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;

import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;

import slimeknights.mantle.client.TooltipKey;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import tcintegrations.TCIntegrations;

public class ModerateModifier extends NoLevelsModifier {

    private static final float BASELINE_TEMPERATURE = 1.0F;
    private static final float MAX_BOOST = 7.5F;
    private static final Component MINING_SPEED = new TranslatableComponent(
            Util.makeDescriptionId("modifier", new ResourceLocation(TCIntegrations.MODID, "moderate.mining_speed")));

    /** Gets the bonus for the given position */
    private static float getBonus(Player player, BlockPos pos) {
        float biomeTemp = player.level.getBiome(pos).value().getHeightAdjustedTemperature(pos);

        return MAX_BOOST - (Math.abs(BASELINE_TEMPERATURE - biomeTemp) * 4.4F);
    }

    @Override
    public void onBreakSpeed(IToolStackView tool, int level, BreakSpeed event, Direction sideHit, boolean isEffective, float miningSpeedModifier) {
        if (isEffective) {
            event.setNewSpeed(event.getNewSpeed() + (getBonus(event.getPlayer(), event.getPos()) * tool.getMultiplier(ToolStats.MINING_SPEED) * miningSpeedModifier));
        }
    }

    @Override
    public void addInformation(IToolStackView tool, int level, @Nullable Player player, List<Component> tooltip, TooltipKey key, TooltipFlag flag) {
        if (tool.hasTag(TinkerTags.Items.HARVEST)) {
            float bonus;

            if (player != null && key == TooltipKey.SHIFT) {
                bonus = getBonus(player, player.blockPosition());
            } else {
                bonus = MAX_BOOST;
            }

            if (bonus > 0.01F) {
                addFlatBoost(MINING_SPEED, bonus * tool.getMultiplier(ToolStats.MINING_SPEED), tooltip);
            }
        }
    }

}
