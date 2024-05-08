package tcintegrations.util;

import com.google.common.collect.Iterables;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;

import vazkii.botania.api.mana.ManaItem;
import vazkii.botania.api.mana.ManaItemHandler;
import vazkii.botania.common.entity.PixieEntity;
import vazkii.botania.xplat.XplatAbstractions;

import tcintegrations.common.capabilities.CapabilityRegistry;

public class BotaniaHelper {

    private static final MobEffect[] potions = {
        MobEffects.BLINDNESS,
        MobEffects.WITHER,
        MobEffects.MOVEMENT_SLOWDOWN,
        MobEffects.WEAKNESS
    };

    public static void spawnPixie(ServerPlayer sp, ItemStack stack, LivingEntity target) {
        PixieEntity pixie = new PixieEntity(sp.level);

        pixie.setPos(sp.getX(), sp.getY() + 2, sp.getZ());

        if (hasGreatFairyArmorSet(sp)) {
            pixie.setApplyPotionEffect(new MobEffectInstance(potions[sp.level.random.nextInt(potions.length)], 40, 0));
        }

        float dmg = 4;

        if (!stack.isEmpty()) {
            dmg += 2;
        }

        pixie.setProps(target, sp, 0, dmg);
        pixie.finalizeSpawn(
                (ServerLevelAccessor) sp.level,
                sp.level.getCurrentDifficultyAt(pixie.blockPosition()),
                MobSpawnType.EVENT, null, null);
        sp.level.addFreshEntity(pixie);
    }


    public static int getManaPerDamageBonus(Player player, int mana) {
        AtomicReference<Double> decreaseModifier = new AtomicReference<>(1.0);

        player.getCapability(CapabilityRegistry.BOTANIA_SET_CAPABILITY).ifPresent(data -> {
            if (data.hasTerrestrial()) {
                if (data.hasAlfheim()) {
                    decreaseModifier.set(0.8);
                }
                else {
                    decreaseModifier.set(0.7);
                }
            }
            else if (data.hasGreatFairy()) {
                decreaseModifier.set(0.9);
            }
        });

        return (int) (mana * decreaseModifier.get());
    }

    public static boolean dispatchManaExact(ServerPlayer player, int manaToSend) {
        List<ItemStack> items = ManaItemHandler.INSTANCE.getManaItems(player);
        List<ItemStack> acc = ManaItemHandler.INSTANCE.getManaAccesories(player);

        for (ItemStack stackInSlot : Iterables.concat(items, acc)) {
            ManaItem manaItemSlot = XplatAbstractions.INSTANCE.findManaItem(stackInSlot);

            if (manaItemSlot.getMana() + manaToSend <= manaItemSlot.getMaxMana()) {
                manaItemSlot.addMana(manaToSend);

                return true;
            }
        }

        return false;
    }

    public static boolean hasTerrestrialArmorSet(Player player) {
        AtomicBoolean hasSet = new AtomicBoolean(false);

        player.getCapability(CapabilityRegistry.BOTANIA_SET_CAPABILITY).ifPresent(data -> {
            hasSet.set(data.hasTerrestrial());
        });

        return hasSet.get();
    }

    public static boolean hasGreatFairyArmorSet(Player player) {
        AtomicBoolean hasSet = new AtomicBoolean(false);

        player.getCapability(CapabilityRegistry.BOTANIA_SET_CAPABILITY).ifPresent(data -> {
            hasSet.set(data.hasGreatFairy());
        });

        return hasSet.get();
    }

    public static boolean hasAlfheimArmorSet(Player player) {
        AtomicBoolean hasSet = new AtomicBoolean(false);

        player.getCapability(CapabilityRegistry.BOTANIA_SET_CAPABILITY).ifPresent(data -> {
            hasSet.set(data.hasAlfheim());
        });

        return hasSet.get();
    }

}