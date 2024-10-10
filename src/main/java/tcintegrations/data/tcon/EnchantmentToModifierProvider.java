package tcintegrations.data.tcon;

import net.minecraft.data.DataGenerator;
import shadows.apotheosis.Apoth;
import slimeknights.tconstruct.library.data.tinkering.AbstractEnchantmentToModifierProvider;
import tcintegrations.items.TCIntegrationsModifiers;

public class EnchantmentToModifierProvider extends AbstractEnchantmentToModifierProvider {

    public EnchantmentToModifierProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void addEnchantmentMappings() {
        add(Apoth.Enchantments.CAPTURING.get(), TCIntegrationsModifiers.CAPTURING_MODIFIER.getId());
    }

    @Override
    public String getName() {
        return "TCIntegrations - TCon Enchantment to Modifier Mapping";
    }

}
