package tcintegrations.common;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.CreativeModeTab;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import slimeknights.mantle.registration.deferred.FluidDeferredRegister;

import slimeknights.mantle.registration.deferred.SynchronizedDeferredRegister;
import slimeknights.tconstruct.common.registration.BlockDeferredRegisterExtension;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;

import tcintegrations.TCIntegrations;

public abstract class TCIntegrationsModule {

    protected static BlockDeferredRegisterExtension METAL_BLOCK_REGISTRY;
    protected static ModifierDeferredRegister MODIFIERS_REGISTRY;
    protected static FluidDeferredRegister FLUID_REGISTRY;
    protected static DeferredRegister<MobEffect> EFFECTS_REGISTRY;
    protected static final SynchronizedDeferredRegister<CreativeModeTab> CREATIVE_TABS = SynchronizedDeferredRegister.create(Registries.CREATIVE_MODE_TAB, TCIntegrations.MODID);

    public static void initRegistries(IEventBus bus) {
        METAL_BLOCK_REGISTRY = new BlockDeferredRegisterExtension(TCIntegrations.MODID);
        MODIFIERS_REGISTRY = ModifierDeferredRegister.create(TCIntegrations.MODID);
        FLUID_REGISTRY = new FluidDeferredRegister(TCIntegrations.MODID);

        METAL_BLOCK_REGISTRY.register(bus);
        MODIFIERS_REGISTRY.register(bus);
        FLUID_REGISTRY.register(bus);

        EFFECTS_REGISTRY = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, TCIntegrations.MODID);
        EFFECTS_REGISTRY.register(bus);

        CREATIVE_TABS.register(bus);
    }

}
