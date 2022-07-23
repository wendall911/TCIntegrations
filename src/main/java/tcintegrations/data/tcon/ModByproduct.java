package tcintegrations.data.tcon;

import java.util.Locale;
import java.util.function.Supplier;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import net.minecraft.world.level.material.Fluid;

import slimeknights.tconstruct.library.data.recipe.IByproduct;
import slimeknights.tconstruct.library.recipe.FluidValues;

import tcintegrations.items.TCIntegrationsItems;

@RequiredArgsConstructor
public enum ModByproduct implements IByproduct {

    SOURCE_GEM ("source_gem", false, TCIntegrationsItems.MOLTEN_SOURCE_GEM, FluidValues.GEM);

    @Getter
    private final String name;
    @Getter
    private final boolean alwaysPresent;
    private final Supplier<? extends Fluid> fluidSupplier;
    @Getter
    private final int amount;

    ModByproduct(boolean alwaysPresent, Supplier<? extends Fluid> fluidSupplier) {
        this.name = name().toLowerCase(Locale.ROOT);
        this.alwaysPresent = alwaysPresent;
        this.fluidSupplier = fluidSupplier;
        this.amount = FluidValues.INGOT;
    }

    @Override
    public Fluid getFluid() {
        return fluidSupplier.get();
    }

}
