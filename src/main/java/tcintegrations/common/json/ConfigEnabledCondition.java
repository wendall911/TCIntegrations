package tcintegrations.common.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSyntaxException;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.BooleanSupplier;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.Serializer;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;

import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionSerializer;

import tcintegrations.config.ConfigHandler;
import tcintegrations.TCIntegrations;
import tcintegrations.proxy.CommonProxy;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ConfigEnabledCondition implements ICondition, LootItemCondition {

    public static final ResourceLocation ID = new ResourceLocation(TCIntegrations.MODID, "config");
    public static final ConfigSerializer SERIALIZER = new ConfigSerializer();

    /* Map of config names to condition cache */
    private static final Map<String,ConfigEnabledCondition> PROPS = new HashMap<>();

    private final String configName;
    private final BooleanSupplier supplier;

    /* Properties */
    public static final ConfigEnabledCondition BRONZE_RECIPE = add("bronze_recipe", ConfigHandler.Common.ENABLE_BRONZE_RECIPE);

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public boolean test() {
        return supplier.getAsBoolean();
    }

    @Override
    public boolean test(LootContext lootContext) {
        return supplier.getAsBoolean();
    }

    @Override
    public LootItemConditionType getType() {
        return CommonProxy.LOOT_CONFIG;
    }

    private static class ConfigSerializer implements Serializer<ConfigEnabledCondition>, IConditionSerializer<ConfigEnabledCondition> {

        @Override
        public ResourceLocation getID() {
            return ID;
        }

        @Override
        public void write(JsonObject json, ConfigEnabledCondition value) {
            json.addProperty("prop", value.configName);
        }

        @Override
        public ConfigEnabledCondition read(JsonObject json) {
            String prop = GsonHelper.getAsString(json, "prop");
            ConfigEnabledCondition config = PROPS.get(prop.toLowerCase(Locale.ROOT));

            if (config == null) {
                throw new JsonSyntaxException("Invalid property name '" + prop + "'");
            }

            return config;
        }

        @Override
        public void serialize(JsonObject json, ConfigEnabledCondition condition, JsonSerializationContext context) {
            write(json, condition);
        }

        @Override
        public ConfigEnabledCondition deserialize(JsonObject json, JsonDeserializationContext context) {
            return read(json);
        }
    }

    /**
     * Adds a condition
     * @param prop     Property name
     * @param supplier Boolean supplier
     * @return Added condition
     */
    private static ConfigEnabledCondition add(String prop, BooleanSupplier supplier) {
        ConfigEnabledCondition conf = new ConfigEnabledCondition(prop, supplier);
        PROPS.put(prop.toLowerCase(Locale.ROOT), conf);

        return conf;
    }

    /**
     * Adds a condition
     * @param prop     Property name
     * @param supplier Config value
     * @return Added condition
     */
    private static ConfigEnabledCondition add(String prop, BooleanValue supplier) {
        return add(prop, supplier::get);
    }

    @Override
    public String toString() {
        return "config_setting_enabled(\"" + this.configName + "\")";
    }

}
