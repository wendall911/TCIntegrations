package tcintegrations.client;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import tcintegrations.TCIntegrations;

import java.util.Locale;

@Mod.EventBusSubscriber(modid = TCIntegrations.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public enum TCIntegrationsSE {
    DISCHARGE;

    private final SoundEvent sound;
    private final String key;
    TCIntegrationsSE() {
        key = name().toLowerCase(Locale.US);
        sound = new SoundEvent(new ResourceLocation(TCIntegrations.MODID, key));
    }

    @SubscribeEvent
    public static void registerSounds(RegisterEvent event) {
        if (event.getRegistryKey() == Registry.SOUND_EVENT_REGISTRY) {
            for (TCIntegrationsSE se : values()) {
                ForgeRegistries.SOUND_EVENTS.register(
                        se.key,
                        se.sound);
            }
        }
    }

    public SoundEvent get() {
        return sound;
    }
}
