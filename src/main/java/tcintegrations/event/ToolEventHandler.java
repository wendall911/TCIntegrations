package tcintegrations.event;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import tcintegrations.TCIntegrations;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.TCIntegrationHooks;
import tcintegrations.items.TCIntegrationsModifiers;
import tcintegrations.items.modifiers.hooks.IArmorCrouchModifier;
import tcintegrations.network.LaunchGhostSword;
import tcintegrations.network.NetworkHandler;

@Mod.EventBusSubscriber(modid = TCIntegrations.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ToolEventHandler {

    private static final TinkerDataCapability.ComputableDataKey<LastTick> LAST_TICK = createKey("last_tick", LastTick::new);

    @SubscribeEvent
    static void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity living = event.getEntity();

        if (!living.isSpectator() && !living.level.isClientSide() && living.isAlive()) {
            ItemStack helmet = living.getItemBySlot(EquipmentSlot.HEAD);

            if (!helmet.isEmpty() && helmet.is(TinkerTags.Items.HELMETS)) {
                ToolStack tool = ToolStack.from(helmet);

                for (ModifierEntry entry : tool.getModifierList()) {
                    IArmorCrouchModifier crouchModifier = entry.getHook(TCIntegrationHooks.CROUCH);

                    if (living.isCrouching() || living.isVisuallySwimming()) {
                        crouchModifier.onCrouch(tool, entry.getLevel(), living);
                    }
                    else {
                        crouchModifier.onStand(living);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    static void onLivingJump(LivingEvent.LivingJumpEvent event) {
        LivingEntity living = event.getEntity();

        if (!living.isSpectator() && !living.level.isClientSide() && living.isAlive()) {
            ItemStack boots = living.getItemBySlot(EquipmentSlot.FEET);

            if (!boots.isEmpty() && boots.is(TinkerTags.Items.BOOTS)) {
                ToolStack tool = ToolStack.from(boots);

                for (ModifierEntry entry : tool.getModifierList()) {
                    entry.getHook(TCIntegrationHooks.JUMP).onJump(tool, living);
                }
            }
        }
    }


    @SubscribeEvent
    static void onLeftClick(PlayerInteractEvent.LeftClickEmpty event) {
        final Player player = event.getEntity() != null ? event.getEntity() : null;

        if (player != null) {
            if (player.getCapability(TinkerDataCapability.CAPABILITY).filter(data -> data.computeIfAbsent(LAST_TICK).update(player)).isEmpty()) {
                return;
            }

            ItemStack stack = event.getItemStack();
            if (player.getCooldowns().isOnCooldown(stack.getItem())) {
                return;
            }

            ToolStack tool = ToolStack.from(stack);
            List<ModifierEntry> modifiers = tool.getModifierList();

            modifiers.forEach(modifierEntry -> {
                if (ModList.get().isLoaded(ModIntegration.IFD_MODID) && modifierEntry.getId().equals(TCIntegrationsModifiers.PHANTASMAL_MODIFIER.getId())) {
                    player.playSound(SoundEvents.ZOMBIE_INFECT, 1, 1);

                    NetworkHandler.INSTANCE.sendToServer(new LaunchGhostSword());
                }
            });
        }
    }

    private static class LastTick {
        private long lastTick = 0;

        private boolean update(Player player) {
            if (player.tickCount >= lastTick + 4) {
                lastTick = player.tickCount;

                return true;
            }

            return false;
        }
    }

    private static <T> TinkerDataCapability.ComputableDataKey<T> createKey(String name, Supplier<T> constructor) {
        return TinkerDataCapability.ComputableDataKey.of(new ResourceLocation(TCIntegrations.MODID, name), constructor);
    }

}