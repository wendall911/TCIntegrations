package tcintegrations.common.capabilities;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class BotaniaSet {

    private boolean terrestrial = false;
    private boolean greatFairy = false;
    private boolean alfheim = false;

    public static Tag writeNBT(Capability<BotaniaSet> capability, BotaniaSet instance, Direction side) {
        CompoundTag tag = new CompoundTag();

        tag.putBoolean("terrestrial", instance.hasTerrestrial());
        tag.putBoolean("great_fairy", instance.hasGreatFairy());
        tag.putBoolean("alfheim", instance.hasAlfheim());

        return tag;
    }

    public static void readNBT(Capability<BotaniaSet> capability, BotaniaSet instance, Direction side, Tag nbt) {
        instance.setTerrestrial(((CompoundTag) nbt).getBoolean("terrestrial"));
        instance.setGreatFairy(((CompoundTag) nbt).getBoolean("great_fairy"));
        instance.setAlfheim(((CompoundTag) nbt).getBoolean("alfheim"));
    }

    public void setTerrestrial(boolean terrestrial) {
        this.terrestrial = terrestrial;
    }

    public boolean hasTerrestrial() {
        return this.terrestrial;
    }

    public void setGreatFairy(boolean greatFairy) {
        this.greatFairy = greatFairy;
    }

    public boolean hasGreatFairy() {
        return this.greatFairy;
    }

    public boolean hasAlfheim() {
        return this.alfheim;
    }

    public void setAlfheim(boolean alfheim) {
        this.alfheim = alfheim;
    }

    public static class Provider implements ICapabilitySerializable<Tag> {
        @NotNull
        private final BotaniaSet instance;

        private final LazyOptional<BotaniaSet> handler;

        public Provider() {
            instance = new BotaniaSet();
            handler = LazyOptional.of(this::getInstance);
        }

        @NotNull
        @Override
        public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
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