package tcintegrations.items.modifiers.tool;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import tcintegrations.items.modifiers.traits.ManaModifier;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.handler.ModSounds;

import tcintegrations.util.BotaniaHelper;

public class TerraModifier extends ManaModifier {

    private static final int MANA_PER_DAMAGE = 100;

    @Override
    public int getManaPerDamage(ServerPlayer sp) {
        return BotaniaHelper.getManaPerDamageBonus(sp, MANA_PER_DAMAGE);
    }

    @Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        final Player player = context.getPlayerAttacker() != null ? (Player) context.getPlayerAttacker() : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;
            DamageSource source = DamageSource.playerAttack(sp);
            ItemStack stack = sp.getItemInHand(InteractionHand.MAIN_HAND);

            source.bypassArmor();

            if (sp.getAttackStrengthScale(0F) == 1 && ManaItemHandler.instance().requestManaExactForTool(stack, sp, getManaPerDamage(sp) * 2, true)) {
                sp.level.playSound(null, sp.getX(), sp.getY(), sp.getZ(), ModSounds.terraBlade, SoundSource.PLAYERS, 1F, 1F);
                ToolAttackUtil.attackEntitySecondary(source, 7.0F, context.getTarget(), context.getLivingTarget(), true);
            }
        }

        return 0;
    }

}
