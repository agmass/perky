package org.agmas.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.TridentItem;
import org.agmas.Perky;
import org.agmas.attachment.PerkyItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(TridentItem.class)
public class PerkModifyTridentRiptideMixin {

	@WrapOperation(method = "releaseUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;isInWaterOrRain()Z"))
	public boolean modifyHurt(Player instance, Operation<Boolean> original) {
		for (PerkyItem attachment : Perky.getAttachments(instance)) {
			if (attachment.canRiptide(instance).equals(PerkyItem.CancellableResult.SUCCESS)) return true;
			if (attachment.canRiptide(instance).equals(PerkyItem.CancellableResult.CANCEL)) return false;
		}
		return original.call(instance);
	}
}