package tcintegrations.data.tcon;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import slimeknights.tconstruct.library.data.tinkering.AbstractModifierTagProvider;

import tcintegrations.data.tcon.material.TciModifierIds;
import tcintegrations.items.TCIntegrationsModifiers;

import static slimeknights.tconstruct.common.TinkerTags.Modifiers.BOOT_UPGRADES;
import static slimeknights.tconstruct.common.TinkerTags.Modifiers.CHESTPLATE_UPGRADES;
import static slimeknights.tconstruct.common.TinkerTags.Modifiers.GENERAL_ABILITIES;
import static slimeknights.tconstruct.common.TinkerTags.Modifiers.GENERAL_ARMOR_UPGRADES;
import static slimeknights.tconstruct.common.TinkerTags.Modifiers.GENERAL_UPGRADES;
import static slimeknights.tconstruct.common.TinkerTags.Modifiers.HARVEST_UPGRADES;
import static slimeknights.tconstruct.common.TinkerTags.Modifiers.HELMET_UPGRADES;
import static slimeknights.tconstruct.common.TinkerTags.Modifiers.INTERACTION_ABILITIES;
import static slimeknights.tconstruct.common.TinkerTags.Modifiers.MELEE_UPGRADES;
import static slimeknights.tconstruct.common.TinkerTags.Modifiers.RANGED_UPGRADES;

public class ModifierTagProvider extends AbstractModifierTagProvider {

    public ModifierTagProvider(DataGenerator generator, String modId, ExistingFileHelper existingFileHelper) {
        super(generator, modId, existingFileHelper);
    }

    @Override
    protected void addTags() {
        this.tag(GENERAL_UPGRADES)
            .addOptional(TciModifierIds.livingwood)
            .addOptional(TciModifierIds.engineersGoggles)
            .addOptional(TciModifierIds.multiVision)
            .addOptional(TCIntegrationsModifiers.ALF_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.ALFHEIM_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.TERRESTRIAL_MODIFIER.getId());
        this.tag(MELEE_UPGRADES)
            .addOptional(TCIntegrationsModifiers.TERRA_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.ELEMENTAL_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.SIREN_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.UTHERIUM_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.FLAMED_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.ICED_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.ZAPPED_MODIFIER.getId());
        this.tag(HARVEST_UPGRADES)
            .addOptional(TCIntegrationsModifiers.SIREN_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.FROSTSTEEL_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.FORGOTTEN_MODIFIER.getId());
        this.tag(GENERAL_ARMOR_UPGRADES)
            .addOptional(TCIntegrationsModifiers.GREAT_FAIRY_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.ALFHEIM_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.ARS_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.SOUL_STAINED_MODIFIER.getId())
            .addOptional(TciModifierIds.masticate);
        this.tag(GENERAL_ABILITIES)
            .addOptional(TCIntegrationsModifiers.MECHANICAL_ARM_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.POSEIDON_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.ALFHEIM_MODIFIER.getId());
        this.tag(INTERACTION_ABILITIES)
            .addOptional(TCIntegrationsModifiers.ALF_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.GLOWUP_MODIFIER.getId());
        this.tag(HELMET_UPGRADES)
            .addOptional(TCIntegrationsModifiers.FRONTIER_CAP_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.TURTLE_SHELL_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.BISON_FUR_MODIFIER.getId());
        this.tag(CHESTPLATE_UPGRADES)
            .addOptional(TCIntegrationsModifiers.ENCHANTERS_SHIELD_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.SHIELD_OF_THE_DEEP_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.CROCODILE_MODIFIER.getId());
        this.tag(BOOT_UPGRADES)
            .addOptional(TCIntegrationsModifiers.ROADRUNNER_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.MOSQUITO_MODIFIER.getId());
        this.tag(RANGED_UPGRADES)
            .addOptional(TCIntegrationsModifiers.FLAMED_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.ICED_MODIFIER.getId())
            .addOptional(TCIntegrationsModifiers.ZAPPED_MODIFIER.getId());
    }

    @Override
    public String getName() {
        return "TCIntegrations - TCon Modifier Tag Provider";
    }

}
