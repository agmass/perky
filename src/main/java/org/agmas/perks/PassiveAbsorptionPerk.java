package org.agmas.perks;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import org.agmas.accessors.PlayerAccessor;
import org.agmas.attachment.PerkyItem;

public class PassiveAbsorptionPerk extends PerkyItem {
    public PassiveAbsorptionPerk() {
        super(2);
    }

    @Override
    public void tick(LivingEntity livingEntity) {
        if (((PlayerAccessor)livingEntity).perky$timeSinceLastDamage() > 20*30) {
            if (!livingEntity.hasEffect(MobEffects.ABSORPTION) && !livingEntity.level().isClientSide()) {
                livingEntity.addEffect(
                        new MobEffectInstance(MobEffects.ABSORPTION, 20*30,1, true, false));
            }
        }
        super.tick(livingEntity);
    }
}
