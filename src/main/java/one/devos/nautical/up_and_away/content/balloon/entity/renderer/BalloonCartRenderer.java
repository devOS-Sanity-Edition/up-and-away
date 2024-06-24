package one.devos.nautical.up_and_away.content.balloon.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.blaze3d.vertex.VertexConsumer;

import com.mojang.math.Axis;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.content.balloon.entity.BalloonCart;
import one.devos.nautical.up_and_away.content.balloon.entity.BalloonCartInteractable;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.model.BalloonCartModel;

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
		matrices.mulPose(Axis.YP.rotationDegrees(180 - yRot));
		if (this.entityRenderDispatcher.shouldRenderHitBoxes() && !Minecraft.getInstance().showOnlyReducedInfo()) {
			for (BalloonCartInteractable interaction : BalloonCartInteractable.VALUES) {
				Vec3 color = interaction.debugColor;
				LevelRenderer.renderLineBox(
						matrices,
						buffers.getBuffer(RenderType.LINES),
						interaction.hitBox,
						(float) (color.x / 255),
						(float) (color.y / 255),
						(float) (color.z / 255),
						1f
				);
			}
		}
		matrices.scale(-1.0F, -1.0F, 1.0F);
		matrices.translate(0.0F, -1.501F, 0.0F);
		this.model.setupAnim(cart, 0, 0, cart.tickCount + partialTicks, 0, 0);
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
