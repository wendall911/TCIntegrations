package tcintegrations.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import tcintegrations.common.capabilities.CapabilityRegistry;

public class BotaniaSetData implements IData {

    private final boolean terrestrial;
    private final boolean greatFairy;

    public BotaniaSetData(boolean terrestrial, boolean greatFairy) {
        this.terrestrial = terrestrial;
        this.greatFairy = greatFairy;
    }

    public BotaniaSetData(FriendlyByteBuf buf) {
        terrestrial = buf.readBoolean();
        greatFairy = buf.readBoolean();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(terrestrial);
        buf.writeBoolean(greatFairy);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                Minecraft.getInstance().player.getCapability(CapabilityRegistry.BOTANIA_SET_CAPABILITY).ifPresent(data -> {
                    data.setTerrestrial(this.terrestrial);
                    data.setGreatFairy(this.greatFairy);
                });
            });
        }
    }

}
