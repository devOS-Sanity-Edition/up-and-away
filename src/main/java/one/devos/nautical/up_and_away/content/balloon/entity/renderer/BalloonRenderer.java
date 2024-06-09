package one.devos.nautical.up_and_away.content.balloon.entity.renderer;

import com.mojang.math.Axis;

import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;

public class BalloonRenderer extends EntityRenderer<AbstractBalloon> {
	public static final ResourceLocation TEXTURE = UpAndAway.id("textures/entity/balloon/balloon.png");
	private final BalloonModels models;

	public BalloonRenderer(Context context) {
		super(context);
		this.models = new BalloonModels(context::bakeLayer);
	}

	@Override
	public void render(AbstractBalloon balloon, float yRot, float partialTicks, PoseStack matrices, MultiBufferSource buffers, int light) {
		super.render(balloon, yRot, partialTicks, matrices, buffers, light);
		EntityModel<AbstractBalloon> model = this.models.choose(balloon.shape());
		VertexConsumer vertices = buffers.getBuffer(RenderType.entityTranslucent(this.getTextureLocation(balloon)));
		matrices.pushPose();
		matrices.translate(0, 1.5, 0);
		matrices.mulPose(Axis.XP.rotationDegrees(180));
		model.renderToBuffer(matrices, vertices, light, OverlayTexture.NO_OVERLAY, balloon.color());
		matrices.popPose();
	}

	@Override
	public ResourceLocation getTextureLocation(AbstractBalloon balloon) {
		return TEXTURE;
	}
}
