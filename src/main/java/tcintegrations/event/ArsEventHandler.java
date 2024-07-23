package tcintegrations.event;

import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import tcintegrations.TCIntegrations;
import tcintegrations.data.integration.ModIntegration;

import java.util.HashMap;

@Mod.EventBusSubscriber(modid = TCIntegrations.MODID)
public class ArsEventHandler {
    @SubscribeEvent
    public static void armorManaDiscount(LivingEvent event) {
        if (ModIntegration.canLoad(ModIntegration.ARS_ELEMENTAL_MODID) && event instanceof com.hollingsworth.arsnouveau.api.event.SpellCastEvent scEvent) {
            if (scEvent.getEntity() instanceof ServerPlayer sp) {
                HashMap<com.hollingsworth.arsnouveau.api.spell.SpellSchool, Integer> bonusMap = tcintegrations.items.modifiers.armor.ArsElementalModifier.getBonusMap(sp);
                if (bonusMap != null) {
                    bonusMap.forEach((school, bonus) -> {
                        if (scEvent.spell.recipe.stream().anyMatch(school::isPartOfSchool)) {
                            scEvent.spell.addDiscount((int) (scEvent.spell.getNoDiscountCost() * bonus * tcintegrations.items.modifiers.armor.ArsElementalModifier.getDiscountPerPart()));
                        }
                    });
                }
            }
        }
    }
}
