package tcintegrations.items;

import net.minecraftforge.fml.ModList;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;
import tcintegrations.common.TCIntegrationsModule;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.items.armor.modifiers.EngineersGogglesModifier;
import tcintegrations.items.armor.modifiers.GreatFairyModifier;
import tcintegrations.items.armor.modifiers.TerrestrialModifier;
import tcintegrations.items.tool.modifiers.ElementalModifier;
import tcintegrations.items.tool.modifiers.LivingwoodModifier;
import tcintegrations.items.tool.modifiers.ManaItemModifier;
import tcintegrations.items.tool.modifiers.MechanicalArmModifier;
import tcintegrations.items.tool.modifiers.ModerateModifier;
import tcintegrations.items.tool.modifiers.TerraModifier;

public class TCIntegrationsModifiers  extends TCIntegrationsModule {

    public static StaticModifier<ManaItemModifier> MANA_MODIFIER;
    public static StaticModifier<LivingwoodModifier> LIVINGWOOD_MODIFIER;
    public static StaticModifier<TerraModifier> TERRA_MODIFIER;
    public static StaticModifier<ElementalModifier> ELEMENTAL_MODIFIER;
    public static StaticModifier<TerrestrialModifier> TERRESTRIAL_MODIFIER;
    public static StaticModifier<GreatFairyModifier> GREAT_FAIRY_MODIFIER;
    public static StaticModifier<ModerateModifier> MODERATE_MODIFIER;
    public static StaticModifier<MechanicalArmModifier> MECHANICAL_ARM_MODIFIER;
    public static StaticModifier<EngineersGogglesModifier> ENGINEERS_GOGGLES_MODIFIER;

    public static void init() {
        if (ModList.get().isLoaded(ModIntegration.BOTANIA_MODID)) {
            MANA_MODIFIER = MODIFIERS_REGISTRY.register("mana", ManaItemModifier::new);
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

        MODERATE_MODIFIER = MODIFIERS_REGISTRY.register("moderate", ModerateModifier::new);
    }

}
