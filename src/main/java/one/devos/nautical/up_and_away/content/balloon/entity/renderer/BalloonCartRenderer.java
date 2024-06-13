package one.devos.nautical.up_and_away.content.balloon.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.content.balloon.entity.BalloonCart;

public class BalloonCartRenderer extends EntityRenderer<BalloonCart> {
	public static final ResourceLocation TEXTURE = UpAndAway.id("textures/entity/balloon_cart/balloon_cart.png");
	private final BalloonCartModel model;

	public BalloonCartRenderer(EntityRendererProvider.Context context) {
		super(context);
		this.model = new BalloonCartModel(context.bakeLayer(BalloonCartModel.LAYER_LOCATION));
	}

	@Override
	public void render(BalloonCart cart, float yRot, float partialTicks, PoseStack matrices, MultiBufferSource buffers, int light) {
		matrices.pushPose();
		matrices.scale(-1.0F, -1.0F, 1.0F);
		matrices.translate(0.0F, -1.501F, 0.0F);
		RenderType renderType = this.model.renderType(this.getTextureLocation(cart));
		VertexConsumer buffer = buffers.getBuffer(renderType);
		this.model.renderToBuffer(matrices, buffer, light, OverlayTexture.NO_OVERLAY);
		matrices.popPose();
		super.render(cart, yRot, partialTicks, matrices, buffers, light);
	}

	@Override
	public ResourceLocation getTextureLocation(BalloonCart cart) {
		return TEXTURE;
	}
}
