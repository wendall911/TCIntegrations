package tcintegrations.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ArsElementalClientHelper {

    public static boolean hasAirArmorSet() {
        final Player player = Minecraft.getInstance().player != null ? Minecraft.getInstance().player : null;

        if (player != null) {
            return ArsElementalHelper.hasAirArmorSet(player);
        }

        return false;
    }

    public static boolean hasAquaArmorSet() {
        final Player player = Minecraft.getInstance().player != null ? Minecraft.getInstance().player : null;

        if (player != null) {
            return ArsElementalHelper.hasAquaArmorSet(player);
        }

        return false;
    }

    public static boolean hasEarthArmorSet() {
        final Player player = Minecraft.getInstance().player != null ? Minecraft.getInstance().player : null;

        if (player != null) {
            return ArsElementalHelper.hasEarthArmorSet(player);
        }

        return false;
    }

    public static boolean hasFireArmorSet() {
        final Player player = Minecraft.getInstance().player != null ? Minecraft.getInstance().player : null;

        if (player != null) {
            return ArsElementalHelper.hasFireArmorSet(player);
        }

        return false;
    }

}
