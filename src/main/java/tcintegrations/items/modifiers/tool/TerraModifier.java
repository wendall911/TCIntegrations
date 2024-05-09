package tcintegrations.items.modifiers.tool;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap.Builder;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.handler.BotaniaSounds;

import tcintegrations.items.modifiers.traits.ManaModifier;
import tcintegrations.util.BotaniaHelper;

public class TerraModifier extends ManaModifier implements MeleeHitModifierHook {

    private static final int MANA_PER_DAMAGE = 100;

    @Override
    protected void registerHooks(Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT);
    }

    @Override
    public int getManaPerDamage(ServerPlayer sp) {
        return BotaniaHelper.getManaPerDamageBonus(sp, MANA_PER_DAMAGE);
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        final Player player = context.getPlayerAttacker() != null ? context.getPlayerAttacker() : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;
            DamageSource source = DamageSource.indirectMagic(sp, null);
            ItemStack stack = sp.getItemInHand(InteractionHand.MAIN_HAND);

            if (sp.getAttackStrengthScale(0F) == 1 && ManaItemHandler.instance().requestManaExactForTool(stack, sp, getManaPerDamage(sp) * 2, true)) {
                sp.level.playSound(null, sp.getX(), sp.getY(), sp.getZ(), BotaniaSounds.terraBlade, SoundSource.PLAYERS, 1F, 1F);
                ToolAttackUtil.attackEntitySecondary(source, 7.0F, context.getTarget(), context.getLivingTarget(), true);
            }
        }
    }

}
