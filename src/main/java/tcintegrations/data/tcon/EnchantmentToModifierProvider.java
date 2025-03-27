package tcintegrations.data.tcon;

import org.jetbrains.annotations.NotNull;

import net.minecraft.data.PackOutput;

import dev.shadowsoffire.apotheosis.Apoth;

import slimeknights.tconstruct.library.data.tinkering.AbstractEnchantmentToModifierProvider;

import tcintegrations.items.TCIntegrationsModifiers;

public class EnchantmentToModifierProvider extends AbstractEnchantmentToModifierProvider {

    public EnchantmentToModifierProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void addEnchantmentMappings() {
        addOptional(Apoth.Enchantments.CAPTURING.getId(), TCIntegrationsModifiers.CAPTURING_MODIFIER.getId(), true);
    }

    @Override
    public @NotNull String getName() {
        return "TCIntegrations - TCon Enchantment to Modifier Mapping";
    }

}
