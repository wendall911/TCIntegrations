package tcintegrations.items.modifiers.armor;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.hook.build.RawDataModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.utils.RestrictedCompoundTag;

public class EngineersGogglesModifier extends NoLevelsModifier implements RawDataModifierHook {

    public static final String CREATE_GOGGLES = "create_goggles";

    @Override
    public void addRawData(IToolStackView tool, ModifierEntry modifier, RestrictedCompoundTag tag) {
        if (tool.hasTag(TinkerTags.Items.HELMETS)) {
            tag.putBoolean(CREATE_GOGGLES, true);
        }
    }

    @Override
    public void removeRawData(IToolStackView tool, Modifier modifier, RestrictedCompoundTag tag) {
        tag.remove(CREATE_GOGGLES);
    }

}
