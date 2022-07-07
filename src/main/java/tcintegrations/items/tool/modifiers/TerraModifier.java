package tcintegrations.items.tool.modifiers;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.handler.ModSounds;

public class TerraModifier extends ManaItemModifier {

    private static final int MANA_PER_DAMAGE = 100;

    @Override
    public int getManaPerDamage() {
        return MANA_PER_DAMAGE;
    }

    @Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        Player player = context.getPlayerAttacker();
        DamageSource source = DamageSource.playerAttack(player);
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        source.bypassArmor();

        if (player.getAttackStrengthScale(0F) == 1 && ManaItemHandler.instance().requestManaExactForTool(stack, player, getManaPerDamage() * 2, true)) {
            player.level.playSound(null, player.getX(), player.getY(), player.getZ(), ModSounds.terraBlade, SoundSource.PLAYERS, 1F, 1F);
            ToolAttackUtil.attackEntitySecondary(source, 7.0F, context.getTarget(), context.getLivingTarget(), true);
        }

        return 0;
    }

}
