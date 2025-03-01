package tcintegrations.network;

import java.util.function.Supplier;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;

import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import tcintegrations.common.capabilities.CapabilityRegistry;

public class ArsElementalSetData implements IData {

    private final boolean air;
    private final boolean aqua;
    private final boolean earth;
    private final boolean fire;

    public ArsElementalSetData(boolean air, boolean aqua, boolean earth, boolean fire) {
        this.air = air;
        this.aqua = aqua;
        this.earth = earth;
        this.fire = fire;
    }

    public ArsElementalSetData(FriendlyByteBuf buf) {
        air = buf.readBoolean();
        aqua = buf.readBoolean();
        earth = buf.readBoolean();
        fire = buf.readBoolean();
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(air);
        buf.writeBoolean(aqua);
        buf.writeBoolean(earth);
        buf.writeBoolean(fire);
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                Minecraft.getInstance().player.getCapability(CapabilityRegistry.ARS_ELEMENTAL_SET_CAPABILITY).ifPresent(data -> {
                    data.setAir(this.air);
                    data.setAqua(this.aqua);
                    data.setEarth(this.earth);
                    data.setFire(this.fire);
                });
            });
        }
    }

}
