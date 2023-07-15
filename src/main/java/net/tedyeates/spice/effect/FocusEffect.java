package net.tedyeates.spice.effect;

import java.util.List;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.phys.AABB;
import net.tedyeates.spice.Spice;

public class FocusEffect extends MobEffect {

    protected FocusEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
    
    private void focus(LivingEntity pLivingEntity){
        AABB playerAoe = new AABB(
            pLivingEntity.getX() - Spice.SPICE_FOCUS_RADIUS, 
            pLivingEntity.getY() - Spice.SPICE_FOCUS_RADIUS,
            pLivingEntity.getZ() - Spice.SPICE_FOCUS_RADIUS,
            pLivingEntity.getX() + Spice.SPICE_FOCUS_RADIUS,
            pLivingEntity.getY() + Spice.SPICE_FOCUS_RADIUS,
            pLivingEntity.getZ() + Spice.SPICE_FOCUS_RADIUS
        );

        TargetingConditions targeting = TargetingConditions.forNonCombat().ignoreInvisibilityTesting();

        List<LivingEntity> entities =  pLivingEntity.level.getNearbyEntities(
            LivingEntity.class, 
            targeting, 
            pLivingEntity, 
            playerAoe
        );

        for (LivingEntity entity : entities) {
            entity.addEffect(
                new MobEffectInstance(MobEffects.GLOWING, Spice.SECOND_TICKS/2), 
                pLivingEntity
            );
        }
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide()) {
            focus(pLivingEntity);
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }

    @Override
    public boolean isDurationEffectTick(int pDuration, int pAmplifier) {
        return true;
    }
}
