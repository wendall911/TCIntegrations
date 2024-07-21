package tcintegrations.items.modifiers.energy;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import tcintegrations.common.capabilities.ToolEnergyHelper;

public class EnergyRepairModifier extends Modifier implements InventoryTickModifierHook {
    private static final int FE_PER_DAMAGE = 2500;
    public static int getFePerDamage(){return FE_PER_DAMAGE;}

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.INVENTORY_TICK);
        hookBuilder.addModule(ToolEnergyHelper.ENERGY_HANDLER);
        hookBuilder.addModule(StatBoostModule.add(ToolEnergyHelper.ENERGY_CAPACITY_STAT).eachLevel(250000));
    }
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder, int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        final Player player = holder instanceof Player ? (Player) holder : null;
        if (player != null && !player.level.isClientSide) {
            ServerPlayer sp = (ServerPlayer) player;
            if (sp.tickCount % 200 != 0){
                return;
            }
            for(int counter = 0; counter < modifier.getLevel(); counter++){
                if (tool.getDamage() > 0
                        && ToolEnergyHelper.ENERGY_HELPER.getEnergy(tool) >= getFePerDamage()){
                    ToolEnergyHelper.ENERGY_HELPER.setEnergy(tool,ToolEnergyHelper.ENERGY_HELPER.getEnergy(tool) - getFePerDamage());
                    tool.setDamage(tool.getDamage() - 1);
                }
            }
        }
    }


}
