package tcintegrations.items.modifiers.hooks;

import net.minecraft.world.entity.LivingEntity;

import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public interface IArmorCrouchModifier {

    void onCrouch(IToolStackView tool, int level, LivingEntity living);

    void onStand(LivingEntity living);

}
