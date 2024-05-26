package tcintegrations.data.tcon;

import java.util.Locale;

import lombok.Getter;
import lombok.experimental.Accessors;

import net.minecraft.world.item.Item;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import slimeknights.mantle.registration.object.FluidObject;

import slimeknights.tconstruct.smeltery.data.Byproduct;

import tcintegrations.data.integration.ModIntegration;
import tcintegrations.data.tcon.material.MaterialIds;
import tcintegrations.items.TCIntegrationsItems;

/** Enum holding all relevant smeltery compat */
public enum SmelteryCompat {

    MANASTEEL (TCIntegrationsItems.MOLTEN_MANASTEEL, ModIntegration.BOTANIA_MODID, Byproduct.IRON, Byproduct.GOLD),
    NEPTUNIUM (TCIntegrationsItems.MOLTEN_NEPTUNIUM, ModIntegration.AQUACULTURE_MODID),
    SOUL_STAINED_STEEL (TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL, ModIntegration.MALUM_MODID),
    CLOGGRUM (TCIntegrationsItems.MOLTEN_CLOGGRUM, ModIntegration.UNDERGARDEN_MODID),
    FROSTSTEEL (TCIntegrationsItems.MOLTEN_FROSTSTEEL, ModIntegration.UNDERGARDEN_MODID),
    FORGOTTEN (TCIntegrationsItems.MOLTEN_FORGOTTEN, ModIntegration.UNDERGARDEN_MODID),
    PENDORITE_ALLOY (TCIntegrationsItems.MOLTEN_PENDORITE_ALLOY, ModIntegration.BYG_MODID),
    DESH (TCIntegrationsItems.MOLTEN_DESH, MaterialIds.desh.getPath()),
    CALORITE (TCIntegrationsItems.MOLTEN_CALORITE, MaterialIds.calorite.getPath()),
    OSTRUM (TCIntegrationsItems.MOLTEN_OSTRUM,MaterialIds.ostrum.getPath()),
    DRAGONSTEEL_FIRE (TCIntegrationsItems.MOLTEN_DRAGONSTEEL_FIRE, ModIntegration.IFD_MODID, Byproduct.IRON, Byproduct.GOLD),
    DRAGONSTEEL_ICE (TCIntegrationsItems.MOLTEN_DRAGONSTEEL_ICE, ModIntegration.IFD_MODID, Byproduct.IRON, Byproduct.GOLD),
    DRAGONSTEEL_LIGHTNING (TCIntegrationsItems.MOLTEN_DRAGONSTEEL_LIGHTNING, ModIntegration.IFD_MODID, Byproduct.IRON, Byproduct.GOLD);

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
    private final String identifier;

    SmelteryCompat(FluidObject<? extends ForgeFlowingFluid> fluid, String identifier, boolean hasDust) {
        this.fluid = fluid;
        this.isOre = false;
        this.byproducts = new Byproduct[0];
        this.hasDust = hasDust;
        this.identifier = identifier;
    }

    /** Byproducts means its an ore, no byproucts are alloys */
    SmelteryCompat(FluidObject<? extends ForgeFlowingFluid> fluid, String identifier, Byproduct... byproducts) {
        this.fluid = fluid;
        this.isOre = byproducts.length > 0;
        this.byproducts = byproducts;
        this.hasDust = true;
        this.identifier = identifier;
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
