package tcintegrations.items;

import java.util.Collection;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import net.minecraft.resources.ResourceLocation;

import slimeknights.tconstruct.library.modifiers.ModifierHook;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;

import tcintegrations.TCIntegrations;
import tcintegrations.items.modifiers.hooks.IArmorCrouchModifier;
import tcintegrations.items.modifiers.hooks.IArmorJumpModifier;

public class TCIntegrationHooks {

    public static ModifierHook<IArmorCrouchModifier> CROUCH;
    public static ModifierHook<IArmorJumpModifier> JUMP;

    private TCIntegrationHooks() {}

    public static void init() {
        CROUCH = register("crouch", IArmorCrouchModifier.class, IArmorCrouchModifier.AllMerger::new, new IArmorCrouchModifier() {});
        JUMP = register("jump", IArmorJumpModifier.class, IArmorJumpModifier.ALL_MERGER, (tool, living) -> {});
    }

    private static <T> ModifierHook<T> register(String name, Class<T> filter, @Nullable Function<Collection<T>,T> merger, T defaultInstance) {
        return ModifierHooks.register(new ResourceLocation(TCIntegrations.MODID, name), filter, defaultInstance, merger);
    }

}
