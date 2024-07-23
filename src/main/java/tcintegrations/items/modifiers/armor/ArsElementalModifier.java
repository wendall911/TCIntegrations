package tcintegrations.items.modifiers.armor;

import alexthw.ars_elemental.api.item.IElementalArmor;
import alexthw.ars_elemental.api.item.ISchoolFocus;
import com.hollingsworth.arsnouveau.api.spell.SpellSchool;
import com.hollingsworth.arsnouveau.api.spell.SpellSchools;
import com.hollingsworth.arsnouveau.common.capability.CapabilityRegistry;
import com.hollingsworth.arsnouveau.common.enchantment.EnchantmentRegistry;
import com.hollingsworth.arsnouveau.common.potions.ModPotions;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.common.util.Lazy;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.ModifyDamageModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.context.EquipmentContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import tcintegrations.items.TCIntegrationsModifiers;
import tcintegrations.items.modifiers.armor.ArsNouveauModifier;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

import static com.hollingsworth.arsnouveau.api.spell.SpellSchools.*;


/*
    * Note that there are four elements (fire, water, earth, and air) so this class will have 4 instances.
 */
public class ArsElementalModifier extends ArsNouveauModifier implements ModifyDamageModifierHook {

    private final SpellSchool element;
    public ArsElementalModifier(SpellSchool element) {
        this.element = element;
    }

    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, ModifierHooks.MODIFY_DAMAGE);
    }

    public static float getDiscountPerPart(){
        return 0.2f;
    }

    @Override
    public void onEquip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        final Player player = context.getEntity() instanceof Player ? (Player) context.getEntity() : null;

        if (player != null && !player.level.isClientSide) {
            ItemStack replacement = context.getReplacement();
            Map<Enchantment, Integer> enchantments = new HashMap<>();

            enchantments.put(EnchantmentRegistry.MANA_BOOST_ENCHANTMENT.get(), 4);
            enchantments.put(EnchantmentRegistry.MANA_REGEN_ENCHANTMENT.get(), 4);

            EnchantmentHelper.setEnchantments(enchantments, replacement);
        }
    }



    @Override
    public Component getDisplayName(int level) {
        return Lazy.of(() -> applyStyle(Component.translatable(getTranslationKey()))).get();
    }

    /**
     *   <ul>
     *   <li>{@link alexthw.ars_elemental.event.DamageEvents#damageTweaking(LivingHurtEvent)} uses Item to predicate the event but not ItemStack.</li>
     *   <li>So we need to write a new tconstruct-ive method instead.</li>
     *   </ul>
     */
    @Override
    public float modifyDamageTaken(IToolStackView tool, ModifierEntry modifier, EquipmentContext context, EquipmentSlot slotType, DamageSource source, float amount, boolean isDirectDamage) {
        if (context.getEntity() instanceof ServerPlayer sp) {
            HashMap<SpellSchool, Integer> bonusMap = getBonusMap(sp);
            boolean not_bypassEnchants = !source.isBypassMagic();
            if (bonusMap != null && not_bypassEnchants) {
                int allBonus = bonusMap.values().stream().mapToInt(Integer::intValue).sum();
                int bonus = bonusMap.getOrDefault(element, 0);
                if (bonus == 4) {
                    switch (element.getId()) {
                        case "fire" -> {
                            if (source.isFire()) {
                                sp.clearFire();
                                if (ISchoolFocus.hasFocus(sp) == SpellSchools.ELEMENTAL_FIRE) {
                                    sp.addEffect(new MobEffectInstance(ModPotions.SPELL_DAMAGE_EFFECT.get(), 200, 2));
                                }
                            }
                        }
                        case "water" -> {
                            if (source == DamageSource.DROWN) {
                                sp.setAirSupply(sp.getMaxAirSupply());
                                allBonus += 5;
                            }
                        }
                        case "air" -> {
                            if (source.isFall()) {
                                allBonus += 5;
                            }
                        }
                        case "earth" -> {
                            if (sp.getEyePosition().y() < 20 && sp.getFoodData().getFoodLevel() < 2) {
                                sp.getFoodData().setFoodLevel(20);
                            }
                        }
                    }
                }
                if (allBonus > 0) {

                    int finalBonusReduction = allBonus;
                    CapabilityRegistry.getMana(sp).ifPresent(mana -> {
                        if (finalBonusReduction > 3) mana.addMana(amount * 5);
                        sp.addEffect(new MobEffectInstance(ModPotions.MANA_REGEN_EFFECT.get(), 200, finalBonusReduction / 2));
                    });
                }
                return amount * (1 - allBonus / 10F);
            }
        }
        return amount;
    }
    @Nullable
    public static HashMap<SpellSchool,Integer> getBonusMap(ServerPlayer sp){
        if (sp == null) return null;

        HashMap<SpellSchool, Integer> bonusMap = new HashMap<>();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            SpellSchool school = getArmorSchoolType(sp, slot);
            if (school != null) {
                bonusMap.put(school, bonusMap.getOrDefault(school, 0) + 1);
            }
        }
        return bonusMap;
    }

    @Nullable
    public static SpellSchool getArmorSchoolType(ServerPlayer sp, EquipmentSlot slot) {
        ItemStack stack = sp.getItemBySlot(slot);

        if (stack.isEmpty()) return null;

        if (stack.getItem() instanceof IElementalArmor) {
            return ((IElementalArmor) stack.getItem()).getSchool(); // Non-tinker's armor will also be checked here.
        }

        ToolStack armor = ToolStack.from(stack);

        if (armor.isBroken()) return null;

        return armor.getUpgrades().getLevel(TCIntegrationsModifiers.AQUAMANCER_MODIFIER.getId()) > 0 ? ELEMENTAL_WATER :
                armor.getUpgrades().getLevel(TCIntegrationsModifiers.PYROMANCER_MODIFIER.getId()) > 0 ? ELEMENTAL_FIRE :
                armor.getUpgrades().getLevel(TCIntegrationsModifiers.GEOMANCER_MODIFIER.getId()) > 0 ? ELEMENTAL_EARTH :
                armor.getUpgrades().getLevel(TCIntegrationsModifiers.AEROMANCER_MODIFIER.getId()) > 0 ? ELEMENTAL_AIR : null;
    }
}
