package tcintegrations.network;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.network.NetworkEvent;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.TinkerHooks;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.TCIntegrationsModifiers;

public class LaunchGhostSword implements IData {

    public LaunchGhostSword() {}

    public LaunchGhostSword(FriendlyByteBuf buf) {}

    @Override
    public void toBytes(FriendlyByteBuf buf) {}

    @Override
    public void process(Supplier<NetworkEvent.Context> ctx) {
        ServerPlayer sp = ctx.get().getSender();

        if (sp != null) {
            ItemStack stack = sp.getMainHandItem();

            if (sp.getCooldowns().isOnCooldown(stack.getItem())) {
                return;
            }

            ToolStack tool = ToolStack.from(stack);
            List<ModifierEntry> modifiers = tool.getModifierList();

            modifiers.forEach(modifierEntry -> {
                if (ModList.get().isLoaded(ModIntegration.IFD_MODID) && modifierEntry.getId().equals(TCIntegrationsModifiers.PHANTASMAL_MODIFIER.getId())) {
                    modifierEntry.getHook(TinkerHooks.PROJECTILE_LAUNCH).onProjectileLaunch(tool, modifierEntry, sp, null, null, null, false);
                }
            });
        }
    }

}
