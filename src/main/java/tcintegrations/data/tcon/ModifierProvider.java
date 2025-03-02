package tcintegrations.data.tcon;

import net.minecraft.data.PackOutput;

import net.minecraftforge.common.crafting.conditions.IConditionBuilder;

import slimeknights.tconstruct.library.data.tinkering.AbstractModifierProvider;
import slimeknights.tconstruct.library.modifiers.modules.behavior.RepairModule;
import slimeknights.tconstruct.library.modifiers.modules.build.ModifierSlotModule;
import slimeknights.tconstruct.library.modifiers.util.ModifierLevelDisplay;
import slimeknights.tconstruct.library.tools.SlotType;

import tcintegrations.data.integration.ModIntegration;
import tcintegrations.data.tcon.material.TciModifierIds;
import tcintegrations.items.TCIntegrationsModifiers;
import tcintegrations.items.modifiers.armor.EngineersGogglesModifier;
import tcintegrations.items.modifiers.armor.MultiVisionModifier;

public class ModifierProvider extends AbstractModifierProvider implements IConditionBuilder {

    public ModifierProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    public String getName() {
        return "TCIntegrations - TCon Modifiers";
    }

    @Override
    protected void addModifiers() {
        buildModifier(TciModifierIds.livingwood)
            .addModule(RepairModule.builder().eachLevel(0.75F));
        buildModifier(TciModifierIds.engineersGoggles, modLoaded(ModIntegration.CREATE_MODID))
            .levelDisplay(ModifierLevelDisplay.NO_LEVELS)
            .addModule(EngineersGogglesModifier.INSTANCE);
        buildModifier(TCIntegrationsModifiers.ARS_MODIFIER.getId())
            .levelDisplay(new ModifierLevelDisplay.UniqueForLevels(3))
            .addModule(new ModifierSlotModule(SlotType.UPGRADE));
        buildModifier(TciModifierIds.multiVision, modLoaded(ModIntegration.IE_MODID))
            .levelDisplay(ModifierLevelDisplay.NO_LEVELS)
            .addModule(MultiVisionModifier.INSTANCE);
    }

}
