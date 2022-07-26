package tcintegrations.items.modifiers.armor;

import java.util.UUID;

import com.github.alexthe666.alexsmobs.item.AMItemRegistry;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import net.minecraftforge.common.ForgeMod;

import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class CrocodileModifier extends NoLevelsModifier {

    private static final AttributeModifier INCREASED_SWIM_SPEED = new AttributeModifier(
        UUID.fromString("95b9a2f3-afe3-4938-98d8-bfca7168fa70"),
        "Crocodile Chestplate Swim Speed",
        1,
        AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier INCREASED_ARMOR = new AttributeModifier(
        UUID.fromString("bcb5131d-3bb9-499e-b336-e9aad469ace4"),
        "Crocodile Chestplate Armor",
        AMItemRegistry.CROCODILE_ARMOR_MATERIAL.getDefenseForSlot(EquipmentSlot.CHEST),
        AttributeModifier.Operation.ADDITION
    );
    private static final AttributeModifier INCREASED_ARMOR_TOUGHNESS = new AttributeModifier(
        UUID.fromString("a9e4d56f-f8aa-4c6f-ad83-0a394912d1e5"),
        "Crocodile Chestplate Armor Toughness",
        AMItemRegistry.CROCODILE_ARMOR_MATERIAL.getToughness(),
        AttributeModifier.Operation.ADDITION
    );

    @Override
    public void onEquip(IToolStackView tool, int level, EquipmentChangeContext context) {
        final Player player = context.getEntity() instanceof Player ? (Player) context.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;
            AttributeInstance swimSpeed = sp.getAttribute(ForgeMod.SWIM_SPEED.get());
            AttributeInstance armor = sp.getAttribute(Attributes.ARMOR);
            AttributeInstance armorToughness = sp.getAttribute(Attributes.ARMOR_TOUGHNESS);

            if (swimSpeed != null
                    && !swimSpeed.hasModifier(INCREASED_SWIM_SPEED)) {
                swimSpeed.addPermanentModifier(INCREASED_SWIM_SPEED);
            }

            if (armor != null
                    && !armor.hasModifier(INCREASED_ARMOR)) {
                armor.addPermanentModifier(INCREASED_ARMOR);
            }

            if (armorToughness != null
                    && !armorToughness.hasModifier(INCREASED_ARMOR_TOUGHNESS)) {
                armorToughness.addPermanentModifier(INCREASED_ARMOR_TOUGHNESS);
            }
        }
    }

    @Override
    public void onUnequip(IToolStackView tool, int level, EquipmentChangeContext context) {
        final Player player = context.getEntity() instanceof Player ? (Player) context.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            final ServerPlayer sp = (ServerPlayer) player;
            AttributeInstance swimSpeed = sp.getAttribute(ForgeMod.SWIM_SPEED.get());
            AttributeInstance armor = sp.getAttribute(Attributes.ARMOR);
            AttributeInstance armorToughness = sp.getAttribute(Attributes.ARMOR_TOUGHNESS);

            if (swimSpeed != null
                    && swimSpeed.hasModifier(INCREASED_SWIM_SPEED)) {
                swimSpeed.removeModifier(INCREASED_SWIM_SPEED);
            }

            if (armor != null
                    && !armor.hasModifier(INCREASED_ARMOR)) {
                armor.removeModifier(INCREASED_ARMOR);
            }

            if (armorToughness != null
                    && !armorToughness.hasModifier(INCREASED_ARMOR_TOUGHNESS)) {
                armorToughness.removeModifier(INCREASED_ARMOR_TOUGHNESS);
            }
        }
    }

}
