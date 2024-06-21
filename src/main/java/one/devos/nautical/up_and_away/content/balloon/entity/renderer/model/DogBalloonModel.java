package one.devos.nautical.up_and_away.content.balloon.entity.renderer.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;

public class DogBalloonModel extends EntityModel<AbstractBalloon> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(UpAndAway.id("dog_balloon"), "main");

	private final ModelPart front_legs;
	private final ModelPart back_legs;
	private final ModelPart body;
	private final ModelPart head;
	private final ModelPart tail;

	public DogBalloonModel(ModelPart root) {
		this.front_legs = root.getChild("front_legs");
		this.back_legs = root.getChild("back_legs");
		this.body = root.getChild("body");
		this.head = root.getChild("head");
		this.tail = root.getChild("tail");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition front_legs = partdefinition.addOrReplaceChild("front_legs", CubeListBuilder.create(), PartPose.offset(1.5F, 20.5F, -6.225F));

		PartDefinition cube_r1 = front_legs.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(116, 40).addBox(-1.5F, -3.5F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 2.0F, 0.198F, -0.7902F, -0.0358F));

		PartDefinition cube_r2 = front_legs.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(116, 40).addBox(-1.5F, -3.5F, -1.5F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.5F, -1.0F, 2.0F, 0.1928F, -0.7626F, -0.231F));

		PartDefinition back_legs = partdefinition.addOrReplaceChild("back_legs", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.25F, 20.5F, 3.625F, -0.1876F, 0.0F, 0.0F));

		PartDefinition cube_r3 = back_legs.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(116, 40).addBox(-2.6656F, -4.8493F, -0.1506F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.75F, 0.0F, 0.0F, 0.0F, 0.7854F, -0.096F));

		PartDefinition cube_r4 = back_legs.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(116, 40).addBox(-0.3427F, -4.8481F, -0.1423F, 3.0F, 7.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.75F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.1047F));

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create(), PartPose.offset(-0.25F, 15.5F, 0.0F));

		PartDefinition cube_r5 = body.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(108, 55).addBox(-1.5F, -1.5F, -3.5F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -4.1329F, -6.3845F, -0.8423F, -0.641F, -0.4902F));

		PartDefinition cube_r6 = body.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(98, 40).addBox(-1.5F, -1.5F, -7.0F, 3.0F, 3.0F, 11.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 2.0F, 0.0F, 0.0F, -0.7854F));

		PartDefinition head = partdefinition.addOrReplaceChild("head", CubeListBuilder.create(), PartPose.offset(1.975F, 6.8486F, -8.3672F));

		PartDefinition cube_r7 = head.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(109, 56).addBox(-1.5F, -1.5F, -7.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.225F, 2.6514F, 2.3672F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r8 = head.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(108, 55).mirror().addBox(-1.5F, -1.5F, -3.5F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, -1.0F, 2.0F, -1.8805F, 0.761F, -0.1605F));

		PartDefinition cube_r9 = head.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(108, 55).addBox(-1.5F, -1.5F, -3.5F, 3.0F, 3.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-3.95F, -1.0F, 2.0F, -1.8805F, -0.761F, 0.1605F));

		PartDefinition tie = head.addOrReplaceChild("tie", CubeListBuilder.create(), PartPose.offset(-2.225F, 3.6514F, -7.6328F));

		PartDefinition cube_r10 = tie.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(124, 34).addBox(0.0F, -1.5F, -1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, 2.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition cube_r11 = tie.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(124, 34).addBox(-1.5F, -1.5F, 1.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.5F, 0.0F, 0.0F, 0.0F, -1.5708F));

		PartDefinition tail = partdefinition.addOrReplaceChild("tail", CubeListBuilder.create(), PartPose.offset(-0.25F, 13.0803F, 11.1168F));

		PartDefinition cube_r12 = tail.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(115, 33).addBox(0.0F, -0.5F, 0.25F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -1.0F, 0.0F, -0.5629F, 1.5708F));

		PartDefinition cube_r13 = tail.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(115, 33).addBox(0.0F, -0.5F, 0.25F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -1.0F, 0.5629F, 0.0F, 0.0F));

		PartDefinition cube_r14 = tail.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(92, 42).addBox(-1.5F, -1.5F, -4.75F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.0F, -1.0F, 0.4196F, -0.3869F, 0.702F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(AbstractBalloon entity, float f, float g, float h, float i, float j) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, int k) {
		front_legs.render(poseStack, vertexConsumer, i, j, k);
		back_legs.render(poseStack, vertexConsumer, i, j, k);
		body.render(poseStack, vertexConsumer, i, j, k);
		head.render(poseStack, vertexConsumer, i, j, k);
		tail.render(poseStack, vertexConsumer, i, j, k);
	}
}
