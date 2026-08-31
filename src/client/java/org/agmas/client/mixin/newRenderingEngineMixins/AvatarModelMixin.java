package org.agmas.client.mixin.newRenderingEngineMixins;


import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
//? if >=1.21.11 {
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.HumanoidArm;
import org.agmas.client.PerkyRenderKeys;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class AvatarModelMixin {
	@Shadow
	@Final
	public ModelPart leftArm;

	@Shadow
	@Final
	public ModelPart rightArm;

	@Shadow
	@Final
	public ModelPart leftLeg;

	@Shadow
	@Final
	public ModelPart rightLeg;

	@Shadow
	@Final
	public ModelPart body;

	@Shadow
	@Final
	public ModelPart head;

	@Inject(at = @At("TAIL"), method = "setupAnim(Lnet/minecraft/client/renderer/entity/state/HumanoidRenderState;)V")
	private void init(HumanoidRenderState humanoidRenderState, CallbackInfo ci) {
		if (humanoidRenderState.mainArm == HumanoidArm.RIGHT) {
			leftArm.visible = leftArm.visible && humanoidRenderState.getDataOrDefault(PerkyRenderKeys.shouldRenderOffArm, true);
			rightArm.visible = rightArm.visible && humanoidRenderState.getDataOrDefault(PerkyRenderKeys.shouldRenderMainArm, true);
		} else {
			leftArm.visible = leftArm.visible && humanoidRenderState.getDataOrDefault(PerkyRenderKeys.shouldRenderMainArm, true);
			rightArm.visible = rightArm.visible && humanoidRenderState.getDataOrDefault(PerkyRenderKeys.shouldRenderOffArm, true);
		}
		leftLeg.visible = leftLeg.visible && humanoidRenderState.getDataOrDefault(PerkyRenderKeys.shouldRenderLeftLeg, true);
		rightLeg.visible = rightLeg.visible && humanoidRenderState.getDataOrDefault(PerkyRenderKeys.shouldRenderRightLeg, true);
		body.visible = body.visible && humanoidRenderState.getDataOrDefault(PerkyRenderKeys.shouldRenderBody, true);
		head.visible = head.visible && humanoidRenderState.getDataOrDefault(PerkyRenderKeys.shouldRenderHead, true);
	}
}
//? } else {
/*
@Mixin(Minecraft.class)
public class AvatarModelMixin {}
*///? }