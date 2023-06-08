package tcintegrations.items.modifiers;

import java.util.Collection;
import java.util.function.Function;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHook;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import tcintegrations.TCIntegrations;
import tcintegrations.items.modifiers.hooks.ArmorCrouchModifierHook;

public class TCIntegrationsHooks {

    private TCIntegrationsHooks() {}

    public static void init() {}

    public static final ModifierHook<ArmorCrouchModifierHook> ARMOR_CROUCH = register("armor_crouch", ArmorCrouchModifierHook.class, ArmorCrouchModifierHook.ALL_MERGER, new ArmorCrouchModifierHook() {

        @Override
        public void onCrouch(IToolStackView tool, ModifierEntry modifier, LivingEntity living) {
            ArmorCrouchModifierHook interact = modifier.getModifier().getHook(ARMOR_CROUCH);

            interact.onCrouch(tool, modifier, living);
        }

        @Override
        public void onStand(ModifierEntry modifier, LivingEntity living) {
            ArmorCrouchModifierHook interact = modifier.getModifier().getHook(ARMOR_CROUCH);

            interact.onStand(modifier, living);
        }

    });

    private static <T> ModifierHook<T> register(String name, Class<T> filter, Function<Collection<T>,T> merger, T defaultInstance) {
        return ModifierHooks.register(new ResourceLocation(TCIntegrations.MODID, name), filter, defaultInstance, merger);
    }

}