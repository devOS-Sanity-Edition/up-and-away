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

public class CubeBalloonModel extends EntityModel<AbstractBalloon> {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(UpAndAway.id("cube_balloon"), "main");

	private final ModelPart balloon;
	private final ModelPart tie;

	public CubeBalloonModel(ModelPart root) {
		this.balloon = root.getChild("balloon");
		this.tie = root.getChild("tie");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition balloon = partdefinition.addOrReplaceChild("balloon", CubeListBuilder.create().texOffs(0, 34).addBox(-7.0F, -14.0F, -7.0F, 14.0F, 14.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 22.0F, 0.0F));

		PartDefinition tie = partdefinition.addOrReplaceChild("tie", CubeListBuilder.create(), PartPose.offset(0.0F, 23.0F, 0.0F));

		PartDefinition cube_r1 = tie.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(0, 34).mirror().addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r2 = tie.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(0, 34).addBox(-1.5F, -1.0F, 0.0F, 3.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}


	@Override
	public void setupAnim(AbstractBalloon entity, float f, float g, float h, float i, float j) {
	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, int k) {
		balloon.render(poseStack, vertexConsumer, i, j, k);
		tie.render(poseStack, vertexConsumer, i, j, k);
	}
}
