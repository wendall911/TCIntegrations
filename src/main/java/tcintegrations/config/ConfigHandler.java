package tcintegrations.config;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.List;

import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
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

        public static BooleanValue ENABLE_BRONZE_RECIPE;

        private static IntValue MAX_DIAMOND_MODIFIER_TIER;
        private static IntValue MAX_EMERALD_MODIFIER_TIER;

        static {
            Pair<Common,ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(Common::new);

            CONFIG_SPEC = specPair.getRight();
            CONFIG = specPair.getLeft();
        }

        Common(ForgeConfigSpec.Builder builder) {
            ENABLE_BRONZE_RECIPE = builder
                .comment("Enables bronze recipe. 3 copper + 1 quartz = 4 bronze")
                .define("ENABLE_BRONZE_RECIPE", true);
            MAX_DIAMOND_MODIFIER_TIER = builder
                .comment("Max diamond modifier tier for upgrade. (0 = wood, 1 = gold, 2 = stone, 3 = iron, 4 = diamond, 5 = netherite)")
                .defineInRange("MAX_DIAMOND_MODIFIER_TIER", 4, 0, 5);
            MAX_EMERALD_MODIFIER_TIER = builder
                .comment("Max emerald modifier tier for upgrade. (0 = wood, 1 = gold, 2 = stone, 3 = iron, 4 = diamond, 5 = netherite)")
                .defineInRange("MAX_EMERALD_MODIFIER_TIER", 3, 0, 5);
        }

        public static Tier maxDiamondModifierTier() {
            return getTier(CONFIG.MAX_DIAMOND_MODIFIER_TIER.get());
        }

        public static Tier maxEmeraldModifierTier() {
            return getTier(CONFIG.MAX_EMERALD_MODIFIER_TIER.get());
        }

        private static Tier getTier(int level) {
            Tier tier;

            switch(level) {
                case 1:
                    tier = Tiers.GOLD;
                    break;
                case 2:
                    tier = Tiers.STONE;
                    break;
                case 3:
                    tier = Tiers.IRON;
                    break;
                case 4:
                    tier = Tiers.DIAMOND;
                    break;
                case 5:
                    tier = Tiers.NETHERITE;
                    break;
                default:
                    tier = Tiers.WOOD;
                    break;
            }

            return tier;
        }

    }

}
