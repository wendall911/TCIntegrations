package tcintegrations.data.tcon;

import net.minecraft.data.DataGenerator;

import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.modifiers.modules.behavior.RepairModule;
import slimeknights.tconstruct.library.modifiers.modules.build.ModifierRequirementsModule;
import slimeknights.tconstruct.library.modifiers.modules.build.ModifierSlotModule;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;

import slimeknights.tconstruct.library.tools.SlotType;
import tcintegrations.data.integration.ModIntegration;
import tcintegrations.data.tcon.material.TciModifierIds;
import tcintegrations.items.TCIntegrationsModifiers;
import tcintegrations.items.modifiers.armor.EngineersGogglesModifier;
import tcintegrations.items.modifiers.armor.MultiVisionModifier;

public class ModifierProvider extends AbstractModifierProvider implements IConditionBuilder {

    public ModifierProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    public String getName() {
        return "TCIntegrations - TCon Modifiers";
    }

    @Override
    protected void addModifiers() {
        buildModifier(TciModifierIds.livingwood)
            .addModule(RepairModule.builder().eachLevel(0.75F));
        buildModifier(TciModifierIds.engineersGoggles, modLoaded(ModIntegration.IE_MODID))
            .addModule(EngineersGogglesModifier.INSTANCE)
            .addModule(new ModifierSlotModule(SlotType.ABILITY, 1));
        buildModifier(TCIntegrationsModifiers.ARS_MODIFIER.getId())
            .levelDisplay(new ModifierLevelDisplay.UniqueForLevels(3))
            .addModule(new ModifierSlotModule(SlotType.UPGRADE));
        buildModifier(TciModifierIds.multiVision, modLoaded(ModIntegration.IE_MODID))
            .levelDisplay(ModifierLevelDisplay.NO_LEVELS)
            .addModule(MultiVisionModifier.INSTANCE);
        buildModifier(TCIntegrationsModifiers.ALF_MODIFIER.getId(), modLoaded(ModIntegration.MYTHIC_BOTANY_MODID))
            .addModule(ModifierRequirementsModule.builder().requireModifier(TCIntegrationsModifiers.TERRA_MODIFIER.getId(), 1)
            .modifierKey(TCIntegrationsModifiers.ALF_MODIFIER.getId()).build());
        buildModifier(TCIntegrationsModifiers.ALFHEIM_MODIFIER.getId(), modLoaded(ModIntegration.MYTHIC_BOTANY_MODID))
            .addModule(ModifierRequirementsModule.builder().requireModifier(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId(), 1)
            .modifierKey(TCIntegrationsModifiers.ALFHEIM_MODIFIER.getId()).build());
    }

}
