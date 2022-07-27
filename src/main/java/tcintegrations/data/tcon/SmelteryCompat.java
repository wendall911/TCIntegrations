package tcintegrations.data.tcon;

import java.util.Locale;

import lombok.Getter;
import lombok.experimental.Accessors;

import net.minecraft.world.item.Item;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import slimeknights.mantle.registration.object.FluidObject;

import slimeknights.tconstruct.smeltery.data.Byproduct;

import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.TCIntegrationsItems;

/** Enum holding all relevant smeltery compat */
public enum SmelteryCompat {

    MANASTEEL (TCIntegrationsItems.MOLTEN_MANASTEEL, ModIntegration.BOTANIA_MODID, Byproduct.IRON, Byproduct.GOLD),
    NEPTUNIUM (TCIntegrationsItems.MOLTEN_NEPTUNIUM, ModIntegration.AQUACULTURE_MODID),
    SOUL_STAINED_STEEL (TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL, ModIntegration.MALUM_MODID);

    @Getter
    private final String name = this.name().toLowerCase(Locale.US);
    private final FluidObject<? extends ForgeFlowingFluid> fluid;
    @Getter
    private final boolean isOre;
    @Accessors(fluent = true)
    @Getter
    private final boolean hasDust;
    @Getter
    private final Byproduct[] byproducts;
    @Getter
    private final String modid;

    SmelteryCompat(FluidObject<? extends ForgeFlowingFluid> fluid, String modid, boolean hasDust) {
        this.fluid = fluid;
        this.isOre = false;
        this.byproducts = new Byproduct[0];
        this.hasDust = hasDust;
        this.modid = modid;
    }

    /** Byproducts means its an ore, no byproucts are alloys */
    SmelteryCompat(FluidObject<? extends ForgeFlowingFluid> fluid, String modid, Byproduct... byproducts) {
        this.fluid = fluid;
        this.isOre = byproducts.length > 0;
        this.byproducts = byproducts;
        this.hasDust = true;
        this.modid = modid;
    }

    /** Gets teh fluid for this compat */
    public FluidObject<?> getFluid() {
        return fluid;
    }

    /** Gets teh bucket for this compat */
    public Item getBucket() {
        return fluid.asItem();
    }
}
