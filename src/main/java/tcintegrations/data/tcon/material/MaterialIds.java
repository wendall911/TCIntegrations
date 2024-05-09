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
    public static final MaterialId manaString = id("manastring");
    public static final MaterialId neptunium = id("neptunium");
    public static final MaterialId soulStainedSteel = id("soul_stained_steel");
    public static final MaterialId desh = id("desh");
    public static final MaterialId calorite = id("calorite");
    public static final MaterialId ostrum = id("ostrum");
    public static final MaterialId pendoriteAlloy = id("pendorite_alloy");
    public static final MaterialId brass = id("brass");

    private static MaterialId id(String name) {
        return new MaterialId(TCIntegrations.MODID, name);
    }

}
