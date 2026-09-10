package org.agmas.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import org.agmas.Perky;
import org.agmas.attachment.PerkyItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public class UnallowTargetMixin {

	@Inject(method = "setTarget", at = @At("HEAD"), cancellable = true)
    public void untarget(LivingEntity livingEntity, CallbackInfo ci) {
        for (PerkyItem attachment : Perky.getAttachments(livingEntity)) {
            if (!attachment.canAngerMob(livingEntity, ((Mob) (Object)this))) {
                ci.cancel();
            }
        }
	}

}