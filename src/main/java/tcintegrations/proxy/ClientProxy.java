package tcintegrations.proxy;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import tcintegrations.client.compat.Create;
import tcintegrations.data.integration.ModIntegration;

@OnlyIn(Dist.CLIENT)
public final class ClientProxy extends CommonProxy {

    public ClientProxy() {}

    @Override
    public void registerListeners(IEventBus bus) {
        super.registerListeners(bus);

        bus.addListener(this::clientSetup);
    }

    public void clientSetup(final FMLClientSetupEvent event) {
        if (ModList.get().isLoaded(ModIntegration.CREATE_MODID)) {
            Create.init();
        }
    }

}
