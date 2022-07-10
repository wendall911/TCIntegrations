package tcintegrations.util;

import com.google.common.collect.Iterables;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;

import slimeknights.tconstruct.library.tools.context.ToolAttackContext;

import vazkii.botania.api.mana.IManaItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.entity.EntityPixie;

import tcintegrations.common.capabilities.CapabilityRegistry;
import tcintegrations.items.armor.modifiers.TerrafirmModifier;
import vazkii.botania.xplat.IXplatAbstractions;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

public class BotaniaHelper {

    private static final MobEffect[] potions = {
        MobEffects.BLINDNESS,
        MobEffects.WITHER,
        MobEffects.MOVEMENT_SLOWDOWN,
        MobEffects.WEAKNESS
    };

    public static void spawnPixie(Player player, ItemStack stack, ToolAttackContext context) {
        EntityPixie pixie = new EntityPixie(player.level);

        pixie.setPos(player.getX(), player.getY() + 2, player.getZ());

        if (TerrafirmModifier.hasArmorSet(player)) {
            pixie.setApplyPotionEffect(new MobEffectInstance(potions[player.level.random.nextInt(potions.length)], 40, 0));
        }

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


    public static int getManaPerDamageBonus(Player player, int mana) {
        AtomicReference<Double> decreaseModifier = new AtomicReference<>(1.0);

        player.getCapability(CapabilityRegistry.BOTANIA_SET_CAPABILITY).ifPresent(data -> {
            if (data.hasTerrafirm()) {
                decreaseModifier.set(0.8);
            }
            else if (data.hasGreatFairy()) {
                decreaseModifier.set(0.9);
            }
        });

        return (int) (mana * decreaseModifier.get());
    }

    public static boolean dispatchManaExact(Player player, int manaToSend) {
        List<ItemStack> items = ManaItemHandler.INSTANCE.get().getManaItems(player);
        List<ItemStack> acc = ManaItemHandler.INSTANCE.get().getManaAccesories(player);

        for (ItemStack stackInSlot : Iterables.concat(items, acc)) {
            IManaItem manaItemSlot = IXplatAbstractions.INSTANCE.findManaItem(stackInSlot);

            if (manaItemSlot.getMana() + manaToSend <= manaItemSlot.getMaxMana()) {
                manaItemSlot.addMana(manaToSend);

                return true;
            }
        }

        return false;
    }

}
