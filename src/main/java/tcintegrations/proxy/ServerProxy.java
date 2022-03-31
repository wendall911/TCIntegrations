package tcintegrations.proxy;

import net.minecraftforge.fml.event.lifecycle.FMLDedicatedServerSetupEvent;

import tcintegrations.TCIntegrations;

public final class ServerProxy extends CommonProxy {

    public ServerProxy() {
		TCIntegrations.BUS.addListener(this::serverSetup);
    }

	private void serverSetup(FMLDedicatedServerSetupEvent event) {
    }

}
