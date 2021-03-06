package tcintegrations.items;

import net.minecraftforge.fml.ModList;

import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import tcintegrations.common.TCIntegrationsModule;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.modifiers.armor.ArsNouveauModifier;
import tcintegrations.items.modifiers.armor.BisonFurModifier;
import tcintegrations.items.modifiers.armor.CrocodileModifier;
import tcintegrations.items.modifiers.armor.EnchantersShieldModifier;
import tcintegrations.items.modifiers.armor.EngineersGogglesModifier;
import tcintegrations.items.modifiers.armor.FrontierCapModifier;
import tcintegrations.items.modifiers.armor.GreatFairyModifier;
import tcintegrations.items.modifiers.armor.MasticateModifier;
import tcintegrations.items.modifiers.armor.MosquitoModifier;
import tcintegrations.items.modifiers.armor.PoseidonModifier;
import tcintegrations.items.modifiers.armor.RoadrunnerModifier;
import tcintegrations.items.modifiers.armor.ShieldOfTheDeepModifier;
import tcintegrations.items.modifiers.armor.TerrestrialModifier;
import tcintegrations.items.modifiers.armor.TurtleShellModifier;
import tcintegrations.items.modifiers.tool.ElementalModifier;
import tcintegrations.items.modifiers.tool.ForgottenModifier;
import tcintegrations.items.modifiers.tool.FroststeelModifier;
import tcintegrations.items.modifiers.tool.LivingwoodModifier;
import tcintegrations.items.modifiers.tool.UtheriumModifier;
import tcintegrations.items.modifiers.traits.ManaModifier;
import tcintegrations.items.modifiers.tool.MechanicalArmModifier;
import tcintegrations.items.modifiers.tool.ModerateModifier;
import tcintegrations.items.modifiers.tool.SirenModifier;
import tcintegrations.items.modifiers.tool.TerraModifier;
import tcintegrations.items.modifiers.traits.SoulStained;
import tcintegrations.items.modifiers.traits.WaterPowered;

public class TCIntegrationsModifiers  extends TCIntegrationsModule {

    public static StaticModifier<ManaModifier> MANA_MODIFIER;
    public static StaticModifier<LivingwoodModifier> LIVINGWOOD_MODIFIER;
    public static StaticModifier<TerraModifier> TERRA_MODIFIER;
    public static StaticModifier<ElementalModifier> ELEMENTAL_MODIFIER;
    public static StaticModifier<TerrestrialModifier> TERRESTRIAL_MODIFIER;
    public static StaticModifier<GreatFairyModifier> GREAT_FAIRY_MODIFIER;
    public static StaticModifier<ModerateModifier> MODERATE_MODIFIER;
    public static StaticModifier<MechanicalArmModifier> MECHANICAL_ARM_MODIFIER;
    public static StaticModifier<EngineersGogglesModifier> ENGINEERS_GOGGLES_MODIFIER;
    public static StaticModifier<WaterPowered> WATER_POWERED_MODIFIER;
    public static StaticModifier<PoseidonModifier> POSEIDON_MODIFIER;
    public static StaticModifier<SirenModifier> SIREN_MODIFIER;
    public static StaticModifier<ArsNouveauModifier> ARS_MODIFIER;
    public static StaticModifier<EnchantersShieldModifier> ENCHANTERS_SHIELD_MODIFIER;
    public static StaticModifier<RoadrunnerModifier> ROADRUNNER_MODIFIER;
    public static StaticModifier<FrontierCapModifier> FRONTIER_CAP_MODIFIER;
    public static StaticModifier<TurtleShellModifier> TURTLE_SHELL_MODIFIER;
    public static StaticModifier<BisonFurModifier> BISON_FUR_MODIFIER;
    public static StaticModifier<ShieldOfTheDeepModifier> SHIELD_OF_THE_DEEP_MODIFIER;
    public static StaticModifier<MosquitoModifier> MOSQUITO_MODIFIER;
    public static StaticModifier<CrocodileModifier> CROCODILE_MODIFIER;
    public static StaticModifier<SoulStained> SOUL_STAINED_MODIFIER;
    public static StaticModifier<MasticateModifier> MASTICATE_MODIFIER;
    public static StaticModifier<UtheriumModifier> UTHERIUM_MODIFIER;
    public static StaticModifier<FroststeelModifier> FROSTSTEEL_MODIFIER;
    public static StaticModifier<ForgottenModifier> FORGOTTEN_MODIFIER;

    public static void init() {
        if (ModList.get().isLoaded(ModIntegration.BOTANIA_MODID)) {
            MANA_MODIFIER = MODIFIERS_REGISTRY.register("mana", ManaModifier::new);
            LIVINGWOOD_MODIFIER = MODIFIERS_REGISTRY.register("livingwood", LivingwoodModifier::new);
            TERRA_MODIFIER = MODIFIERS_REGISTRY.register("terra", TerraModifier::new);
            ELEMENTAL_MODIFIER = MODIFIERS_REGISTRY.register("elemental", ElementalModifier::new);
            TERRESTRIAL_MODIFIER = MODIFIERS_REGISTRY.register("terrestrial", TerrestrialModifier::new);
            GREAT_FAIRY_MODIFIER = MODIFIERS_REGISTRY.register("great_fairy", GreatFairyModifier::new);
        }

        if (ModList.get().isLoaded(ModIntegration.CREATE_MODID)) {
            MECHANICAL_ARM_MODIFIER = MODIFIERS_REGISTRY.register("mechanical_arm", MechanicalArmModifier::new);
            ENGINEERS_GOGGLES_MODIFIER = MODIFIERS_REGISTRY.register("engineers_goggles", EngineersGogglesModifier::new);
        }

        if (ModList.get().isLoaded(ModIntegration.AQUACULTURE_MODID)) {
            WATER_POWERED_MODIFIER = MODIFIERS_REGISTRY.register("water_powered", WaterPowered::new);
            POSEIDON_MODIFIER = MODIFIERS_REGISTRY.register("poseidon", PoseidonModifier::new);
            SIREN_MODIFIER = MODIFIERS_REGISTRY.register("siren", SirenModifier::new);
        }

        if (ModList.get().isLoaded(ModIntegration.ARS_MODID)) {
            ARS_MODIFIER = MODIFIERS_REGISTRY.register("ars_nouveau", ArsNouveauModifier::new);
            ENCHANTERS_SHIELD_MODIFIER = MODIFIERS_REGISTRY.register("enchanters_shield", EnchantersShieldModifier::new);
        }

        if (ModList.get().isLoaded(ModIntegration.ALEX_MODID)) {
            ROADRUNNER_MODIFIER = MODIFIERS_REGISTRY.register("roadrunner", RoadrunnerModifier::new);
            FRONTIER_CAP_MODIFIER = MODIFIERS_REGISTRY.register("frontier_cap", FrontierCapModifier::new);
            TURTLE_SHELL_MODIFIER = MODIFIERS_REGISTRY.register("turtle_shell", TurtleShellModifier::new);
            BISON_FUR_MODIFIER = MODIFIERS_REGISTRY.register("bison_fur", BisonFurModifier::new);
            SHIELD_OF_THE_DEEP_MODIFIER = MODIFIERS_REGISTRY.register("shield_of_the_deep", ShieldOfTheDeepModifier::new);
            MOSQUITO_MODIFIER = MODIFIERS_REGISTRY.register("mosquito", MosquitoModifier::new);
            CROCODILE_MODIFIER = MODIFIERS_REGISTRY.register("crocodile", CrocodileModifier::new);
        }

        if (ModList.get().isLoaded(ModIntegration.MALUM_MODID)) {
            SOUL_STAINED_MODIFIER = MODIFIERS_REGISTRY.register("soul_stained", SoulStained::new);
        }

        if (ModList.get().isLoaded(ModIntegration.UNDERGARDEN_MODID)) {
            MASTICATE_MODIFIER = MODIFIERS_REGISTRY.register("masticate", MasticateModifier::new);
            UTHERIUM_MODIFIER = MODIFIERS_REGISTRY.register("utherium", UtheriumModifier::new);
            FROSTSTEEL_MODIFIER = MODIFIERS_REGISTRY.register("froststeel", FroststeelModifier::new);
            FORGOTTEN_MODIFIER = MODIFIERS_REGISTRY.register("forgotten", ForgottenModifier::new);
        }

        MODERATE_MODIFIER = MODIFIERS_REGISTRY.register("moderate", ModerateModifier::new);
    }

}
