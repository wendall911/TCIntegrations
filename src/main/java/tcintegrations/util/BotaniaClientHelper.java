package tcintegrations.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class BotaniaClientHelper {

    public static boolean hasGreatFairyArmorSet() {
        final Player player = Minecraft.getInstance().player != null ? Minecraft.getInstance().player : null;

        if (player != null) {
            return BotaniaHelper.hasGreatFairyArmorSet(player);
        }

        return false;
    }

    public static boolean hasTerrestrialArmorSet() {
        final Player player = Minecraft.getInstance().player != null ? Minecraft.getInstance().player : null;

        if (player != null) {
            return BotaniaHelper.hasTerrestrialArmorSet(player);
        }

        return false;
    }

}
