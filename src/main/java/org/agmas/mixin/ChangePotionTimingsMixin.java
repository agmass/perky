package org.agmas.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.agmas.Perky;
import org.agmas.attachment.PerkyItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class ChangePotionTimingsMixin {

	@WrapMethod(method = "addEffect(Lnet/minecraft/world/effect/MobEffectInstance;Lnet/minecraft/world/entity/Entity;)Z")
    public boolean untarget(MobEffectInstance mobEffectInstance, Entity entity, Operation<Boolean> original) {
        float durationScale = 1;
        for (PerkyItem attachment : Perky.getAttachments((LivingEntity) (Object)this)) {
            durationScale *= attachment.getPotionTimeMultiplier((LivingEntity) (Object)this, mobEffectInstance);
        }
        if (durationScale == 1) {
            return original.call(mobEffectInstance, entity);
        } else {
            return original.call(new MobEffectInstance(mobEffectInstance.getEffect(), (int) (mobEffectInstance.getDuration()*durationScale), mobEffectInstance.getAmplifier(),mobEffectInstance.isAmbient(),mobEffectInstance.isVisible()), entity);
        }
    }

}