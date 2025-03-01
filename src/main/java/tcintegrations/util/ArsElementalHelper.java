package tcintegrations.util;

import java.util.concurrent.atomic.AtomicBoolean;

import net.minecraft.world.entity.player.Player;

import tcintegrations.common.capabilities.CapabilityRegistry;

public class ArsElementalHelper {

    public static boolean hasAirArmorSet(Player player) {
        AtomicBoolean hasSet = new AtomicBoolean(false);

        player.getCapability(CapabilityRegistry.ARS_ELEMENTAL_SET_CAPABILITY).ifPresent(data -> {
            hasSet.set(data.hasAir());
        });

        return hasSet.get();
    }

    public static boolean hasAquaArmorSet(Player player) {
        AtomicBoolean hasSet = new AtomicBoolean(false);

        player.getCapability(CapabilityRegistry.ARS_ELEMENTAL_SET_CAPABILITY).ifPresent(data -> {
            hasSet.set(data.hasAqua());
        });

        return hasSet.get();
    }

    public static boolean hasEarthArmorSet(Player player) {
        AtomicBoolean hasSet = new AtomicBoolean(false);

        player.getCapability(CapabilityRegistry.ARS_ELEMENTAL_SET_CAPABILITY).ifPresent(data -> {
            hasSet.set(data.hasEarth());
        });

        return hasSet.get();
    }

    public static boolean hasFireArmorSet(Player player) {
        AtomicBoolean hasSet = new AtomicBoolean(false);

        player.getCapability(CapabilityRegistry.ARS_ELEMENTAL_SET_CAPABILITY).ifPresent(data -> {
            hasSet.set(data.hasFire());
        });

        return hasSet.get();
    }

}
