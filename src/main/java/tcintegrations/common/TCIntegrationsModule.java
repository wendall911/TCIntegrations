package tcintegrations.common;

import net.minecraft.world.effect.MobEffect;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import slimeknights.mantle.registration.deferred.FluidDeferredRegister;

import slimeknights.tconstruct.common.registration.BlockDeferredRegisterExtension;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;

import tcintegrations.TCIntegrations;
import tcintegrations.data.integration.ModIntegration;

public abstract class TCIntegrationsModule {

    protected static BlockDeferredRegisterExtension METAL_BLOCK_REGISTRY;
    protected static ModifierDeferredRegister MODIFIERS_REGISTRY;
    protected static FluidDeferredRegister FLUID_REGISTRY;
    protected static FluidDeferredRegister BEYOND_EARTH_FLUID_REGISTRY;
    protected static DeferredRegister<MobEffect> BEYOND_EARTH_EFFECTS_REGISTRY;

    public static void initRegistries(IEventBus bus) {
        String dataGen = System.getenv("DATA_GEN");

        METAL_BLOCK_REGISTRY = new BlockDeferredRegisterExtension(TCIntegrations.MODID);
        MODIFIERS_REGISTRY = ModifierDeferredRegister.create(TCIntegrations.MODID);
        FLUID_REGISTRY = new FluidDeferredRegister(TCIntegrations.MODID);

        if (dataGen != null && dataGen.contains("all")) {
            BEYOND_EARTH_FLUID_REGISTRY = new FluidDeferredRegister(ModIntegration.BEYOND_EARTH_MODID);
            BEYOND_EARTH_FLUID_REGISTRY.register(bus);
        }

        METAL_BLOCK_REGISTRY.register(bus);
        MODIFIERS_REGISTRY.register(bus);
        FLUID_REGISTRY.register(bus);

        if (ModList.get().isLoaded(ModIntegration.BEYOND_EARTH_MODID)) {
            BEYOND_EARTH_EFFECTS_REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ModIntegration.BEYOND_EARTH_MODID);
            BEYOND_EARTH_EFFECTS_REGISTRY.register(bus);
        }
    }

}
