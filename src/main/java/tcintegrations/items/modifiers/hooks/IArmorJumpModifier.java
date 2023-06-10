package tcintegrations.items.modifiers.hooks;

import net.minecraft.world.entity.LivingEntity;

import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public interface IArmorJumpModifier {

    void onJump(IToolStackView tool, LivingEntity living);

}
