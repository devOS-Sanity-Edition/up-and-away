package one.devos.nautical.up_and_away.content.balloon.entity.renderer.model;

import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import one.devos.nautical.up_and_away.UpAndAway;

public class HeartBalloonModel extends AbstractBalloonModel {
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(UpAndAway.id("heart_balloon"), "main");

	private final ModelPart balloon;

	public HeartBalloonModel(ModelPart root) {
		this.balloon = root.getChild("balloon");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition balloon = partdefinition.addOrReplaceChild("balloon", CubeListBuilder.create().texOffs(64, 0).addBox(-16.0607F, -9.0607F, -4.0F, 15.0F, 8.0F, 8.0F, new CubeDeformation(0.0F))
				.texOffs(64, 17).addBox(-9.0607F, -16.0607F, -4.0F, 8.0F, 7.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition tie = balloon.addOrReplaceChild("tie", CubeListBuilder.create(), PartPose.offsetAndRotation(-0.8309F, -0.7905F, 0.5F, 0.0F, 0.0F, -0.7854F));

		PartDefinition cube_r1 = tie.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(64, -3).mirror().addBox(0.0F, -1.0F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, -0.7854F, 0.0F));

		PartDefinition cube_r2 = tie.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(64, -3).addBox(0.0F, -1.0F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -0.5F, 0.0F, 0.7854F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public ModelPart root() {
		return this.balloon;
	}
}
