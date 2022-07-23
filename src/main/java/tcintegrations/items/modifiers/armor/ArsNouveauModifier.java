package tcintegrations.items.modifiers.armor;

import java.util.HashMap;
import java.util.Map;

import com.hollingsworth.arsnouveau.common.enchantment.EnchantmentRegistry;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import net.minecraftforge.common.util.Lazy;

import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import tcintegrations.items.modifiers.ArsNouveauBaseModifier;

public class ArsNouveauModifier extends ArsNouveauBaseModifier {

    private final Lazy<Component> MAGE_NAME = Lazy.of(() -> applyStyle(new TranslatableComponent(getTranslationKey() + ".2")));
    private final Lazy<Component> ARCHMAGE_NAME = Lazy.of(() -> applyStyle(new TranslatableComponent(getTranslationKey() + ".3")));

    @Override
    public Component getDisplayName(int level) {
        return switch(level) {
            case 2 -> MAGE_NAME.get();
            case 3 -> ARCHMAGE_NAME.get();
            default -> super.getDisplayName();
        };
    }

    @Override
    public void onEquip(IToolStackView tool, int level, EquipmentChangeContext context) {
        final Player player = context.getEntity() instanceof Player ? (Player) context.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            ItemStack replacement = context.getReplacement();
            Map<Enchantment, Integer> enchantments = new HashMap<>();

            enchantments.put(EnchantmentRegistry.MANA_BOOST_ENCHANTMENT, level);
            enchantments.put(EnchantmentRegistry.MANA_REGEN_ENCHANTMENT, level);

            EnchantmentHelper.setEnchantments(enchantments, replacement);
        }
    }

}
