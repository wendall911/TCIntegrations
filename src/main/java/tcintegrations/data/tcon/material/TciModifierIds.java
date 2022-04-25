package tcintegrations.data.tcon.material;

import slimeknights.tconstruct.library.modifiers.ModifierId;

import tcintegrations.TCIntegrations;

public class TciModifierIds {
    public static final ModifierId mana = id("mana");

    private TciModifierIds() {}

    private static ModifierId id(String name) {
        return new ModifierId(TCIntegrations.MODID, name);
    }
}
