
package org.agmas.client.render.models;

// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports

//? if <26.1 {
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
//? }
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
//? if >=1.21.4
import net.minecraft.client.renderer.rendertype.RenderTypes;
//? if <1.21.4
//import net.minecraft.client.renderer.RenderType;
import org.agmas.client.render.blockEntity.ConsoleBlockEntityRenderState;
import org.agmas.porting.PPIdentifier;

public class ConsoleModel extends Model
		//? if >=1.21.4
		<ConsoleBlockEntityRenderState>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(PPIdentifier.of("console").id, "main");
	public static final PPIdentifier textureLocation = PPIdentifier.of("textures/block/console.png");
	private final ModelPart group;
	private final ModelPart fillant;

	public ConsoleModel(ModelPart root) {
		//? if >=26.1 {
		/*super(root, RenderTypes::entityTranslucentCullItemTarget);
		*///? } else if >1.21.4 {
		super(root, RenderTypes::itemEntityTranslucentCull);
		//? } else {
		/*super(RenderType::itemEntityTranslucentCull);
		 *///? }
		this.group = root.getChild("group");
		this.fillant = this.group.getChild("fillant");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition group = partdefinition.addOrReplaceChild("group", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, -27.432F, -13.6538F, 16.0F, 32.0F, 14.0F, new CubeDeformation(0.0F))
				.texOffs(0, 46).addBox(4.0F, -23.432F, -14.6538F, 10.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-9.0F, 19.432F, 6.6538F));

		PartDefinition cube_r1 = group.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(20, 61).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.0F, -27.4F, -12.7F, -0.1745F, 0.0F, 0.0F));

		PartDefinition cube_r2 = group.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(22, 46).addBox(-4.0F, -0.5F, -2.0F, 8.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(9.0F, -8.7071F, -14.7071F, 0.7854F, 0.0F, 0.0F));

		PartDefinition fillant = group.addOrReplaceChild("fillant", CubeListBuilder.create().texOffs(16, 51).addBox(-2.5F, -2.0F, -3.5F, 6.0F, 4.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 56).addBox(-2.0F, -1.0F, -3.0F, 5.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(12.5F, -29.432F, -6.1538F));

		return LayerDefinition.create(meshdefinition, 64, 64);
	}


	public void setupAnim(ConsoleBlockEntityRenderState console) {
		//? if >=1.21.4
		super.setupAnim(console);
		fillant.visible = console.shouldShowFillant;

	}

	//? if <1.21.4 {
	/*@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j,
							   //? if <1.21.1
							   //float f, float g, float h,float
							   //? if >=1.21.1
							   int
							   k) {
		console.render(poseStack,vertexConsumer,i,j,
				//? if <1.21.1
				//f,g,h,
				k);
	}
	*///? }
}