package tcintegrations.common;

import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import tcintegrations.common.json.ConfigEnabledCondition;
import tcintegrations.common.TCIntegrationsModule;
import tcintegrations.TCIntegrations;

public final class TCIntegrationsCommon extends TCIntegrationsModule {

    public static LootItemConditionType LOOT_CONFIG;

    @SubscribeEvent
    void registerRecipeSerializers(RegistryEvent.Register<RecipeSerializer<?>> event) {
        LOOT_CONFIG = Registry.register(
            Registry.LOOT_CONDITION_TYPE,
            ConfigEnabledCondition.ID,
            new LootItemConditionType(ConfigEnabledCondition.SERIALIZER)
        );
    }

}
