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

    /* 这种方法注册不进去，不知道为什么 */
    /*
    @SubscribeEvent
    void commonSetup(FMLCommonSetupEvent event) {
        ToolCapabilityProvider.register(ToolEnergyCapability.TinkerProvider::new);
    }
     */


}
