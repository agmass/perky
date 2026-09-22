package org.agmas.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.agmas.Perky;
import org.agmas.attachment.PerkyItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class DontAllowUseMixin {

	@Inject(method = "use", at = @At("HEAD"), cancellable = true)
	public void cantUse(Level level, Player player, InteractionHand interactionHand, CallbackInfoReturnable<InteractionResult> cir) {
		for (PerkyItem attachment : Perky.getAttachments(player)) {
			if (attachment == null) continue;
			InteractionResult result = attachment.overrideItemUse(((ItemStack) (Object)this), player, interactionHand);
			if (result != InteractionResult.PASS) {
				cir.setReturnValue(result);
				cir.cancel();
			}
		}
	}

}