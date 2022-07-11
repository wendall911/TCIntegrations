package tcintegrations.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.network.PacketDistributor;

import tcintegrations.common.capabilities.CapabilityRegistry;
import tcintegrations.network.BotaniaSetData;
import tcintegrations.network.NetworkHandler;
import tcintegrations.TCIntegrations;

@Mod.EventBusSubscriber(modid= TCIntegrations.MODID)
public class PlayerEventHandler {

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        final Player player = event.getEntity() instanceof Player ? (Player) event.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;

            if (ModList.get().isLoaded("botania")) {
                sp.getCapability(CapabilityRegistry.BOTANIA_SET_CAPABILITY).ifPresent(data -> {

                    NetworkHandler.INSTANCE.send(
                            PacketDistributor.PLAYER.with(() -> sp),
                            new BotaniaSetData(data.hasTerrestrial(), data.hasGreatFairy())
                    );
                });
            }
        }
    }

}
