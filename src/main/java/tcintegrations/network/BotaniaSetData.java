package tcintegrations.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import tcintegrations.common.capabilities.CapabilityRegistry;

public class BotaniaSetData implements IData {

    private boolean terrafirm;
    private boolean greatfairy;

    public BotaniaSetData(boolean terrafirm, boolean greatfairy) {
        this.terrafirm = terrafirm;
        this.greatfairy = greatfairy;
    }

    public BotaniaSetData(FriendlyByteBuf buf) {
        terrafirm = buf.readBoolean();
        greatfairy = buf.readBoolean();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(terrafirm);
        buf.writeBoolean(greatfairy);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                Minecraft.getInstance().player.getCapability(CapabilityRegistry.BOTANIA_SET_CAPABILITY).ifPresent(data -> {
                    data.setTerrafirm(terrafirm);
                    data.setGreatfairy(greatfairy);
                });
            });
        }
    }

}
