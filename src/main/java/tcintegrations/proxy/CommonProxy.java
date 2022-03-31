package tcintegrations.proxy;

import tcintegrations.config.ConfigHandler;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.TCIntegrationsItems;

public class CommonProxy {

    CommonProxy() {
        ConfigHandler.init();
        TCIntegrationsItems.init();
        ModIntegration.init();
    }

}
