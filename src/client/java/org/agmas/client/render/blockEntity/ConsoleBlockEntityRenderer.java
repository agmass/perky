package org.agmas.client.render.blockEntity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.phys.Vec3;
import org.agmas.block.entity.ConsoleBlockEntity;
import org.agmas.client.render.models.ConsoleModel;

//? if >=1.21.4 {
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
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
        getRenderState(blockEntity,blockEntityRenderState);
    }
    //? }

    public ConsoleBlockEntityRenderState getRenderState(ConsoleBlockEntity blockEntity, ConsoleBlockEntityRenderState consoleBlockEntityRenderState) {
        consoleBlockEntityRenderState.shouldShowFillant = true;
        return consoleBlockEntityRenderState;
    }

    @Override
    //? if >=1.21.4 {
    public void submit(ConsoleBlockEntityRenderState blockEntityRenderState, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState cameraRenderState) {
        model.resetPose();
        model.setupAnim(blockEntityRenderState);
        Direction facing = blockEntityRenderState.blockState.getValue(HorizontalDirectionalBlock.FACING);
        poseStack.mulPose(
                Axis.YP.rotationDegrees(facing.toYRot())
        );
        submitNodeCollector.submitModel(model,blockEntityRenderState,poseStack, RenderTypes.entityTranslucent(ConsoleModel.textureLocation.id), LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY,0, null);
    }
    //? } else {
    /*public void render(ConsoleBlockEntity blockEntity, float f, PoseStack poseStack, MultiBufferSource multiBufferSource, int i, int j) {
        ConsoleBlockEntityRenderState blockEntityRenderState = getRenderState(blockEntity, new ConsoleBlockEntityRenderState());
        model.resetPose();
        model.setupAnim(blockEntityRenderState);
        model.renderToBuffer(poseStack,multiBufferSource.getBuffer(model.renderType(ConsoleModel.textureLocation.id)),i,j,f,1,1,1);
    }
    *///? }

}
