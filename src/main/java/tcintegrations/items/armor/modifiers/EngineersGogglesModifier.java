package tcintegrations.items.armor.modifiers;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.utils.RestrictedCompoundTag;

public class EngineersGogglesModifier extends NoLevelsModifier {

    public static final String CREATE_GOGGLES = "create_goggles";

    @Override
    public void addRawData(IToolStackView tool, int level, RestrictedCompoundTag tag) {
        if (tool.hasTag(TinkerTags.Items.HELMETS)) {
            tag.putBoolean(CREATE_GOGGLES, true);
        }
    }

    @Override
    public void beforeRemoved(IToolStackView tool, RestrictedCompoundTag tag) {
        tag.remove(CREATE_GOGGLES);
    }

}
