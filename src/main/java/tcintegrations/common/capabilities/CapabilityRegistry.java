package tcintegrations.common.capabilities;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import tcintegrations.TCIntegrations;

@Mod.EventBusSubscriber(modid= TCIntegrations.MODID)
public class CapabilityRegistry {

    public static final Capability<BotaniaSet> BOTANIA_SET_CAPABILITY = CapabilityManager.get(new CapabilityToken<>(){});

    @SubscribeEvent
    public static void registerCapability(RegisterCapabilitiesEvent event) {
        event.register(BotaniaSet.class);
    }

}
