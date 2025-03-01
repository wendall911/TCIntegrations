package tcintegrations.common.capabilities;

import org.jetbrains.annotations.NotNull;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

public class ArsElementalSet {

    private boolean air = false;
    private boolean aqua = false;
    private boolean earth = false;
    private boolean fire = false;

   public static Tag writeNBT(Capability<ArsElementalSet> capability, ArsElementalSet instance, Direction side) {
        CompoundTag tag = new CompoundTag();

        tag.putBoolean("air", instance.hasAir());
        tag.putBoolean("aqua", instance.hasAqua());
        tag.putBoolean("earth", instance.hasEarth());
        tag.putBoolean("fire", instance.hasFire());

        return tag;
    }

    public static void readNBT(Capability<ArsElementalSet> capability, ArsElementalSet instance, Direction side, Tag nbt) {
        instance.setAir(((CompoundTag) nbt).getBoolean("air"));
        instance.setAqua(((CompoundTag) nbt).getBoolean("aqua"));
        instance.setEarth(((CompoundTag) nbt).getBoolean("earth"));
        instance.setFire(((CompoundTag) nbt).getBoolean("fire"));
    }

    public void setAir(boolean air) {
        this.air = air;
    }

    public boolean hasAir() {
        return this.air;
    }

    public void setAqua(boolean aqua) {
        this.aqua = aqua;
    }

    public boolean hasAqua() {
        return this.aqua;
    }

    public void setEarth(boolean earth) {
        this.earth = earth;
    }

    public boolean hasEarth() {
        return this.earth;
    }

    public void setFire(boolean fire) {
        this.fire = fire;
    }

    public boolean hasFire() {
        return this.fire;
    }

    public static class Provider implements ICapabilitySerializable<Tag> {
        @NotNull
        private final ArsElementalSet instance;

        private final LazyOptional<ArsElementalSet> handler;

        public Provider() {
            instance = new ArsElementalSet();
            handler = LazyOptional.of(this::getInstance);
        }

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
            if (cap == null) return LazyOptional.empty();

            return CapabilityRegistry.ARS_ELEMENTAL_SET_CAPABILITY.orEmpty(cap, handler);
        }

        public @NotNull ArsElementalSet getInstance() {
            return instance;
        }

        @Override
        public Tag serializeNBT() {
            return ArsElementalSet.writeNBT(CapabilityRegistry.ARS_ELEMENTAL_SET_CAPABILITY, instance, null);
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            ArsElementalSet.readNBT(CapabilityRegistry.ARS_ELEMENTAL_SET_CAPABILITY, instance, null, nbt);
        }
    }

}
