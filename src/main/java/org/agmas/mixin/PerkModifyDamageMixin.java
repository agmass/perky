package org.agmas.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.agmas.Perky;
import org.agmas.accessors.PlayerAccessor;
import org.agmas.attachment.PerkyItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public class PerkModifyDamageMixin {

	@WrapMethod(method = "actuallyHurt")
	//? if <1.21.4 {
	public void modifyHurt(DamageSource damageSource, float f, Operation<Void> original) {
	//? } else {
	/*public void modifyHurt(ServerLevel serverLevel, DamageSource damageSource, float f, Operation<Void> original) {
	*///? }
		float damage = f;
		for (PerkyItem attachment : Perky.getAttachments(((LivingEntity) (Object) this))) {
			float modifiedDamage = attachment.modifyDamage(((LivingEntity) (Object)this),damageSource,damage);
			if (modifiedDamage != -1) {
				damage = modifiedDamage;
			}
		}
		//? if <1.21.4
		original.call(damageSource,damage);
		//? if >1.21.4
		//original.call(serverLevel, damageSource,damage);
	}
}