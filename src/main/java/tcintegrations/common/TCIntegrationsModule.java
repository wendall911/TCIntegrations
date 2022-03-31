package tcintegrations.common;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;

import slimeknights.mantle.registration.deferred.FluidDeferredRegister;

import slimeknights.tconstruct.common.registration.BlockDeferredRegisterExtension;
import slimeknights.tconstruct.library.modifiers.Modifier;

import tcintegrations.TCIntegrations;

public abstract class TCIntegrationsModule {

    protected static BlockDeferredRegisterExtension METAL_BLOCK_REGISTRY;
    protected static DeferredRegister<Modifier> MODIFIERS_REGISTRY;
    protected static FluidDeferredRegister FLUID_REGISTRY;

    public static void initRegistries(IEventBus bus) {
        METAL_BLOCK_REGISTRY = new BlockDeferredRegisterExtension(TCIntegrations.MODID);
        MODIFIERS_REGISTRY = DeferredRegister.create(Modifier.class, TCIntegrations.MODID);
        FLUID_REGISTRY = new FluidDeferredRegister(TCIntegrations.MODID);

        METAL_BLOCK_REGISTRY.register(bus);
        MODIFIERS_REGISTRY.register(bus);
        FLUID_REGISTRY.register(bus);
    }

}
