package tcintegrations.items.tool.modifiers;

import java.util.List;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;

import net.minecraftforge.common.Tags;

import slimeknights.tconstruct.library.recipe.modifiers.severing.SeveringRecipe;
import slimeknights.tconstruct.library.recipe.modifiers.severing.SeveringRecipeCache;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import tcintegrations.common.TagManager;
import vazkii.botania.common.entity.EntityPixie;

import tcintegrations.TCIntegrations;

public class ElementalModifier extends ManaItemModifier {

    private static final int MANA_PER_DAMAGE = 70;

    @Override
    public int getManaPerDamage() {
        return MANA_PER_DAMAGE;
    }

    @Override
    public int afterEntityHit(IToolStackView tool, int level, ToolAttackContext context, float damageDealt) {
        Player player = context.getPlayerAttacker();
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);

        if (TCIntegrations.RANDOM.nextFloat() <= 0.05F) {
            EntityPixie pixie = new EntityPixie(player.level);

            pixie.setPos(player.getX(), player.getY() + 2, player.getZ());

            float dmg = 4;

            if (!stack.isEmpty()) {
                dmg += 2;
            }

            pixie.setProps(context.getLivingTarget(), player, 0, dmg);
            pixie.finalizeSpawn(
                    (ServerLevelAccessor) player.level,
                    player.level.getCurrentDifficultyAt(pixie.blockPosition()),
                    MobSpawnType.EVENT, null, null);
            player.level.addFreshEntity(pixie);
        }

        return 0;
    }

    @Override
    public List<ItemStack> processLoot(IToolStackView tool, int level, List<ItemStack> generatedLoot, LootContext context) {
        if (!context.hasParam(LootContextParams.DAMAGE_SOURCE)) {
            return generatedLoot;
        }

        Entity entity = context.getParamOrNull(LootContextParams.THIS_ENTITY);

        if (entity != null && entity.getType().is(TagManager.EntityTypes.ELEMENTAL_SEVERING_MOBS)) {
            if (generatedLoot.stream().noneMatch(stack -> stack.is(Tags.Items.HEADS))) {
                List<SeveringRecipe> recipes = SeveringRecipeCache.findRecipe(context.getLevel().getRecipeManager(), entity.getType());

                if (!recipes.isEmpty()) {
                    float chance = 0.0769F;
                    EntityType entityType = entity.getType();

                    if (entityType == EntityType.SKELETON || entityType == EntityType.WITHER_SKELETON) {
                        chance = 0.1154F;
                    }

                    for (SeveringRecipe recipe : recipes) {
                        ItemStack result = recipe.getOutput(entity);

                        if (!result.isEmpty() && TCIntegrations.RANDOM.nextFloat() <= chance) {
                            if (result.getCount() > 1) {
                                result.setCount(TCIntegrations.RANDOM.nextInt(result.getCount()) + 1);
                            }

                            generatedLoot.add(result);
                        }
                    }
                }
            }
        }

        return generatedLoot;
    }

}
