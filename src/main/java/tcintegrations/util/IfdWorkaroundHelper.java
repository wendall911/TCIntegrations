package tcintegrations.util;

import net.minecraftforge.fml.ModList;

import tcintegrations.data.integration.ModIntegration;

public class IfdWorkaroundHelper {

    /*
     * This class is necessary since the Community Edition breaks the API of IFD, but doesn't bother to change the modid.
     */

    public static boolean isLoaded() {
        if (ModList.get().isLoaded(ModIntegration.IFD_MODID)) {
            try {
                Class.forName("com.github.alexthe666.iceandfire.IceAndFire");

                return true;
            } catch (ClassNotFoundException e) {
                return false;
            }
        }

        return false;
    }

}
