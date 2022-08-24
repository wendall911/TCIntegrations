package tcintegrations.items.modifiers.armor;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.utils.RestrictedCompoundTag;

public class MultiVisionModifier extends NoLevelsModifier {

    public static final String VOLTMETER = "ie_voltmeter";

    @Override
    public void addRawData(IToolStackView tool, int level, RestrictedCompoundTag tag) {
        if (tool.hasTag(TinkerTags.Items.HELMETS)) {
            tag.putBoolean(VOLTMETER, true);
        }
    }

    @Override
    public void beforeRemoved(IToolStackView tool, RestrictedCompoundTag tag) {
        tag.remove(VOLTMETER);
    }

}
