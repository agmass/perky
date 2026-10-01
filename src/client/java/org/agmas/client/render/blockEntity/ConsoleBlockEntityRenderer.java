package org.agmas.client.render.blockEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
//? if <26.1
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
//? if >1.21.4
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;
import org.agmas.block.entity.ConsoleBlockEntity;
import org.agmas.client.render.models.ConsoleModel;

//? if >=1.21.4 {
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
//? if <26.1
import net.minecraft.client.renderer.state.CameraRenderState;
//? if >=26.1
//import net.minecraft.client.renderer.state.level.CameraRenderState;
import org.jspecify.annotations.Nullable;
//? } else {
/*import net.minecraft.client.renderer.RenderType;
*///? }

public class ConsoleBlockEntityRenderer implements BlockEntityRenderer<ConsoleBlockEntity
        //? if >=1.21.4
        , ConsoleBlockEntityRenderState
        > {


    private final ConsoleModel model;

    public ConsoleBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new ConsoleModel(ConsoleModel.createBodyLayer().bakeRoot());
    }

    //? if >=1.21.4 {
    @Override
    public ConsoleBlockEntityRenderState createRenderState() {
        return new ConsoleBlockEntityRenderState();
    }

    @Override
    public void extractRenderState(ConsoleBlockEntity blockEntity, ConsoleBlockEntityRenderState blockEntityRenderState, float f, Vec3 vec3, ModelFeatureRenderer.@Nullable CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(blockEntity, blockEntityRenderState, f, vec3, crumblingOverlay);
        getRenderState(blockEntity,blockEntityRenderState, f);
    }
    //? }

    public ConsoleBlockEntityRenderState getRenderState(ConsoleBlockEntity blockEntity, ConsoleBlockEntityRenderState consoleBlockEntityRenderState, float f) {
        consoleBlockEntityRenderState.shouldShowFillant = blockEntity.hasBottleAttached;
        consoleBlockEntityRenderState.direction = blockEntity.direction;
        consoleBlockEntityRenderState.tickDelta = f;
        return consoleBlockEntityRenderState;
    }

    //? if >=1.21.4 {

    @Override
    public void submit(ConsoleBlockEntityRenderState blockEntityRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        model.resetPose();
        model.setupAnim(blockEntityRenderState);
        Direction facing = blockEntityRenderState.direction;
        poseStack.mulPose(
                Axis.ZP.rotationDegrees(180)
        );
        poseStack.mulPose(
                Axis.YP.rotationDegrees(facing.toYRot())
        );
        poseStack.translate(0,-1.5,0);
        if (facing.equals(Direction.SOUTH)) {
            poseStack.translate(-0.5,0,0.5);
        }
        if (facing.equals(Direction.WEST)) {
            poseStack.translate(-0.5,0,-0.5);
        }
        if (facing.equals(Direction.EAST)) {
            poseStack.translate(0.5,0,0.5);
        }
        if (facing.equals(Direction.NORTH)) {
            poseStack.translate(0.5,0,-0.5);
        }

        //? if <26.1 {
        submitNodeCollector.submitModel(model,blockEntityRenderState,poseStack,
                RenderTypes.itemEntityTranslucentCull(ConsoleModel.textureLocation.id),
                blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY,0, null);
        //? } else {
        /*submitNodeCollector.submitModel(model,blockEntityRenderState,poseStack,
                RenderTypes.entityTranslucentCullItemTarget(ConsoleModel.textureLocation.id),
                blockEntityRenderState.lightCoords, OverlayTexture.NO_OVERLAY,0, (ModelFeatureRenderer.CrumblingOverlay) null);
        *///? }
    }
    //? } else {
    /*public void render(ConsoleBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        ConsoleBlockEntityRenderState blockEntityRenderState = getRenderState(blockEntity, new ConsoleBlockEntityRenderState(),f);
        model.setupAnim(blockEntityRenderState);
        Direction facing = blockEntityRenderState.direction.getOpposite();
        poseStack.mulPose(
                Axis.ZP.rotationDegrees(180)
        );
        poseStack.mulPose(
                Axis.YP.rotationDegrees(facing.toYRot())
        );
        poseStack.translate(0,-1.5,0);
        if (facing.equals(Direction.SOUTH)) {
            poseStack.translate(-1,0,0.5);
        }
        if (facing.equals(Direction.WEST)) {
            poseStack.translate(-1,0,-0.5);
        }
        if (facing.equals(Direction.EAST)) {
            poseStack.translate(0,0,0.5);
        }
        if (facing.equals(Direction.NORTH)) {
            poseStack.translate(0,0,-0.5);
        }
        model.renderToBuffer(poseStack,multiBufferSource.getBuffer(model.renderType(ConsoleModel.textureLocation.id)),
                //? if <1.21.1
                //i,j,1,1,1,1
                //? if >=1.21.1
                i,j,-1
        );
    }
    *///? }

}
