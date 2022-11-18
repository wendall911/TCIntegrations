package tcintegrations.items.modifiers.tool;

import java.util.List;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import tcintegrations.items.modifiers.traits.ManaModifier;
import tcintegrations.util.BotaniaHelper;

public class AlfModifier extends ManaModifier {

    private static final int MANA_PER_DAMAGE = 100;
    private static final int ITEM_COLLECT_RANGE = 8;

    @Override
    public int getManaPerDamage(ServerPlayer sp) {
        return BotaniaHelper.getManaPerDamageBonus(sp, MANA_PER_DAMAGE);
    }

    @Override
    public InteractionResult onToolUse(IToolStackView tool, int level, Level world, Player player, InteractionHand hand, EquipmentSlot slot) {
        if (tool.hasTag(TinkerTags.Items.MELEE_PRIMARY)) {
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            List<ItemEntity> items = world.getEntitiesOfClass(ItemEntity.class,
                    new AABB(x - ITEM_COLLECT_RANGE, y - ITEM_COLLECT_RANGE, z - ITEM_COLLECT_RANGE,
                            x + ITEM_COLLECT_RANGE, y + ITEM_COLLECT_RANGE, z + ITEM_COLLECT_RANGE));

            for (ItemEntity item : items) {
                item.moveTo(x + world.random.nextFloat() - 0.5F, y  + world.random.nextFloat(),
                        z + world.random.nextFloat() - 0.5F, item.getYRot(), item.getXRot());
            }
        }

        return InteractionResult.PASS;
    }

}