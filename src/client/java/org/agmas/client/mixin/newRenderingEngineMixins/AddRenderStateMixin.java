package org.agmas.client.mixin.newRenderingEngineMixins;

import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
//? if >=1.21.11 {
import net.minecraft.world.entity.Avatar;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import org.agmas.Perky;
import org.agmas.attachment.PerkyItem;
import org.agmas.client.PerkyRenderKeys;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AvatarRenderer.class)
public class AddRenderStateMixin {
	@Inject(at = @At("TAIL"), method = "extractRenderState(Lnet/minecraft/world/entity/Avatar;Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;F)V")
	private void init(Avatar avatar, AvatarRenderState avatarRenderState, float f, CallbackInfo ci) {
		for (PerkyItem attachment : Perky.getAttachments(avatar)) {
			if (attachment.modifiesVisibleParts()) {
				avatarRenderState.setData(PerkyRenderKeys.shouldRenderMainArm, attachment.shouldRenderPart("mainArm"));
				avatarRenderState.setData(PerkyRenderKeys.shouldRenderOffArm, attachment.shouldRenderPart("offArm"));
				avatarRenderState.setData(PerkyRenderKeys.shouldRenderHead, attachment.shouldRenderPart("head"));
				avatarRenderState.setData(PerkyRenderKeys.shouldRenderBody, attachment.shouldRenderPart("body"));
				avatarRenderState.setData(PerkyRenderKeys.shouldRenderLeftLeg, attachment.shouldRenderPart("leftLeg"));
				avatarRenderState.setData(PerkyRenderKeys.shouldRenderRightLeg, attachment.shouldRenderPart("rightLeg"));
			}
		}
	}
}
//? } else {
/*
@Mixin(Minecraft.class)
public class AddRenderStateMixin {}
*///? }