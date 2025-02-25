package tcintegrations.data.tcon;

import java.util.Locale;

import lombok.Getter;

import net.minecraft.world.item.Item;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import slimeknights.mantle.registration.object.FluidObject;

import tcintegrations.data.tcon.material.MaterialIds;
import tcintegrations.items.TCIntegrationsItems;

/**
 * Ennum for all generic (multi-mod) materials that need smeltery support
 */
public enum SmelteryCompat {

    DESH (TCIntegrationsItems.MOLTEN_DESH, MaterialIds.desh.getPath()),
    CALORITE (TCIntegrationsItems.MOLTEN_CALORITE, MaterialIds.calorite.getPath()),
    OSTRUM (TCIntegrationsItems.MOLTEN_OSTRUM,MaterialIds.ostrum.getPath());

    @Getter
    private final String name = this.name().toLowerCase(Locale.US);
    private final FluidObject<? extends ForgeFlowingFluid> fluid;
    @Getter
    private final String identifier;

    SmelteryCompat(FluidObject<? extends ForgeFlowingFluid> fluid, String identifier) {
        this.fluid = fluid;
        this.identifier = identifier;
    }

    public FluidObject<?> getFluid() {
        return fluid;
    }

    public Item getBucket() {
        return fluid.asItem();
    }

}
