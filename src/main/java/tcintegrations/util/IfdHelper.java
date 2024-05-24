package tcintegrations.util;

import com.github.alexthe666.iceandfire.entity.EntityGhostSword;
import com.github.alexthe666.iceandfire.entity.IafEntityRegistry;

import com.mojang.math.Vector3f;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public class IfdHelper {

    public static void shootGhostSword(Player player, float damage) {
        EntityGhostSword ghostSword = new EntityGhostSword(IafEntityRegistry.GHOST_SWORD.get(), player.getLevel(), player, damage * 0.5F);
        Vec3 vector3d = player.getViewVector(1.0F);
        Vector3f vector3f = new Vector3f(vector3d);

        ghostSword.shoot(vector3f.x(), vector3f.y(), vector3f.z(), 1.0F, 0.5F);
        player.level.addFreshEntity(ghostSword);
    }

}
