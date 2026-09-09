package org.agmas.client.mixin.oldRenderingEngineMixins;


import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;import net.minecraft.world.entity.HumanoidArm;import net.minecraft.world.entity.LivingEntity;import org.agmas.accessors.PlayerAccessor;import org.spongepowered.asm.mixin.Final;import org.spongepowered.asm.mixin.Mixin;import org.spongepowered.asm.mixin.Shadow;import org.spongepowered.asm.mixin.injection.At;import org.spongepowered.asm.mixin.injection.Inject;import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

//? if <=1.21.1 {


import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
@Mixin(HumanoidModel.class)
public class HumanoidModelMixin {

    @Shadow@Final public ModelPart leftArm;@Shadow@Final public ModelPart rightArm;@Shadow@Final public ModelPart leftLeg;@Shadow@Final public ModelPart rightLeg;@Shadow@Final public ModelPart body;@Shadow@Final public ModelPart head;

    @Inject(at = @At("TAIL"), method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V")
    private void init(LivingEntity livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (livingEntity.getMainArm() == HumanoidArm.RIGHT) {
            leftArm.visible = leftArm.visible && !((PlayerAccessor)livingEntity).perky$offArmDisabled();
            rightArm.visible = rightArm.visible &&!((PlayerAccessor)livingEntity).perky$mainArmDisabled();
        } else {
            leftArm.visible = leftArm.visible && !((PlayerAccessor)livingEntity).perky$mainArmDisabled();
            rightArm.visible = rightArm.visible && !((PlayerAccessor)livingEntity).perky$offArmDisabled();
        }
        leftLeg.visible = leftLeg.visible && !((PlayerAccessor)livingEntity).perky$leftLegDisabled();
        rightLeg.visible = rightLeg.visible && !((PlayerAccessor)livingEntity).perky$rightLegDisabled();
        body.visible = body.visible && !((PlayerAccessor)livingEntity).perky$bodyDisabled();
        head.visible = head.visible && !((PlayerAccessor)livingEntity).perky$headDisabled();

        if (((HumanoidModel)(Object)this) instanceof PlayerModel<?> pm) {
            pm.leftSleeve.visible = leftArm.visible;
            pm.rightSleeve.visible = rightArm.visible;

            pm.leftPants.visible = leftLeg.visible;
            pm.rightPants.visible = rightLeg.visible;
        }
    }
}
//? } else {
/*@Mixin(Minecraft.class)
public class HumanoidModelMixin {}
*///? }
