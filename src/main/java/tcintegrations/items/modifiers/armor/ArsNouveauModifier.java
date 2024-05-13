package tcintegrations.items.modifiers.armor;

import java.util.HashMap;
import java.util.Map;

import com.hollingsworth.arsnouveau.common.enchantment.EnchantmentRegistry;

import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

import net.minecraftforge.common.util.Lazy;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap.Builder;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import tcintegrations.items.modifiers.ArsNouveauBaseModifier;

public class ArsNouveauModifier extends ArsNouveauBaseModifier implements EquipmentChangeModifierHook {

    private final Lazy<Component> MAGE_NAME = Lazy.of(() -> applyStyle(Component.translatable(getTranslationKey() + ".2")));
    private final Lazy<Component> ARCHMAGE_NAME = Lazy.of(() -> applyStyle(Component.translatable(getTranslationKey() + ".3")));

    @Override
    protected void registerHooks(Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.EQUIPMENT_CHANGE);
    }

    @Override
    public Component getDisplayName(int level) {
        return switch(level) {
            case 2 -> MAGE_NAME.get();
            case 3 -> ARCHMAGE_NAME.get();
            default -> super.getDisplayName();
        };
    }

    @Override
    public void onEquip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        final Player player = context.getEntity() instanceof Player ? (Player) context.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            ItemStack replacement = context.getReplacement();
            Map<Enchantment, Integer> enchantments = new HashMap<>();

            enchantments.put(EnchantmentRegistry.MANA_BOOST_ENCHANTMENT.get(), tool.getModifierLevel(modifier.getModifier()));
            enchantments.put(EnchantmentRegistry.MANA_REGEN_ENCHANTMENT.get(), tool.getModifierLevel(modifier.getModifier()));

            EnchantmentHelper.setEnchantments(enchantments, replacement);
        }
    }

}
