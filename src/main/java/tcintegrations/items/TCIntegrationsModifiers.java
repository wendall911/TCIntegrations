package tcintegrations.items;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.impl.BasicModifier;
import slimeknights.tconstruct.library.modifiers.modules.ModifierModule;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import tcintegrations.common.TCIntegrationsModule;
import tcintegrations.common.capabilities.EnergyModule;
import tcintegrations.common.capabilities.ToolEnergyHelper;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.modifiers.armor.*;
import tcintegrations.items.modifiers.energy.DischargeModifier;
import tcintegrations.items.modifiers.energy.EnergyDispatcherModifier;
import tcintegrations.items.modifiers.energy.EnergyRepairModifier;
import tcintegrations.items.modifiers.energy.SolarPanelHatModifier;
import tcintegrations.items.modifiers.tool.*;
import tcintegrations.items.modifiers.traits.*;

import static tcintegrations.util.ResourceLocationHelper.resource;

public class TCIntegrationsModifiers  extends TCIntegrationsModule {

    public static StaticModifier<ManaModifier> MANA_MODIFIER;
    public static StaticModifier<TerraModifier> TERRA_MODIFIER;
    public static StaticModifier<ElementalModifier> ELEMENTAL_MODIFIER;
    public static StaticModifier<TerrestrialModifier> TERRESTRIAL_MODIFIER;
    public static StaticModifier<GreatFairyModifier> GREAT_FAIRY_MODIFIER;
    public static StaticModifier<ModerateModifier> MODERATE_MODIFIER;
    public static StaticModifier<MechanicalArmModifier> MECHANICAL_ARM_MODIFIER;
    public static StaticModifier<WaterPowered> WATER_POWERED_MODIFIER;
    public static StaticModifier<PoseidonModifier> POSEIDON_MODIFIER;
    public static StaticModifier<SirenModifier> SIREN_MODIFIER;
    public static StaticModifier<ArsNouveauModifier> ARS_MODIFIER;
    public static StaticModifier<EnchantersShieldModifier> ENCHANTERS_SHIELD_MODIFIER;
    public static StaticModifier<FrontierCapModifier> FRONTIER_CAP_MODIFIER;
    public static StaticModifier<RoadrunnerModifier> ROADRUNNER_MODIFIER;
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
    public static StaticModifier<CheesyModifier> CHEESY_MODIFIER;
    public static StaticModifier<OxygenatedModifier> OXYGENATED_MODIFIER;
    public static StaticModifier<HellishModifier> HELLISH_MODIFIER;
    public static StaticModifier<AlfheimModifier> ALFHEIM_MODIFIER;
    public static StaticModifier<AlfModifier> ALF_MODIFIER;
    public static StaticModifier<KineticModifier> KINETIC_MODIFIER;
    public static StaticModifier<GlowUpModifier> GLOWUP_MODIFIER;
    public static StaticModifier<FlamedModifier> FLAMED_MODIFIER;
    public static StaticModifier<IcedModifier> ICED_MODIFIER;
    public static StaticModifier<ZappedModifier> ZAPPED_MODIFIER;
    public static StaticModifier<PhantasmalModifier> PHANTASMAL_MODIFIER;
    public static StaticModifier<DragonScalesModifier> DRAGON_SCALES_MODIFIER;
    public static StaticModifier<Modifier> ENERGY_HANDLER;
    public static StaticModifier<SolarPanelHatModifier> SOLAR_PANEL_HAT_MODIFIER;
    public static StaticModifier<EnergyRepairModifier> ENERGY_REPAIR_MODIFIER;
    public static StaticModifier<EnergyDispatcherModifier> ENERGY_DISPATCHER_MODIFIER;
    public static StaticModifier<DischargeModifier> DISCHARGE_MODIFIER;

    public static void init() {

        //Energy Modifiers
        ENERGY_HANDLER = MODIFIERS_REGISTRY.register(
                "energy_handler",
                () -> ModuleHookMap.builder()
                                   .addModule(new EnergyModule(ToolEnergyHelper.ENERGY_HELPER))
                                   .modifier()
                                   .levelDisplay(ModifierLevelDisplay.NO_LEVELS)
                                   .tooltipDisplay(BasicModifier.TooltipDisplay.NEVER)
                                   .build()
        );
        SOLAR_PANEL_HAT_MODIFIER = MODIFIERS_REGISTRY.register("solar_panel_hat", SolarPanelHatModifier::new);
        ENERGY_REPAIR_MODIFIER = MODIFIERS_REGISTRY.register("energy_repair", EnergyRepairModifier::new);
        ENERGY_DISPATCHER_MODIFIER = MODIFIERS_REGISTRY.register("energy_dispatcher", EnergyDispatcherModifier::new);
        DISCHARGE_MODIFIER = MODIFIERS_REGISTRY.register("discharge", DischargeModifier::new);


        if (ModIntegration.canLoad(ModIntegration.BOTANIA_MODID)) {
            MANA_MODIFIER = MODIFIERS_REGISTRY.register("mana", ManaModifier::new);
            TERRA_MODIFIER = MODIFIERS_REGISTRY.register("terra", TerraModifier::new);
            ELEMENTAL_MODIFIER = MODIFIERS_REGISTRY.register("elemental", ElementalModifier::new);
            TERRESTRIAL_MODIFIER = MODIFIERS_REGISTRY.register("terrestrial", TerrestrialModifier::new);
            GREAT_FAIRY_MODIFIER = MODIFIERS_REGISTRY.register("great_fairy", GreatFairyModifier::new);
        }

        if (ModIntegration.canLoad(ModIntegration.CREATE_MODID)) {
            MECHANICAL_ARM_MODIFIER = MODIFIERS_REGISTRY.register("mechanical_arm", MechanicalArmModifier::new);
            ModifierModule.LOADER.register(resource("engineers_goggles"), EngineersGogglesModifier.INSTANCE.getLoader());
        }

        if (ModIntegration.canLoad(ModIntegration.AQUACULTURE_MODID)) {
            WATER_POWERED_MODIFIER = MODIFIERS_REGISTRY.register("water_powered", WaterPowered::new);
            POSEIDON_MODIFIER = MODIFIERS_REGISTRY.register("poseidon", PoseidonModifier::new);
            SIREN_MODIFIER = MODIFIERS_REGISTRY.register("siren", SirenModifier::new);
        }

        if (ModIntegration.canLoad(ModIntegration.ARS_MODID)) {
            ARS_MODIFIER = MODIFIERS_REGISTRY.register("ars_nouveau", ArsNouveauModifier::new);
            ENCHANTERS_SHIELD_MODIFIER = MODIFIERS_REGISTRY.register("enchanters_shield", EnchantersShieldModifier::new);
        }

        if (ModIntegration.canLoad(ModIntegration.ALEX_MODID)) {
            ROADRUNNER_MODIFIER = MODIFIERS_REGISTRY.register("roadrunner", RoadrunnerModifier::new);
            FRONTIER_CAP_MODIFIER = MODIFIERS_REGISTRY.register("frontier_cap", FrontierCapModifier::new);
            TURTLE_SHELL_MODIFIER = MODIFIERS_REGISTRY.register("turtle_shell", TurtleShellModifier::new);
            BISON_FUR_MODIFIER = MODIFIERS_REGISTRY.register("bison_fur", BisonFurModifier::new);
            SHIELD_OF_THE_DEEP_MODIFIER = MODIFIERS_REGISTRY.register("shield_of_the_deep", ShieldOfTheDeepModifier::new);
            MOSQUITO_MODIFIER = MODIFIERS_REGISTRY.register("mosquito", MosquitoModifier::new);
            CROCODILE_MODIFIER = MODIFIERS_REGISTRY.register("crocodile", CrocodileModifier::new);
        }

        if (ModIntegration.canLoad(ModIntegration.MALUM_MODID)) {
            SOUL_STAINED_MODIFIER = MODIFIERS_REGISTRY.register("soul_stained", SoulStained::new);
        }

        if (ModIntegration.canLoad(ModIntegration.UNDERGARDEN_MODID)) {
            MASTICATE_MODIFIER = MODIFIERS_REGISTRY.register("masticate", MasticateModifier::new);
            UTHERIUM_MODIFIER = MODIFIERS_REGISTRY.register("utherium", UtheriumModifier::new);
            FROSTSTEEL_MODIFIER = MODIFIERS_REGISTRY.register("froststeel", FroststeelModifier::new);
            FORGOTTEN_MODIFIER = MODIFIERS_REGISTRY.register("forgotten", ForgottenModifier::new);
        }

        if (ModIntegration.canLoad(ModIntegration.BYG_MODID)) {
            HELLISH_MODIFIER = MODIFIERS_REGISTRY.register("hellish", HellishModifier::new);
        }

        if (ModIntegration.canLoad(ModIntegration.IE_MODID)) {
            ModifierModule.LOADER.register(resource("multivision"), MultiVisionModifier.INSTANCE.getLoader());
        }

        if (ModIntegration.canLoad(ModIntegration.MEKANISM_MODID)) {
            KINETIC_MODIFIER = MODIFIERS_REGISTRY.register("kinetic", KineticModifier::new);
            GLOWUP_MODIFIER = MODIFIERS_REGISTRY.register("glowup", GlowUpModifier::new);
        }

        if (ModIntegration.canLoad(ModIntegration.MYTHIC_BOTANY_MODID)) {
            ALFHEIM_MODIFIER = MODIFIERS_REGISTRY.register("alfheim", AlfheimModifier::new);
            ALF_MODIFIER = MODIFIERS_REGISTRY.register("alf", AlfModifier::new);
        }

        if (ModIntegration.canLoad(ModIntegration.IFD_MODID)) {
            FLAMED_MODIFIER = MODIFIERS_REGISTRY.register("flamed", FlamedModifier::new);
            ICED_MODIFIER = MODIFIERS_REGISTRY.register("iced", IcedModifier::new);
            ZAPPED_MODIFIER = MODIFIERS_REGISTRY.register("zapped", ZappedModifier::new);
            PHANTASMAL_MODIFIER = MODIFIERS_REGISTRY.register("phantasmal", PhantasmalModifier::new);
            DRAGON_SCALES_MODIFIER = MODIFIERS_REGISTRY.register("dragonscales", DragonScalesModifier::new);
        }

        // Ad Astra / Beyond Earth, etc. and whatever other space clones
        CHEESY_MODIFIER = MODIFIERS_REGISTRY.register("cheesy", CheesyModifier::new);
        OXYGENATED_MODIFIER = MODIFIERS_REGISTRY.register("oxygenated", OxygenatedModifier::new);

        MODERATE_MODIFIER = MODIFIERS_REGISTRY.register("moderate", ModerateModifier::new);
    }

}
