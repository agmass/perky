
package org.agmas.client.render.models;

// Made with Blockbench 5.1.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
//? if >=1.21.4
//import net.minecraft.client.renderer.rendertype.RenderTypes;
//? if <1.21.4
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.entity.Entity;
import org.agmas.block.entity.ConsoleBlockEntity;
import org.agmas.client.render.blockEntity.ConsoleBlockEntityRenderState;
import org.agmas.porting.PPIdentifier;

public class ConsoleModel extends Model
		//? if >=1.21.4
		//<ConsoleBlockEntityRenderState>
{
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(PPIdentifier.of("console").id, "main");
	public static final PPIdentifier textureLocation = PPIdentifier.of("textures/block/console.png");
	private final ModelPart console;
	private final ModelPart body;
	private final ModelPart lever;
	private final ModelPart extrusion;
	private final ModelPart fillant;
	private final ModelPart posters;
	private final ModelPart planes;

	public ConsoleModel(ModelPart root) {
		//? if >1.21.4 {
		/*super(root, RenderTypes::entityTranslucent);
		*///? } else {
		super(RenderType::entityTranslucent);
		 //? }
		this.console = root.getChild("Console");
		this.body = this.console.getChild("body");
		this.lever = this.body.getChild("lever");
		this.extrusion = this.console.getChild("extrusion");
		this.fillant = this.extrusion.getChild("fillant");
		this.posters = this.extrusion.getChild("posters");
		this.planes = this.console.getChild("planes");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition Console = partdefinition.addOrReplaceChild("Console", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition body = Console.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-13.0F, 0.0F, -7.0F, 26.0F, 38.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition lever = body.addOrReplaceChild("lever", CubeListBuilder.create(), PartPose.offset(1.0F, 22.0F, 9.25F));

		PartDefinition cube_r1 = lever.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(8, 62).addBox(-0.5F, 0.0F, -1.25F, 1.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, 0.0F, -0.2182F, 1.5708F));

		PartDefinition cube_r2 = lever.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 12).addBox(-2.5F, -0.5F, 3.75F, 5.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -3.0F, 0.2182F, 0.0F, 0.0F));

		PartDefinition extrusion = Console.addOrReplaceChild("extrusion", CubeListBuilder.create().texOffs(0, 52).mirror().addBox(5.5F, -14.0F, 10.5F, 10.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 61).addBox(9.0F, -21.0F, 10.5F, 6.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(66, 10).addBox(-9.0F, -19.5F, 10.5F, 5.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
				.texOffs(0, 52).mirror().addBox(-6.5F, -14.0F, 10.5F, 10.0F, 8.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(0, 79).addBox(-9.5F, -2.0F, 10.5F, 26.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-3.5F, 38.0F, -3.5F));

		PartDefinition cube_r3 = extrusion.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(66, 3).addBox(-8.0F, -0.5F, -3.0F, 16.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(4.5F, -23.432F, 11.1538F, -0.7854F, 0.0F, 0.0F));

		PartDefinition fillant = extrusion.addOrReplaceChild("fillant", CubeListBuilder.create().texOffs(33, 52).addBox(-6.0F, 0.0F, 0.0F, 6.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(0, 68).addBox(-6.5F, 0.0F, -0.5F, 7.0F, 4.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition posters = extrusion.addOrReplaceChild("posters", CubeListBuilder.create(), PartPose.offset(17.3F, -32.0F, -1.5F));

		PartDefinition cube_r4 = posters.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(68, 26).addBox(0.0F, -1.0F, -1.0F, 0.0F, 16.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 0.0F, 0.0F, 0.0F, 0.0F, -0.0436F));

		PartDefinition planes = Console.addOrReplaceChild("planes", CubeListBuilder.create(), PartPose.offset(7.5F, 39.0F, -0.5F));

		PartDefinition cube_r5 = planes.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(0, 0).addBox(-2.0F, -2.0F, 0.0F, 4.0F, 4.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-17.5F, -20.6196F, 9.4973F, 0.5236F, 0.0F, 0.0F));

		PartDefinition cube_r6 = planes.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(22, 52).addBox(-3.0F, -3.0F, 0.0F, 6.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(2.0F, -0.25F, 6.5F, 0.0F, 0.0F, -0.0873F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}


	public void setupAnim(ConsoleBlockEntityRenderState console) {
		//? if >=1.21.4
		//super.setupAnim(console);
		fillant.visible = console.shouldShowFillant;

	}

	//? if <1.21.4 {
	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, float f, float g, float h, float k) {
		console.render(poseStack,vertexConsumer,i,j,f,g,h,k);
	}
	//? }
}