package org.agmas.mixin;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
//? if <=1.21.1 {
/*import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Zombie;
*///? } else {
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.monster.zombie.Zombie;
//? }
import org.agmas.Perky;
import org.agmas.accessors.PlayerAccessor;
import org.agmas.attachment.PerkyItem;
import org.agmas.init.PerkyItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

@Mixin(Mob.class)
public class ChanceToSpawnSinglehandedMixin  {

	@Inject(method = "populateDefaultEquipmentSlots", at = @At("HEAD"))
	public void spawnSingleHanded(CallbackInfo ci) {
		if (new Random().nextFloat(10) == 1) {
			if (((Mob) (Object) this) instanceof Zombie || ((Mob) (Object) this) instanceof AbstractSkeleton) {
				Perky.addAttachment(((LivingEntity) (Object) this), PerkyItems.SINGLEHANDED);
			}
		}
	}
}