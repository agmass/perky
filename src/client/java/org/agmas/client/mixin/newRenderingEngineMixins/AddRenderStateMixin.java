package org.agmas.client.mixin.newRenderingEngineMixins;

import net.minecraft.world.entity.player.Player;
import org.agmas.accessors.PlayerAccessor;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
//? if >=1.21.4 {
/*import net.minecraft.world.entity.Avatar;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.agmas.client.PerkyRenderKeys;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AddRenderStateMixin {
	@Inject(at = @At("TAIL"), method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V")
	private void init(Avatar avatar, AvatarRenderState avatarRenderState, float f, CallbackInfo ci) {
		if (avatar instanceof Player p) {
			avatarRenderState.setData(PerkyRenderKeys.shouldRenderMainArm, !((PlayerAccessor) p).perky$mainArmDisabled());
			avatarRenderState.setData(PerkyRenderKeys.shouldRenderOffArm, !((PlayerAccessor) p).perky$offArmDisabled());
			avatarRenderState.setData(PerkyRenderKeys.shouldRenderHead, !((PlayerAccessor) p).perky$headDisabled());
			avatarRenderState.setData(PerkyRenderKeys.shouldRenderBody, !((PlayerAccessor) p).perky$bodyDisabled());
			avatarRenderState.setData(PerkyRenderKeys.shouldRenderLeftLeg, !((PlayerAccessor) p).perky$leftLegDisabled());
			avatarRenderState.setData(PerkyRenderKeys.shouldRenderRightLeg, !((PlayerAccessor) p).perky$rightLegDisabled());
		}
	}
}
*///? } else {

@Mixin(Minecraft.class)
public class AddRenderStateMixin {}
//? }