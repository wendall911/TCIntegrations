package tcintegrations.items.modifiers.hooks;

import java.util.Collection;
import java.util.function.Function;

import net.minecraft.world.entity.LivingEntity;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import tcintegrations.TCIntegrations;

public interface ArmorCrouchModifierHook {

    ArmorCrouchModifierHook EMPTY = new ArmorCrouchModifierHook() {};

    Function<Collection<ArmorCrouchModifierHook>, ArmorCrouchModifierHook> ALL_MERGER = AllMerger::new;

    default void onCrouch(IToolStackView tool, ModifierEntry modifier, LivingEntity living) {
        TCIntegrations.LOGGER.warn("default onCrouch");
    }

    default void onStand(ModifierEntry modifier, LivingEntity living) {};

    record AllMerger(Collection<ArmorCrouchModifierHook> modules) implements ArmorCrouchModifierHook {

        @Override
        public void onCrouch(IToolStackView tool, ModifierEntry modifier, LivingEntity living) {
            for (ArmorCrouchModifierHook module : modules) {
                module.onCrouch(tool, modifier, living);
            }
        }

        @Override
        public void onStand(ModifierEntry modifier, LivingEntity living) {
            for (ArmorCrouchModifierHook module : modules) {
                module.onStand(modifier, living);
            }
        }

    }

}