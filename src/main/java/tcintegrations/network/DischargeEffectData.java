package tcintegrations.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import tcintegrations.client.TCIntegrationsSE;

import javax.annotation.Nonnull;
import java.util.function.Supplier;

public class DischargeEffectData implements IData{
    public Vec3 fromVec;
    public Vec3 toVec;
    public DischargeEffectData(Vec3 fromVec, Vec3 toVec){
        this.fromVec = fromVec;
        this.toVec = toVec;
    }
    public DischargeEffectData(FriendlyByteBuf buf){
        fromVec = vecFromBytes(buf);
        toVec = vecFromBytes(buf);
    }
    public static void vecToBytes(FriendlyByteBuf buf, Vec3 vec){
        buf.writeDouble(vec.x);
        buf.writeDouble(vec.y);
        buf.writeDouble(vec.z);
    }
    public static Vec3 vecFromBytes(FriendlyByteBuf buf){
        return new Vec3(buf.readDouble(), buf.readDouble(), buf.readDouble());
    }
    @Override
    public void toBytes(FriendlyByteBuf buf) {
        vecToBytes(buf, fromVec);
        vecToBytes(buf, toVec);
    }
    public static void send(@Nonnull Level level, @Nonnull Vec3 fromPoint, @Nonnull Vec3 hitPoint){
        Vec3 midpoint = fromPoint.add(hitPoint).scale(0.5);
        double radius = 64.0 + fromPoint.distanceTo(midpoint);
        double radiusSqr = radius * radius;
        if (level instanceof ServerLevel) {
            ((ServerLevel) level).getPlayers(p -> p.distanceToSqr(midpoint) < radiusSqr)
                    .forEach(player -> NetworkHandler.INSTANCE.sendTo(
                            new DischargeEffectData(fromPoint, hitPoint),
                            player.connection.getConnection(),
                            NetworkDirection.PLAY_TO_CLIENT
                            )
                    );
        }
    }

    @Override
    public void process(Supplier<NetworkEvent.Context> ctx) {
        if (ctx.get().getDirection() == NetworkDirection.PLAY_TO_CLIENT) {
            ctx.get().enqueueWork(() -> {
                // show the effect of the discharge
                ClientLevel clientLevel = Minecraft.getInstance().level;
                Player cp = Minecraft.getInstance().player;
                if (clientLevel != null && cp != null) {
                    ParticleEngine particleEngine = Minecraft.getInstance().particleEngine;
                    double distance = fromVec.distanceTo(toVec);
                    int numParticles = Math.max(1, (int) Math.round(distance * 10)) + clientLevel.random.nextInt(10); // 10 particles per block + random jitter
                    for (int i = 0; i < numParticles; i++) {  // Distribute the particles evenly between fromVec and toVec
                        double t = (double) i / (numParticles - 1);
                        Vec3 particlePos = fromVec.scale(1 - t).add(toVec.scale(t));
                        double velocityFactor = clientLevel.random.nextFloat() * i / numParticles / 5;

                        //Add particles from fromVec to toVec to form a lightning bolt. Temporarily using vanilla particles.
                        particleEngine.createParticle(ParticleTypes.ELECTRIC_SPARK,
                                particlePos.x,
                                particlePos.y,
                                particlePos.z,
                                (clientLevel.random.nextFloat() - 0.5) * velocityFactor,
                                (clientLevel.random.nextFloat() - 0.5) * velocityFactor,
                                (clientLevel.random.nextFloat() - 0.5) * velocityFactor);
                    }
                    clientLevel.playSound(cp, cp.blockPosition(), TCIntegrationsSE.DISCHARGE.get(), cp.getSoundSource(), 1.0f, 1.0f);

                    //TCIntegrations.LOGGER.debug("DischargeEffectData: fromVec: " + fromVec + " toVec: " + toVec);
                }

            });
        }
        ctx.get().setPacketHandled(true);
    }
}
