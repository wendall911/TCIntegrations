package tcintegrations.items.modifiers.hooks;

import java.util.Collection;

import net.minecraft.world.entity.LivingEntity;

import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public interface IArmorCrouchModifier {

    default void onCrouch(IToolStackView tool, int level, LivingEntity living) {}

    default void onStand(LivingEntity living) {}

    record AllMerger(Collection<IArmorCrouchModifier> modules) implements IArmorCrouchModifier {

        @Override
        public void onCrouch(IToolStackView tool, int level, LivingEntity living) {
            for (IArmorCrouchModifier module : modules) {
                module.onCrouch(tool, level, living);
            }
        }

        @Override
        public void onStand(LivingEntity living) {
            for (IArmorCrouchModifier module : modules) {
                module.onStand(living);
            }
        }

    }

}
