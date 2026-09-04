package org.agmas.client.mixin.newRenderingEngineMixins;

import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.agmas.accessors.PlayerAccessor;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
//? if >=1.21.4 {
import net.minecraft.world.entity.Avatar;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.agmas.client.PerkyRenderKeys;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class AddRenderStateMixin {
	@Inject(at = @At("TAIL"), method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V")
	private void init(LivingEntity avatar, LivingEntityRenderState avatarRenderState, float f, CallbackInfo ci) {
		avatarRenderState.setData(PerkyRenderKeys.shouldRenderMainArm, !((PlayerAccessor) avatar).perky$mainArmDisabled());
		avatarRenderState.setData(PerkyRenderKeys.shouldRenderOffArm, !((PlayerAccessor) avatar).perky$offArmDisabled());
		avatarRenderState.setData(PerkyRenderKeys.shouldRenderHead, !((PlayerAccessor) avatar).perky$headDisabled());
		avatarRenderState.setData(PerkyRenderKeys.shouldRenderBody, !((PlayerAccessor) avatar).perky$bodyDisabled());
		avatarRenderState.setData(PerkyRenderKeys.shouldRenderLeftLeg, !((PlayerAccessor) avatar).perky$leftLegDisabled());
		avatarRenderState.setData(PerkyRenderKeys.shouldRenderRightLeg, !((PlayerAccessor) avatar).perky$rightLegDisabled());
	}
}
//? } else {

/*@Mixin(Minecraft.class)
public class AddRenderStateMixin {}
*///? }