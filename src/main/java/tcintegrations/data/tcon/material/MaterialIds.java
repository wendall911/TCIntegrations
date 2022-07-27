package tcintegrations.data.tcon.material;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import slimeknights.tconstruct.library.materials.definition.MaterialId;

import tcintegrations.TCIntegrations;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MaterialIds {

    public static final MaterialId manaSteel = id("manasteel");
    public static final MaterialId livingWood = id("livingwood");
    public static final MaterialId livingRock = id("livingrock");
    public static final MaterialId neptunium = id("neptunium");
    public static final MaterialId soulStainedSteel = id("soul_stained_steel");

    private static MaterialId id(String name) {
        return new MaterialId(TCIntegrations.MODID, name);
    }

}
