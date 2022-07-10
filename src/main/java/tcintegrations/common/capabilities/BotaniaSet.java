package tcintegrations.common.capabilities;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class BotaniaSet {

    private boolean terrestrial = false;
    private boolean greatfairy = false;

    public static Tag writeNBT(Capability<BotaniaSet> capability, BotaniaSet instance, Direction side) {
        CompoundTag tag = new CompoundTag();

        tag.putBoolean("terrestrial", instance.hasTerrestrial());
        tag.putBoolean("greatfairy", instance.hasGreatFairy());

        return tag;
    }

    public static void readNBT(Capability<BotaniaSet> capability, BotaniaSet instance, Direction side, Tag nbt) {
        instance.setTerrestrial(((CompoundTag) nbt).getBoolean("terrestrial"));
        instance.setGreatfairy(((CompoundTag) nbt).getBoolean("greatfairy"));
    }

    public void setTerrestrial(boolean terrestrial) {
        this.terrestrial = terrestrial;
    }

    public boolean hasTerrestrial() {
        return this.terrestrial;
    }

    public void setGreatfairy(boolean greatfairy) {
        this.greatfairy = greatfairy;
    }

    public boolean hasGreatFairy() {
        return this.greatfairy;
    }

    public static class Provider implements ICapabilitySerializable<Tag> {
        @Nonnull
        private final BotaniaSet instance;

        private final LazyOptional<BotaniaSet> handler;

        public Provider() {
            instance = new BotaniaSet();
            handler = LazyOptional.of(this::getInstance);
        }

        @Nonnull
        @Override
        public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
            if (cap == null) return LazyOptional.empty();

            return CapabilityRegistry.BOTANIA_SET_CAPABILITY.orEmpty(cap, handler);
        }

        public BotaniaSet getInstance() {
            return instance;
        }

        @Override
        public Tag serializeNBT() {
            return BotaniaSet.writeNBT(CapabilityRegistry.BOTANIA_SET_CAPABILITY, instance, null);
        }

        @Override
        public void deserializeNBT(Tag nbt) {
            BotaniaSet.readNBT(CapabilityRegistry.BOTANIA_SET_CAPABILITY, instance, null, nbt);
        }

    }

}
