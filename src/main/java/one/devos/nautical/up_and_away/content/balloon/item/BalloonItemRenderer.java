package one.devos.nautical.up_and_away.content.balloon.item;

import com.mojang.math.Axis;

import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.BalloonModels;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.BalloonRenderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class BalloonItemRenderer implements DynamicItemRenderer {
	public static final BalloonItemRenderer INSTANCE = new BalloonItemRenderer();

	private BalloonModels models;

	@Override
	public void render(ItemStack stack, ItemDisplayContext mode, PoseStack matrices, MultiBufferSource buffers, int light, int overlay) {
		if (this.models == null) {
			this.models = new BalloonModels(Minecraft.getInstance().getEntityModels()::bakeLayer);
		}

		matrices.pushPose();
		matrices.translate(0, 1.5, 0);
		matrices.mulPose(Axis.XP.rotationDegrees(180));
		EntityModel<AbstractBalloon> model = this.models.choose(((BalloonItem) stack.getItem()).shape);
		VertexConsumer vertices = buffers.getBuffer(RenderType.entityTranslucent(BalloonRenderer.TEXTURE));
		int color = DyedItemColor.getOrDefault(stack, BalloonRenderer.DEFAULT_COLOR);
		model.renderToBuffer(matrices, vertices, light, overlay, color);
		matrices.popPose();
	}
}
