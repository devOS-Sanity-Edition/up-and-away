package one.devos.nautical.up_and_away.content.balloon.entity.renderer;
// Made with Blockbench 4.10.3
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


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
import one.devos.nautical.up_and_away.content.balloon.entity.BalloonCart;

public class BalloonCartModel extends EntityModel<BalloonCart> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(UpAndAway.id("balloon_cart"), "main");
	private final ModelPart body;
	private final ModelPart balloon_ties;
	private final ModelPart helium_tanks;
	private final ModelPart right_tank;
	private final ModelPart right_valve;
	private final ModelPart left_tank;
	private final ModelPart left_valve;
	private final ModelPart wheels;
	private final ModelPart left_wheel;
	private final ModelPart right_wheel;
	private final ModelPart middle_wheel;
	private final ModelPart swivel;
	private final ModelPart wheel;
	private final ModelPart crafting_table;
	private final ModelPart table;
	private final ModelPart supports;
	private final ModelPart bottom;
	private final ModelPart top;
	private final ModelPart chest;
	private final ModelPart lid;
	private final ModelPart top_hatches;
	private final ModelPart hinges;
	private final ModelPart left_hatch;
	private final ModelPart right_hatch;
	private final ModelPart center_decoration;

	public BalloonCartModel(ModelPart root) {
		this.body = root.getChild("body");
		this.balloon_ties = this.body.getChild("balloon_ties");
		this.helium_tanks = this.body.getChild("helium_tanks");
		this.right_tank = this.helium_tanks.getChild("right_tank");
		this.right_valve = this.right_tank.getChild("right_valve");
		this.left_tank = this.helium_tanks.getChild("left_tank");
		this.left_valve = this.left_tank.getChild("left_valve");
		this.wheels = this.body.getChild("wheels");
		this.left_wheel = this.wheels.getChild("left_wheel");
		this.right_wheel = this.wheels.getChild("right_wheel");
		this.middle_wheel = this.wheels.getChild("middle_wheel");
		this.swivel = this.middle_wheel.getChild("swivel");
		this.wheel = this.swivel.getChild("wheel");
		this.crafting_table = this.body.getChild("crafting_table");
		this.table = this.crafting_table.getChild("table");
		this.supports = this.crafting_table.getChild("supports");
		this.bottom = this.supports.getChild("bottom");
		this.top = this.bottom.getChild("top");
		this.chest = this.body.getChild("chest");
		this.lid = this.chest.getChild("lid");
		this.top_hatches = this.body.getChild("top_hatches");
		this.hinges = this.top_hatches.getChild("hinges");
		this.left_hatch = this.top_hatches.getChild("left_hatch");
		this.right_hatch = this.top_hatches.getChild("right_hatch");
		this.center_decoration = this.body.getChild("center_decoration");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition body = partdefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 0).addBox(-12.9688F, -4.8797F, -13.0597F, 28.0F, 17.0F, 44.0F, new CubeDeformation(0.0F))
				.texOffs(0, 93).addBox(-12.9688F, 10.1203F, -25.0597F, 28.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 108).addBox(-12.9688F, -4.8797F, -25.0597F, 28.0F, 2.0F, 12.0F, new CubeDeformation(0.0F))
				.texOffs(0, 68).addBox(-12.9688F, -1.8797F, -12.0597F, 12.0F, 12.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(-1.0313F, 6.3797F, -8.9403F));

		PartDefinition handle_r1 = body.addOrReplaceChild("handle_r1", CubeListBuilder.create().texOffs(69, 91).addBox(-12.5F, 2.7208F, -5.1312F, 25.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0313F, -3.7222F, -25.2503F, -1.1781F, 0.0F, 0.0F));

		PartDefinition handle_r2 = body.addOrReplaceChild("handle_r2", CubeListBuilder.create().texOffs(63, 98).addBox(-10.5F, -2.5355F, -8.8126F, 28.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-2.4687F, -0.4465F, -23.2095F, -0.3927F, 0.0F, 0.0F));

		PartDefinition balloon_ties = body.addOrReplaceChild("balloon_ties", CubeListBuilder.create().texOffs(19, 34).addBox(-14.0F, -13.9F, 20.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(1.0313F, 6.0203F, 8.9403F));

		PartDefinition cube_r1 = balloon_ties.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(19, 34).addBox(-1.0F, -1.4F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, -12.5F, 21.0F, 0.0F, 1.5708F, 0.0F));

		PartDefinition cube_r2 = balloon_ties.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(19, 34).addBox(-1.0F, -1.4F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-13.0F, -12.5F, -21.0F, 0.0F, -1.5708F, 0.0F));

		PartDefinition cube_r3 = balloon_ties.addOrReplaceChild("cube_r3", CubeListBuilder.create().texOffs(19, 34).addBox(-1.0F, -1.4F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(13.0F, -12.5F, -21.0F, 0.0F, 3.1416F, 0.0F));

		PartDefinition helium_tanks = body.addOrReplaceChild("helium_tanks", CubeListBuilder.create(), PartPose.offset(-5.9688F, -12.8797F, -18.8097F));

		PartDefinition right_tank = helium_tanks.addOrReplaceChild("right_tank", CubeListBuilder.create(), PartPose.offset(13.0F, 0.0F, 0.175F));

		PartDefinition cube_r4 = right_tank.addOrReplaceChild("cube_r4", CubeListBuilder.create().texOffs(92, 123).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5176F, 1.0F, -1.9319F, -2.3562F, -1.309F, 3.1416F));

		PartDefinition cube_r5 = right_tank.addOrReplaceChild("cube_r5", CubeListBuilder.create().texOffs(92, 123).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5176F, 1.0F, -1.9319F, 2.3562F, -1.309F, 3.1416F));

		PartDefinition cube_r6 = right_tank.addOrReplaceChild("cube_r6", CubeListBuilder.create().texOffs(108, 110).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -1.8326F, 0.0F));

		PartDefinition cube_r7 = right_tank.addOrReplaceChild("cube_r7", CubeListBuilder.create().texOffs(92, 111).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F))
				.texOffs(150, 96).addBox(-4.0F, 1.0F, -4.0F, 8.0F, 18.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, -1.0472F, 0.0F));

		PartDefinition right_valve = right_tank.addOrReplaceChild("right_valve", CubeListBuilder.create(), PartPose.offset(0.0F, -1.1F, 0.0F));

		PartDefinition cube_r8 = right_valve.addOrReplaceChild("cube_r8", CubeListBuilder.create().texOffs(98, 103).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.0F, 0.0F, 0.0F, -2.618F, 0.0F));

		PartDefinition left_tank = helium_tanks.addOrReplaceChild("left_tank", CubeListBuilder.create(), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 2.2209F, 0.0F));

		PartDefinition cube_r9 = left_tank.addOrReplaceChild("cube_r9", CubeListBuilder.create().texOffs(92, 123).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5176F, 1.0F, -1.9319F, -2.3562F, -1.309F, 3.1416F));

		PartDefinition cube_r10 = left_tank.addOrReplaceChild("cube_r10", CubeListBuilder.create().texOffs(92, 123).addBox(-1.0F, -1.0F, 0.0F, 2.0F, 2.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5176F, 1.0F, -1.9319F, 2.3562F, -1.309F, 3.1416F));

		PartDefinition cube_r11 = left_tank.addOrReplaceChild("cube_r11", CubeListBuilder.create().texOffs(108, 110).addBox(-1.0F, -3.0F, -1.0F, 2.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.0F, 0.0F, 0.0F, -1.8326F, 0.0F));

		PartDefinition cube_r12 = left_tank.addOrReplaceChild("cube_r12", CubeListBuilder.create().texOffs(92, 120).addBox(-3.0F, -1.0F, -3.0F, 6.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 4.0F, 0.0F, 0.0F, -2.618F, 0.0F));

		PartDefinition cube_r13 = left_tank.addOrReplaceChild("cube_r13", CubeListBuilder.create().texOffs(117, 96).addBox(-4.0F, -6.0F, -4.0F, 8.0F, 18.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.0F, 0.0F, 0.0F, -2.618F, 0.0F));

		PartDefinition left_valve = left_tank.addOrReplaceChild("left_valve", CubeListBuilder.create(), PartPose.offset(0.0F, -1.1F, 0.0F));

		PartDefinition cube_r14 = left_valve.addOrReplaceChild("cube_r14", CubeListBuilder.create().texOffs(98, 103).addBox(-3.0F, -1.1F, -3.0F, 6.0F, 0.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.1F, 0.0F, 0.0F, -2.618F, 0.0F));

		PartDefinition wheels = body.addOrReplaceChild("wheels", CubeListBuilder.create(), PartPose.offset(1.0313F, 20.1203F, 8.9403F));

		PartDefinition left_wheel = wheels.addOrReplaceChild("left_wheel", CubeListBuilder.create().texOffs(0, -16).addBox(0.0F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)), PartPose.offset(-14.25F, -10.5F, 11.5F));

		PartDefinition right_wheel = wheels.addOrReplaceChild("right_wheel", CubeListBuilder.create(), PartPose.offset(14.25F, -10.5F, 11.5F));

		PartDefinition cube_r15 = right_wheel.addOrReplaceChild("cube_r15", CubeListBuilder.create().texOffs(0, -16).mirror().addBox(-0.5F, -8.0F, -8.0F, 0.0F, 16.0F, 16.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(0.5F, 0.0F, 0.0F, 1.5708F, 0.0F, 0.0F));

		PartDefinition middle_wheel = wheels.addOrReplaceChild("middle_wheel", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, -2.0F));

		PartDefinition cube_r16 = middle_wheel.addOrReplaceChild("cube_r16", CubeListBuilder.create().texOffs(0, 34).addBox(-3.0F, -0.5F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -7.5F, -26.0F, 0.0F, 0.7854F, 0.0F));

		PartDefinition swivel = middle_wheel.addOrReplaceChild("swivel", CubeListBuilder.create().texOffs(0, 27).addBox(-0.5F, 0.25F, -1.5F, 1.0F, 3.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, -7.25F, -26.0F));

		PartDefinition wheel = swivel.addOrReplaceChild("wheel", CubeListBuilder.create().texOffs(1, 14).addBox(0.0F, -2.0F, -2.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 2.75F, -1.0F));

		PartDefinition crafting_table = body.addOrReplaceChild("crafting_table", CubeListBuilder.create(), PartPose.offset(-12.9688F, -2.8797F, 15.4403F));

		PartDefinition table = crafting_table.addOrReplaceChild("table", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition cube_r17 = table.addOrReplaceChild("cube_r17", CubeListBuilder.create().texOffs(145, 6).addBox(-1.0F, 0.0F, -14.5F, 1.0F, 12.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.05F, 1.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition cube_r18 = table.addOrReplaceChild("cube_r18", CubeListBuilder.create().texOffs(113, 0).addBox(-1.0F, 0.0F, -14.5F, 1.0F, 12.0F, 29.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.05F, 0.0F, 0.0F, 0.0F, 0.0F, 1.5708F));

		PartDefinition supports = crafting_table.addOrReplaceChild("supports", CubeListBuilder.create(), PartPose.offset(0.0F, 8.25F, 0.5F));

		PartDefinition bottom = supports.addOrReplaceChild("bottom", CubeListBuilder.create(), PartPose.offset(0.0F, 2.25F, -28.0F));

		PartDefinition cube_r19 = bottom.addOrReplaceChild("cube_r19", CubeListBuilder.create().texOffs(103, 35).addBox(-0.8017F, -0.1522F, 13.5F, 1.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(103, 35).addBox(-0.8017F, -0.1522F, 41.5F, 1.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 2.3126F));

		PartDefinition top = bottom.addOrReplaceChild("top", CubeListBuilder.create(), PartPose.offset(-5.5813F, -5.5267F, 27.55F));

		PartDefinition cube_r20 = top.addOrReplaceChild("cube_r20", CubeListBuilder.create().texOffs(101, 35).addBox(-0.8017F, 7.7978F, 13.5F, 1.0F, 8.0F, 0.0F, new CubeDeformation(0.0F))
				.texOffs(101, 35).addBox(-0.8017F, 7.7978F, 41.45F, 1.0F, 8.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(5.5813F, 5.5267F, -27.525F, 0.0F, 0.0F, 2.3126F));

		PartDefinition chest = body.addOrReplaceChild("chest", CubeListBuilder.create().texOffs(50, 68).addBox(-0.5F, -7.0F, -10.5F, 11.0F, 7.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(95, 87).mirror().addBox(-0.75F, -0.75F, -0.25F, 22.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false)
				.texOffs(95, 84).addBox(-0.75F, -0.75F, -10.75F, 22.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(-11.9688F, 9.6203F, -1.0597F));

		PartDefinition lid = chest.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(95, 68).addBox(-11.0F, -4.0F, -5.5F, 11.0F, 4.0F, 11.0F, new CubeDeformation(0.0F))
				.texOffs(54, 72).addBox(-12.0F, -2.0F, -1.0F, 1.0F, 4.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(10.5F, -7.0F, -5.0F));

		PartDefinition top_hatches = body.addOrReplaceChild("top_hatches", CubeListBuilder.create(), PartPose.offset(1.0313F, 6.1203F, 8.9403F));

		PartDefinition hinges = top_hatches.addOrReplaceChild("hinges", CubeListBuilder.create(), PartPose.offset(9.0F, -11.0F, 6.0F));

		PartDefinition body_r1 = hinges.addOrReplaceChild("body_r1", CubeListBuilder.create().texOffs(11, 29).addBox(-0.5F, -0.5F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(11, 29).addBox(-0.5F, -0.5F, -10.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(11, 29).addBox(-0.5F, -0.5F, 9.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F))
				.texOffs(11, 29).addBox(-0.5F, -0.5F, 1.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -1.0F, 0.0F, 0.0F, 0.7854F));

		PartDefinition left_hatch = top_hatches.addOrReplaceChild("left_hatch", CubeListBuilder.create().texOffs(89, 0).addBox(-20.0F, 0.25F, -6.0F, 20.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, -11.25F, 12.0F));

		PartDefinition body_r2 = left_hatch.addOrReplaceChild("body_r2", CubeListBuilder.create().texOffs(0, 34).addBox(-5.6265F, -0.75F, 4.6265F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.25F, 0.0F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition right_hatch = top_hatches.addOrReplaceChild("right_hatch", CubeListBuilder.create().texOffs(89, 0).addBox(-20.0F, 0.25F, -6.0F, 20.0F, 0.0F, 12.0F, new CubeDeformation(0.0F)), PartPose.offset(9.0F, -11.25F, 0.0F));

		PartDefinition body_r3 = right_hatch.addOrReplaceChild("body_r3", CubeListBuilder.create().texOffs(0, 34).addBox(-0.5F, -0.5F, -0.5F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-18.5F, -0.25F, 0.0F, 0.0F, -0.7854F, 0.0F));

		PartDefinition center_decoration = body.addOrReplaceChild("center_decoration", CubeListBuilder.create().texOffs(168, 18).addBox(-16.125F, -6.925F, -4.475F, 20.0F, 15.0F, 24.0F, new CubeDeformation(0.0F)), PartPose.offset(6.1563F, 2.0453F, 7.4153F));

		PartDefinition brush_r1 = center_decoration.addOrReplaceChild("brush_r1", CubeListBuilder.create().texOffs(214, 0).addBox(-7.0F, 0.0F, -7.0F, 14.0F, 0.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, -1.6868F, -0.7429F, 0.9846F));

		PartDefinition yellow_paint_can_r1 = center_decoration.addOrReplaceChild("yellow_paint_can_r1", CubeListBuilder.create().texOffs(232, 59).addBox(-3.0F, -3.5F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.125F, 4.575F, 15.025F, 0.0F, 0.0F, 0.0F));

		PartDefinition red_paint_can_r1 = center_decoration.addOrReplaceChild("red_paint_can_r1", CubeListBuilder.create().texOffs(232, 16).addBox(-3.0F, -3.5F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-11.875F, 4.575F, 0.025F, 0.0F, -1.1781F, 0.0F));

		PartDefinition blue_paint_can_r1 = center_decoration.addOrReplaceChild("blue_paint_can_r1", CubeListBuilder.create().texOffs(232, 29).addBox(-3.0F, -3.5F, -3.0F, 6.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-12.125F, 4.575F, 11.525F, 0.0F, -0.3927F, 0.0F));

		return LayerDefinition.create(meshdefinition, 256, 128);
	}

	@Override
	public void setupAnim(BalloonCart entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int i, int j, int k) {
		this.body.render(poseStack, vertexConsumer, i, j, k);
	}
}
