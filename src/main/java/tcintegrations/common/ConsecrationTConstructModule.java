package tcintegrations.common;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import com.illusivesoulworks.consecration.api.ConsecrationApi;
import com.illusivesoulworks.consecration.api.ConsecrationImc;
import com.illusivesoulworks.consecration.common.integration.AbstractCompatibilityModule;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.fml.InterModComms;

import slimeknights.tconstruct.library.materials.definition.MaterialVariantId;
import slimeknights.tconstruct.library.tools.nbt.MaterialIdNBT;

import tcintegrations.TCIntegrations;
import tcintegrations.data.integration.ModIntegration;

// Implemented from https://github.com/illusivesoulworks/consecration/blob/1.18.x/src/main/java/top/theillusivec4/consecration/common/integration/TConstructModule.java

public class ConsecrationTConstructModule extends AbstractCompatibilityModule {

    private static final List<AbstractCompatibilityModule> ACTIVE_MODULES = new ArrayList<>();
    private static final Map<String, Class<? extends AbstractCompatibilityModule>> MODULES = new HashMap<>();

    static {
        MODULES.put(ModIntegration.TCON_MODID, ConsecrationTConstructModule.class);

        MODULES.forEach((id, module) -> {
            try {
                ACTIVE_MODULES.add(module.getDeclaredConstructor().newInstance());
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                TCIntegrations.LOGGER.error("Error adding module for mod " + id);
            }
        });
    }

    public static void setup() {
        ACTIVE_MODULES.forEach(AbstractCompatibilityModule::enqueueImc);
    }

    @Override
    public void enqueueImc() {
        TCIntegrations.LOGGER.warn("sending enqueueImc to consecration");

        InterModComms.sendTo(ModIntegration.CONSECRATION_MODID, ConsecrationImc.HOLY_ATTACK.getId(),
            () -> (BiFunction<LivingEntity, DamageSource, Boolean>) (livingEntity, damageSource) -> {
                Entity source = damageSource.getDirectEntity();

                if (source instanceof LivingEntity) {
                    ItemStack stack = ((LivingEntity) source).getMainHandItem();
                    MaterialIdNBT nbt = MaterialIdNBT.from(stack);

                    for (MaterialVariantId material : nbt.getMaterials()) {
                        if (ConsecrationApi.getInstance().isHolyMaterial(material.getId().getPath())) {
                            return true;
                        }
                    }
                }

                return false;
            });
    }

}
