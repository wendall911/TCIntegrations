package tcintegrations;

import java.util.Random;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import tcintegrations.common.TCIntegrationsModule;
import tcintegrations.proxy.ClientProxy;
import tcintegrations.proxy.ServerProxy;

@Mod(TCIntegrations.MODID)
public class TCIntegrations {

    public static final String MODID = "tcintegrations";
    public static final Logger LOGGER = LogManager.getFormatterLogger(TCIntegrations.MODID);
    public static final Random RANDOM = new Random();

    public static IEventBus BUS;

    public TCIntegrations() {
        BUS = FMLJavaModLoadingContext.get().getModEventBus();

        TCIntegrationsModule.initRegistries(TCIntegrations.BUS);

        if (FMLEnvironment.dist.isClient()) {
            BUS.register(new ClientProxy());
        }
        else {
            BUS.register(new ServerProxy());
        }
    }

}
