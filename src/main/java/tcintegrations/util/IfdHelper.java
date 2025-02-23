package tcintegrations.util;

import com.github.alexthe666.iceandfire.entity.EntityGhostSword;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;

import net.minecraft.world.entity.player.Player;

public class IfdHelper {

    public static void shootGhostSword(Player player, float damage) {
        EntityGhostSword ghostSword = new EntityGhostSword(IafEntityRegistry.GHOST_SWORD.get(), player.level(), player, damage * 0.5F);

        ghostSword.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.0F, 0.5F);
        player.level().addFreshEntity(ghostSword);
    }

}
