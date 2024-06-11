package tcintegrations.proxy;

import net.minecraft.core.Registry;

import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.RegisterEvent;

import tcintegrations.common.ConsecrationTConstructModule;
import tcintegrations.config.ConfigHandler;
import tcintegrations.data.integration.CreateGogglesPredicate;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.TCIntegrationHooks;
import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.items.TCIntegrationsModifiers;
import tcintegrations.network.NetworkHandler;

public class CommonProxy {

    CommonProxy() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ConfigHandler.init();
        TCIntegrationsItems.init();
        TCIntegrationsModifiers.init();
        TCIntegrationHooks.init();
        registerListeners(bus);
        ModIntegration.setup();
    }

    public void registerListeners(IEventBus bus) {
        bus.register(Listeners.class);
    }

    public static final class Listeners {

        @SubscribeEvent
        public static void setup(FMLCommonSetupEvent event) {
            NetworkHandler.init();
            TCIntegrationsItems.setup(event);
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerItems(RegisterEvent event) {
            event.register(Registry.ITEM_REGISTRY, ModIntegration::init);
        }

        @SubscribeEvent
        public static void imcEnqueue(final InterModEnqueueEvent event) {
            if (ModList.get().isLoaded(ModIntegration.CONSECRATION_MODID)) {
                ConsecrationTConstructModule.setup();
            }
            if (ModList.get().isLoaded(ModIntegration.CREATE_MODID)) {
                CreateGogglesPredicate.init();
            }
        }

    }

}
