package tcintegrations.client.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.runtime.IIngredientManager;
import mezz.jei.api.runtime.IJeiRuntime;

import java.util.Collections;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.registries.tags.ITag;

import tcintegrations.data.tcon.SmelteryCompat;

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
    }

    private static void removeFluid(IIngredientManager manager, Fluid fluid, Item bucket) {
        manager.removeIngredientsAtRuntime(ForgeTypes.FLUID_STACK, Collections.singleton(new FluidStack(fluid, FluidType.BUCKET_VOLUME)));
        manager.removeIngredientsAtRuntime(VanillaTypes.ITEM_STACK, Collections.singleton(new ItemStack(bucket)));
    }

}
