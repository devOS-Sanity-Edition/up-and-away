package one.devos.nautical.up_and_away.content.balloon.item;

import com.mojang.math.Axis;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.minecraft.client.model.geom.ModelPart;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.BalloonModels;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.BalloonRenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.model.AbstractBalloonModel;

public class BalloonItemRenderer implements DynamicItemRenderer {
	public static final BalloonItemRenderer INSTANCE = new BalloonItemRenderer();

	private static BalloonModels models;

	@Override
	public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource buffers, int light, int overlay) {
		matrices.pushPose();
		matrices.translate(0, 1.5, 0);
		matrices.mulPose(Axis.XP.rotationDegrees(180));
		renderBalloonFromItem(matrices, 1f, buffers, stack, light, overlay);
		matrices.popPose();
	}

	public static void renderBalloonFromItem(PoseStack matrices, float scale, MultiBufferSource buffers, ItemStack stack, int light, int overlay) {
		if (!(stack.getItem() instanceof BalloonItem balloonItem))
			return;

		if (models == null) {
			models = new BalloonModels(Minecraft.getInstance().getEntityModels()::bakeLayer);
		}

		VertexConsumer vertices = buffers.getBuffer(RenderType.entityTranslucent(BalloonRenderer.TEXTURE));
		int color = DyedItemColor.getOrDefault(stack, AbstractBalloon.DEFAULT_COLOR);
		AbstractBalloonModel model = models.choose(balloonItem.shape);
		ModelPart root = model.root();
		root.xScale = scale;
		root.yScale = scale;
		root.zScale = scale;
		model.renderToBuffer(matrices, vertices, light, overlay, color);
	}

	public static void renderInflatingBalloon(PoseStack matrices, MultiBufferSource buffers, ItemStack stack, Player player,
											  float partialTicks, float equipProgress, int light) {
		matrices.pushPose();

		int totalDuration = stack.getUseDuration(player);
		float remaining = player.getUseItemRemainingTicks() - Mth.lerp(partialTicks, 0, 1);
		float progress = 1 - (remaining / (float) totalDuration);

		// transform to center
		// based on applyItemArmTransform
		matrices.translate(0, -0.52, -2);
		// rotate
		matrices.mulPose(Axis.XP.rotationDegrees(90));

		BalloonItemRenderer.renderBalloonFromItem(matrices, progress, buffers, stack, light, OverlayTexture.NO_OVERLAY);
		matrices.popPose();
	}
}
