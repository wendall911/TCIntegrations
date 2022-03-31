package tcintegrations;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tcintegrations.common.TCIntegrationsCommon;
import tcintegrations.common.TCIntegrationsModule;
import tcintegrations.proxy.ClientProxy;
import tcintegrations.proxy.ServerProxy;

@Mod(TCIntegrations.MODID)
public class TCIntegrations {

    public static final String MODID = "tcintegrations";
    public static final Logger LOGGER = LogManager.getFormatterLogger(TCIntegrations.MODID);

    public static TCIntegrations INSTANCE;
    public static IEventBus BUS;

    public TCIntegrations() {
        BUS = FMLJavaModLoadingContext.get().getModEventBus();
        INSTANCE = this;

        MinecraftForge.EVENT_BUS.register(INSTANCE);

        TCIntegrationsModule.initRegistries(TCIntegrations.BUS);

        BUS.register(new TCIntegrationsCommon());

        DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);
    }

}
