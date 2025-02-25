package tcintegrations.client.integration.jei;

import java.util.Collections;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.tags.ITag;

import tcintegrations.data.integration.ModIntegration;
import tcintegrations.data.tcon.SmelteryCompat;
import tcintegrations.items.TCIntegrationsItems;

import static tcintegrations.util.ResourceLocationHelper.location;
import static tcintegrations.util.ResourceLocationHelper.resource;
import static tcintegrations.util.TagHelper.getTag;

@JeiPlugin
public class JEIPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return resource("jei_plugin");
    }

    @Override
    public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
        IIngredientManager manager = jeiRuntime.getIngredientManager();

        for (SmelteryCompat compat : SmelteryCompat.values()) {
            ITag<Item> ingot = getTag(location("forge", "ingots/" + compat.getName()));

            if (ingot.isEmpty()) {
                try {
                    removeFluid(manager, compat.getFluid().get(), compat.getBucket());
                }
                catch (NullPointerException ignored) {}
            }
        }

        if (!ModIntegration.canLoad(ModIntegration.BOTANIA_MODID)) {
            removeFluid(manager, TCIntegrationsItems.MOLTEN_MANASTEEL.get(), TCIntegrationsItems.MOLTEN_MANASTEEL.getBucket());
        }

        if (!ModIntegration.canLoad(ModIntegration.AQUACULTURE_MODID)) {
            removeFluid(manager, TCIntegrationsItems.MOLTEN_NEPTUNIUM.get(), TCIntegrationsItems.MOLTEN_NEPTUNIUM.getBucket());
        }

        if (!ModIntegration.canLoad(ModIntegration.MALUM_MODID)) {
            removeFluid(manager, TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL.get(), TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL.getBucket());
        }

        if (!ModIntegration.canLoad(ModIntegration.UNDERGARDEN_MODID)) {
            removeFluid(manager, TCIntegrationsItems.MOLTEN_CLOGGRUM.get(), TCIntegrationsItems.MOLTEN_CLOGGRUM.getBucket());
            removeFluid(manager, TCIntegrationsItems.MOLTEN_FROSTSTEEL.get(), TCIntegrationsItems.MOLTEN_FROSTSTEEL.getBucket());
            removeFluid(manager, TCIntegrationsItems.MOLTEN_FORGOTTEN_METAL.get(), TCIntegrationsItems.MOLTEN_FORGOTTEN_METAL.getBucket());
        }

        if (!ModIntegration.canLoad(ModIntegration.IFD_MODID)) {
            removeFluid(manager, TCIntegrationsItems.MOLTEN_DRAGONSTEEL_FIRE.get(), TCIntegrationsItems.MOLTEN_DRAGONSTEEL_FIRE.getBucket());
            removeFluid(manager, TCIntegrationsItems.MOLTEN_DRAGONSTEEL_ICE.get(), TCIntegrationsItems.MOLTEN_DRAGONSTEEL_ICE.getBucket());
            removeFluid(manager, TCIntegrationsItems.MOLTEN_DRAGONSTEEL_LIGHTNING.get(), TCIntegrationsItems.MOLTEN_DRAGONSTEEL_LIGHTNING.getBucket());
        }

        if (!ModIntegration.canLoad(ModIntegration.ARS_MODID)) {
            removeFluid(manager, TCIntegrationsItems.MOLTEN_SOURCE_GEM.get(), TCIntegrationsItems.MOLTEN_SOURCE_GEM.getBucket());
        }
    }

    private static void removeFluid(IIngredientManager manager, Fluid fluid, Item bucket) {
        manager.removeIngredientsAtRuntime(ForgeTypes.FLUID_STACK, Collections.singleton(new FluidStack(fluid, FluidType.BUCKET_VOLUME)));
        manager.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, Collections.singleton(new ItemStack(bucket)));
    }

}
