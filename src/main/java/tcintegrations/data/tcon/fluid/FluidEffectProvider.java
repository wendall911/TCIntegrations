package tcintegrations.data.tcon.fluid;

import com.hollingsworth.arsnouveau.setup.registry.ModPotions;

import com.sammy.malum.registry.common.MobEffectRegistry;

import net.minecraft.data.PackOutput;
import net.minecraft.world.effect.MobEffects;

import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;

import quek.undergarden.registry.UGEffects;

import slimeknights.mantle.data.predicate.entity.LivingEntityPredicate;
import slimeknights.tconstruct.common.TinkerDamageTypes;
import slimeknights.tconstruct.library.data.tinkering.AbstractFluidEffectProvider;
import slimeknights.tconstruct.library.modifiers.fluid.FluidEffect;
import slimeknights.tconstruct.library.modifiers.fluid.FluidMobEffect;
import slimeknights.tconstruct.library.modifiers.fluid.TimeAction;

import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.TCIntegrationsItems;

public class FluidEffectProvider extends AbstractFluidEffectProvider {

    public FluidEffectProvider(PackOutput packOutput, String modId) {
        super(packOutput, modId);
    }

    @Override
    public String getName() {
        return "TCIntegrations - TCon Fluid Effect Provider";
    }

    @Override
    protected void addFluids() {
        addMetal(TCIntegrationsItems.MOLTEN_MANASTEEL)
            .addCondition(new ModLoadedCondition(ModIntegration.BOTANIA_MODID))
            .magicDamage(2F)
            .addEffect(FluidMobEffect.builder().effect(ModPotions.RECOVERY_EFFECT.get(), 20 * 6, 2), TimeAction.SET);
        addMetal(TCIntegrationsItems.MOLTEN_NEPTUNIUM)
            .addCondition(new ModLoadedCondition(ModIntegration.AQUACULTURE_MODID))
            .fireDamage(2F)
            .addDamage(LivingEntityPredicate.WATER_SENSITIVE, 2F, TinkerDamageTypes.WATER)
            .addEntityEffect(FluidEffect.EXTINGUISH_FIRE);
        addGem(TCIntegrationsItems.MOLTEN_SOURCE_GEM)
            .addCondition(new ModLoadedCondition(ModIntegration.ARS_MODID))
            .addEffect(FluidMobEffect.builder().effect(ModPotions.RECOVERY_EFFECT.get(), 20 * 6, 2), TimeAction.SET);
        addMetal(TCIntegrationsItems.MOLTEN_SOUL_STAINED_STEEL)
            .addCondition(new ModLoadedCondition(ModIntegration.MALUM_MODID))
            .magicDamage(2F)
            .addEffect(FluidMobEffect.builder().effect(MobEffectRegistry.GLUTTONY.get(), 20 * 10, 2), TimeAction.SET);
        addMetal(TCIntegrationsItems.MOLTEN_CLOGGRUM)
            .addCondition(new ModLoadedCondition(ModIntegration.UNDERGARDEN_MODID))
            .addEffect(FluidMobEffect.builder().effect(UGEffects.GOOEY.get(), 20 * 5, 2), TimeAction.SET);
        addMetal(TCIntegrationsItems.MOLTEN_FROSTSTEEL)
            .addCondition(new ModLoadedCondition(ModIntegration.UNDERGARDEN_MODID))
            .addEffect(FluidMobEffect.builder().effect(UGEffects.CHILLY.get(), 20 * 4, 2), TimeAction.SET);
        addMetal(TCIntegrationsItems.MOLTEN_FORGOTTEN_METAL)
            .addCondition(new ModLoadedCondition(ModIntegration.UNDERGARDEN_MODID))
            .magicDamage(2F)
            .addEffect(FluidMobEffect.builder().effect(MobEffects.ABSORPTION, 20 * 20, 2), TimeAction.SET);
    }

}
