package tcintegrations.common;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ItemStack;

import tcintegrations.client.CreativeTabBase;
import tcintegrations.TCIntegrations;

public class CreativeTabs {

    public static final CreativeModeTab INTEGRATION_TAB_GROUP = new CreativeTabBase(
        TCIntegrations.MODID + ".items", () -> new ItemStack(Items.OAK_PLANKS)
    ).setRecipeFolderName("building_blocks");

}
