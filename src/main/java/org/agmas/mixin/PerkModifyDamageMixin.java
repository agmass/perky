package org.agmas.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.agmas.Perky;
import org.agmas.attachment.PerkyItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(LivingEntity.class)
public class PerkModifyDamageMixin {

	//? if <1.21.4 {
	@WrapMethod(method = "hurt")
	public boolean modifyHurt(DamageSource damageSource, float f, Operation<Boolean> original) {
	//? } else {
	
	/*@WrapMethod(method = "hurtServer")
	public boolean modifyHurt(ServerLevel serverLevel, DamageSource damageSource, float f, Operation<Boolean> original) {
	*///? }
		float damage = f;
		for (PerkyItem attachment : Perky.getAttachments(((LivingEntity) (Object) this))) {
			float modifiedDamage = attachment.modifyDamage(((LivingEntity) (Object)this),damageSource,damage);
			if (modifiedDamage != -1) {
				damage = modifiedDamage;
			}
		}
		//? if <1.21.4
		return original.call(damageSource,damage);
		//? if >1.21.4
		//return original.call(serverLevel, damageSource,damage);

	}
}