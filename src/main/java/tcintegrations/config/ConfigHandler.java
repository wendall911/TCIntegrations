package tcintegrations.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

import org.apache.commons.lang3.tuple.Pair;

import tcintegrations.TCIntegrations;

@Mod.EventBusSubscriber(modid = TCIntegrations.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ConfigHandler {

    private ConfigHandler() {}

    public static void init() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Common.CONFIG_SPEC);
    }

    public static final class Common {

        public static final ForgeConfigSpec CONFIG_SPEC;

        private static final Common CONFIG;

        static {
            Pair<Common,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);

            CONFIG_SPEC = specPair.getRight();
            CONFIG = specPair.getLeft();
        }

        Common(ForgeConfigSpec.Builder builder) {
        }

    }

}
