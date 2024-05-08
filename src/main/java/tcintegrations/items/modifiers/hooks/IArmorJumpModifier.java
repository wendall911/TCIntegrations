package tcintegrations.items.modifiers.hooks;

import java.util.Collection;
import java.util.function.Function;

import net.minecraft.world.entity.LivingEntity;

import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public interface IArmorJumpModifier {

    IArmorJumpModifier EMPTY = ((tool, living) -> {});

    Function<Collection<IArmorJumpModifier>, IArmorJumpModifier> ALL_MERGER = AllMerger::new;

    void onJump(IToolStackView tool, LivingEntity living);

    record AllMerger(Collection<IArmorJumpModifier> modules) implements IArmorJumpModifier {

        @Override
        public void onJump(IToolStackView tool, LivingEntity living) {
            for (IArmorJumpModifier module : modules) {
                module.onJump(tool, living);
            }
        }

    }
}
