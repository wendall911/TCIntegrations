package tcintegrations.event;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import slimeknights.tconstruct.library.tools.item.ModifiableItem;
import tcintegrations.TCIntegrations;
import tcintegrations.common.capabilities.BotaniaSet;
import tcintegrations.common.capabilities.ToolEnergyCapability;
import tcintegrations.data.integration.ModIntegration;

@Mod.EventBusSubscriber(modid= TCIntegrations.MODID)
public class CapabilityEventHandler {

    @SubscribeEvent
    public static void addCapability(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player && !(event.getObject() instanceof FakePlayer)) {
            if (ModList.get().isLoaded(ModIntegration.BOTANIA_MODID)) {
                event.addCapability(
                    new ResourceLocation(TCIntegrations.MODID, "botaniaset"),
                    new BotaniaSet.Provider()
                );
            }
        }

    }

    public static final ResourceLocation TOOL_ENERGY = new ResourceLocation(TCIntegrations.MODID, "tool_energy");
    @SubscribeEvent
    public static void onAttachCapabilitiesEvent(AttachCapabilitiesEvent<ItemStack> event) {

        if ((!event.getCapabilities().containsKey(TOOL_ENERGY)) && (event.getObject().getItem() instanceof ModifiableItem)) {
                    event.addCapability(
                            TOOL_ENERGY,
                            new ToolEnergyCapability.ForgeProvider(event.getObject())
            );
        }
    }
}
