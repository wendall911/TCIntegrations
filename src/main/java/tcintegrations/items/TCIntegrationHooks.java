package tcintegrations.items;

import java.util.Collection;
import java.util.function.Function;

import org.jetbrains.annotations.Nullable;

import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.module.ModuleHook;

import tcintegrations.items.modifiers.hooks.IArmorCrouchModifier;
import tcintegrations.items.modifiers.hooks.IArmorJumpModifier;

import static tcintegrations.util.ResourceLocationHelper.resource;

public class TCIntegrationHooks {

    public static ModuleHook<IArmorCrouchModifier> CROUCH;
    public static ModuleHook<IArmorJumpModifier> JUMP;

    private TCIntegrationHooks() {}

    public static void init() {
        CROUCH = register("crouch", IArmorCrouchModifier.class, IArmorCrouchModifier.AllMerger::new, new IArmorCrouchModifier() {});
        JUMP = register("jump", IArmorJumpModifier.class, IArmorJumpModifier.ALL_MERGER, (tool, living) -> {});
    }

    private static <T> ModuleHook<T> register(String name, Class<T> filter, @Nullable Function<Collection<T>,T> merger, T defaultInstance) {
        return ModifierHooks.register(resource(name), filter, merger, defaultInstance);
    }

}
