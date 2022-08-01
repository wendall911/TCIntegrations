package tcintegrations.proxy;

import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import tcintegrations.common.json.ConfigEnabledCondition;
import tcintegrations.config.ConfigHandler;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.TCIntegrationsItems;
import tcintegrations.items.TCIntegrationsModifiers;
import tcintegrations.network.NetworkHandler;

public class CommonProxy {

    public static LootItemConditionType LOOT_CONFIG;

    CommonProxy() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        ConfigHandler.init();
        TCIntegrationsItems.init();
        TCIntegrationsModifiers.init();
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
        }

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerItems(RegistryEvent.Register<Item> event) {
            ModIntegration.init(event.getRegistry());
        }

        @SubscribeEvent
        public static void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
            CraftingHelper.register(ConfigEnabledCondition.SERIALIZER);

            LOOT_CONFIG = Registry.register(
                    Registry.LOOT_CONDITION_TYPE,
                    ConfigEnabledCondition.ID,
                    new LootItemConditionType(ConfigEnabledCondition.SERIALIZER)
            );
        }

    }

}
