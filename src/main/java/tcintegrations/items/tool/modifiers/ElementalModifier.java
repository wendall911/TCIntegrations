package tcintegrations.items.tool.modifiers;

import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;

import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import vazkii.botania.common.entity.EntityPixie;

import tcintegrations.TCIntegrations;

public class ElementalModifier extends ManaItemModifier {

    private static final int MANA_PER_DAMAGE = 70;

    @Override
    public int getManaPerDamage() {
        return MANA_PER_DAMAGE;
    }

    @Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        Player player = context.getPlayerAttacker();
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (TCIntegrations.RANDOM.nextFloat() <= 0.05F) {
            EntityPixie pixie = new EntityPixie(player.level);

            pixie.setPos(player.getX(), player.getY() + 2, player.getZ());

            float dmg = 4;

            if (!stack.isEmpty()) {
                dmg += 2;
            }

            pixie.setProps(context.getLivingTarget(), player, 0, dmg);
            pixie.finalizeSpawn(
                    (ServerLevelAccessor) player.level,
                    player.level.getCurrentDifficultyAt(pixie.blockPosition()),
                    MobSpawnType.EVENT, null, null);
            player.level.addFreshEntity(pixie);
        }

        return 0;
    }

}
