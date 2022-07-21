package tcintegrations.items.modifiers.tool;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import vazkii.botania.api.mana.ManaItemHandler;

import tcintegrations.util.BotaniaHelper;

public class ManaItemModifier extends NoLevelsModifier {

    private static final int MANA_PER_DAMAGE = 60;

    public int getManaPerDamage(ServerPlayer sp) {
        return BotaniaHelper.getManaPerDamageBonus(sp, MANA_PER_DAMAGE);
    }

    @Override
    public void onInventoryTick(IToolStackView tool, int level, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (!world.isClientSide
                && holder.tickCount % 20 == 0
                && holder instanceof ServerPlayer sp
                && tool.getDamage() > 0
                && ManaItemHandler.instance().requestManaExactForTool(stack, sp, getManaPerDamage(sp) * 2, true)) {
            tool.setDamage(tool.getDamage() - 1);
        }
    }

}
