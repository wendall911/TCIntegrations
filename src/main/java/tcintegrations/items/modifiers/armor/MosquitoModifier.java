package tcintegrations.items.modifiers.armor;

import java.util.List;

import com.github.alexthe666.alexsmobs.misc.AMSoundRegistry;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.phys.AABB;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent;

import slimeknights.tconstruct.library.modifiers.impl.TotalArmorLevelModifier;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability.TinkerDataKey;
import slimeknights.tconstruct.library.tools.helper.ModifierUtil;

import tcintegrations.TCIntegrations;

public class MosquitoModifier extends TotalArmorLevelModifier {

    private static final TinkerDataKey<Integer> MOSQUITO = TinkerDataKey.of(new ResourceLocation(TCIntegrations.MODID, "mosquito"));

    public MosquitoModifier() {
        super(MOSQUITO);
        MinecraftForge.EVENT_BUS.addListener(MosquitoModifier::onLivingTick);
    }

    @Override
    public Component getDisplayName(int level) {
        return super.getDisplayName();
    }

    private static void onLivingTick(LivingEvent.LivingUpdateEvent event) {
        LivingEntity entity = event.getEntityLiving();

        if (!entity.isSpectator() && (entity.tickCount & 1) == 0) {
            int level = ModifierUtil.getTotalModifierLevel(entity, MOSQUITO);

            if (level > 0) {
                applyAnnoyance(entity, level);
            }
        }
    }

    public static void applyAnnoyance(LivingEntity entity, int level) {
        double x = entity.getX();
        double y = entity.getY();
        double z = entity.getZ();
        float range = 3F + 1F * level;

        List<LivingEntity> entities = entity.level.getEntitiesOfClass(LivingEntity.class, new AABB(x - range, y - range, z - range, x + range, y + range, z + range));

        if (!entities.isEmpty() && entity.tickCount % 100 == 0) {
            entity.playSound(AMSoundRegistry.MOSQUITO_LOOP, 1.0F, 1.0F);
        }

        for (LivingEntity livingEntity : entities) {
            if (!livingEntity.equals(entity) && entity.tickCount % 20 == 0) {
                for (int i = 0; i < 3; i++) {
                    livingEntity.level.addParticle(ParticleTypes.CRIMSON_SPORE, livingEntity.getRandomX(1.0), livingEntity.getRandomY(), livingEntity.getRandomZ(1.0), 0, 0, 0);
                }
            }
        }
    }

}
