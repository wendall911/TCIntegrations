package tcintegrations.items.modifiers.energy;

import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.modules.build.StatBoostModule;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import tcintegrations.client.TCIntegrationsSE;
import tcintegrations.common.capabilities.ToolEnergyHelper;
import tcintegrations.network.DischargeEffectData;

public class DischargeModifier extends Modifier implements MeleeHitModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        super.registerHooks(hookBuilder);
        hookBuilder.addModule(ToolEnergyHelper.ENERGY_HANDLER);
        hookBuilder.addModule(StatBoostModule.add(ToolEnergyHelper.ENERGY_CAPACITY_STAT).eachLevel(getCapacityEachLevel()));
        hookBuilder.addHook(this, ModifierHooks.MELEE_HIT);
    }

    private static int getCapacityEachLevel() {
        return 500000;
    }
    private double getRange(int modifierLevel){
        return 2.5 + modifierLevel * 1.5;
    }
    private float getDamage(int modifierLevel, int energySpent, float damageDealt){
        return (energySpent / 300000f) * damageDealt * (1 + modifierLevel * 1.2f);
    }
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        ToolEnergyHelper toolEnergyHelper = ToolEnergyHelper.ENERGY_HELPER;
        if (context.getLivingTarget() == null
         || context.getPlayerAttacker() == null
         || context.getPlayerAttacker().level.isClientSide
         || damageDealt == 0
         || !context.isFullyCharged()
         || toolEnergyHelper.getEnergy(tool) < toolEnergyHelper.getCapacity(tool) * 0.5f){
            return;
        }
        int discharged = (int) (toolEnergyHelper.getEnergy(tool) * 0.05f);
        Player attacker = context.getPlayerAttacker();
        LivingEntity target = context.getLivingTarget();
        Level world = attacker.level;
        for (LivingEntity entity : world.getEntitiesOfClass(LivingEntity.class,
                new AABB(target.blockPosition()).inflate(getRange(modifier.getLevel())),
                (e) -> !e.equals(attacker))) {
            entity.hurt(DamageSource.playerAttack(attacker), getDamage(modifier.getLevel(), discharged, damageDealt));
            entity.setRemainingFireTicks(30);
            DischargeEffectData.send(world, target.getEyePosition(), entity.getEyePosition()); // send the effect to the client
        }
        world.playSound(attacker, attacker.blockPosition(), TCIntegrationsSE.DISCHARGE.get(), SoundSource.PLAYERS, 1.0f, 1.0f);
        toolEnergyHelper.setEnergy(tool, toolEnergyHelper.getEnergy(tool) - discharged);
    }
}
