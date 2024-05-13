package tcintegrations.items.modifiers.tool;

import java.util.List;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.GeneralInteractionModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InteractionSource;
import slimeknights.tconstruct.library.module.ModuleHookMap.Builder;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import tcintegrations.items.modifiers.traits.ManaModifier;
import tcintegrations.util.BotaniaHelper;

public class AlfModifier extends ManaModifier implements GeneralInteractionModifierHook {

    private static final int MANA_PER_DAMAGE = 100;
    private static final int ITEM_COLLECT_RANGE = 8;

    @Override
    protected void registerHooks(Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.GENERAL_INTERACT);
    }

    @Override
    public int getManaPerDamage(ServerPlayer sp) {
        return BotaniaHelper.getManaPerDamageBonus(sp, MANA_PER_DAMAGE);
    }

    @Override
    public InteractionResult onToolUse(IToolStackView tool, ModifierEntry modifier, Player player, InteractionHand hand, InteractionSource source) {
        if (tool.hasTag(TinkerTags.Items.MELEE_PRIMARY)) {
            Level level = player.getLevel();
            double x = player.getX();
            double y = player.getY();
            double z = player.getZ();
            List<ItemEntity> items = level.getEntitiesOfClass(ItemEntity.class,
                    new AABB(x - ITEM_COLLECT_RANGE, y - ITEM_COLLECT_RANGE, z - ITEM_COLLECT_RANGE,
                            x + ITEM_COLLECT_RANGE, y + ITEM_COLLECT_RANGE, z + ITEM_COLLECT_RANGE));

            for (ItemEntity item : items) {
                item.moveTo(x + level.random.nextFloat() - 0.5F, y  + level.random.nextFloat(),
                        z + level.random.nextFloat() - 0.5F, item.getYRot(), item.getXRot());
            }
        }

        return InteractionResult.PASS;
    }

}
