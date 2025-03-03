package tcintegrations.items.modifiers.armor;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import tcintegrations.common.capabilities.ArsElementalSet;
import tcintegrations.items.TCIntegrationsModifiers;
import tcintegrations.util.ArsElementalClientHelper;
import tcintegrations.util.ArsElementalHelper;

public class GeomancerModifier extends ArsElementalSetBase implements InventoryTickModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.EQUIPMENT_CHANGE, ModifierHooks.MODIFY_DAMAGE, ModifierHooks.INVENTORY_TICK);
    }

    @Override
    public boolean hasArmorSet() {
        return ArsElementalClientHelper.hasEarthArmorSet();
    }

    @Override
    public void setHasSet(ArsElementalSet data, boolean hasSet) {
        data.setEarth(hasSet);
    }

    @Override
    public ModifierId getModifierId() {
        return TCIntegrationsModifiers.GEOMANCER_MODIFIER.getId();
    }

    @Override
    public boolean hasArmorSet(Player player) {
        return ArsElementalHelper.hasEarthArmorSet(player);
    }


    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level level, LivingEntity entity, int slot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
       final Player player = entity instanceof Player ? (Player) entity : null;

        if (player != null && !player.level().isClientSide && hasArmorSet(player) && player.getEyePosition().y() < 20 && player.getFoodData().getFoodLevel() < 2) {
            player.getFoodData().setFoodLevel(20);
        }
    }

}
