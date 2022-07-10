package tcintegrations.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tcintegrations.TCIntegrations;
import tcintegrations.common.capabilities.BotaniaSet;

@Mod.EventBusSubscriber(modid= TCIntegrations.MODID)
public class CapabilityEventHandler {

    @SubscribeEvent
    public static void addCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer)) {
            event.addCapability(
                new ResourceLocation(TCIntegrations.MODID, "botaniaset"),
                new BotaniaSet.Provider()
            );

        }
    }
    
}
