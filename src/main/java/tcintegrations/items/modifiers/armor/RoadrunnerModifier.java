package tcintegrations.items.modifiers.armor;

import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import slimeknights.mantle.client.TooltipKey;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.TinkerHooks;
import slimeknights.tconstruct.library.modifiers.hook.armor.ArmorWalkModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.armor.EquipmentChangeModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.util.ModifierHookMap;
import slimeknights.tconstruct.library.tools.context.EquipmentChangeContext;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

// Derived from SoulSpeedModifer in TCon 1.18.2

public class RoadrunnerModifier extends Modifier implements ArmorWalkModifierHook, EquipmentChangeModifierHook, TooltipModifierHook {

    private static final UUID ATTRIBUTE_BONUS = UUID.fromString("cd653c21-b0a3-4542-a81e-e575e2b95c7c");
    private static final float SPEED_FACTOR = 0.04F;

    @Override
    protected void registerHooks(ModifierHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addHook(this, TinkerHooks.BOOT_WALK, TinkerHooks.EQUIPMENT_CHANGE, TinkerHooks.TOOLTIP);
    }

    //Gets the position this entity is standing on, cloned from protected living entity method
    private static BlockPos getOnPosition(LivingEntity living) {
        Vec3 position = living.position();
        int x = Mth.floor(position.x);
        int y = Mth.floor(position.y - (double)0.2F);
        int z = Mth.floor(position.z);
        BlockPos pos = new BlockPos(x, y, z);

        if (living.level.isEmptyBlock(pos)) {
            BlockPos below = pos.below();
            BlockState blockstate = living.level.getBlockState(below);
            if (blockstate.collisionExtendsVertically(living.level, below, living)) {
                return below;
            }
        }

        return pos;
    }

    @Override
    public void onWalk(IToolStackView tool, ModifierEntry modifier, LivingEntity living, BlockPos prevPos, BlockPos newPos) {
        // no point trying if not on the ground
        if (tool.isBroken() || !living.isOnGround() || living.level.isClientSide) {
            return;
        }
        // must have speed
        AttributeInstance attribute = living.getAttribute(Attributes.MOVEMENT_SPEED);
        if (attribute == null) {
            return;
        }
        // not above air
        BlockPos belowPos = getOnPosition(living);
        BlockState below = living.level.getBlockState(belowPos);
        if (below.isAir()) {
            return;
        }

        // start by removing the attribute, we are likely going to give it a new number
        if (attribute.getModifier(ATTRIBUTE_BONUS) != null) {
            attribute.removeModifier(ATTRIBUTE_BONUS);
        }

        // add back speed boost if above a sand block and not flying
        if (!living.isFallFlying() && below.is(BlockTags.SAND)) {
            RandomSource rand = living.getRandom();

            // boost speed
            float boost = modifier.getEffectiveLevel() * SPEED_FACTOR;

            attribute.addTransientModifier(new AttributeModifier(ATTRIBUTE_BONUS, "tcintegrations.modifier.roadrunner", boost, AttributeModifier.Operation.ADDITION));

            // damage boots
            if (rand.nextFloat() < 0.04F) {
                ToolDamageUtil.damageAnimated(tool, 1, living, EquipmentSlot.FEET);
            }

            // particles and sounds
            Vec3 motion = living.getDeltaMovement();
            if (living.level instanceof ServerLevel) {
                ((ServerLevel)living.level).sendParticles(ParticleTypes.ASH,
                        living.getX() + (rand.nextDouble() - 0.5) * living.getBbWidth(),
                        living.getY() + 0.1,
                        living.getZ() + (rand.nextDouble() - 0.5) * living.getBbWidth(),
                        0, motion.x * -0.2, 0.1, motion.z * -0.2, 1);
            }
            living.level.playSound(null, living.getX(), living.getY(), living.getZ(), SoundEvents.SAND_FALL, living.getSoundSource(), rand.nextFloat() * 0.4f + rand.nextFloat() > 0.9f ? 0.6f : 0.0f, 0.6f + rand.nextFloat() * 0.4f);
        }
    }

    @Override
    public void onUnequip(IToolStackView tool, ModifierEntry modifier, EquipmentChangeContext context) {
        // remove boost when boots are removed
        LivingEntity livingEntity = context.getEntity();

        if (!livingEntity.level.isClientSide && context.getChangedSlot() == EquipmentSlot.FEET) {
            IToolStackView newTool = context.getReplacementTool();
            // damaging the tool will trigger this hook, so ensure the new tool has the same level
            if (newTool == null || newTool.isBroken() || newTool.getModifierLevel(this) != modifier.getEffectiveLevel()) {
                AttributeInstance attribute = livingEntity.getAttribute(Attributes.MOVEMENT_SPEED);
                if (attribute != null && attribute.getModifier(ATTRIBUTE_BONUS) != null) {
                    attribute.removeModifier(ATTRIBUTE_BONUS);
                }
            }
        }
    }


    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player, List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        if (player == null || tooltipKey == TooltipKey.SHIFT || (!player.isFallFlying() && player.level.getBlockState(getOnPosition(player)).is(BlockTags.SAND))) {
            TooltipModifierHook.addPercentBoost(modifier.getModifier(), getDisplayName(), modifier.getEffectiveLevel() * SPEED_FACTOR, tooltip);
        }
    }

}
