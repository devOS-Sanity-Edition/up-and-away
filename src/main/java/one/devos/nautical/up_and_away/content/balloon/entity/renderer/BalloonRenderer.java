package one.devos.nautical.up_and_away.content.balloon.entity.renderer;


import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;

import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;

import org.joml.Matrix4f;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.phys.Vec3;

public class BalloonRenderer extends EntityRenderer<AbstractBalloon> {
	private final ItemRenderer itemRenderer;

	public BalloonRenderer(Context context) {
		super(context);
		this.itemRenderer = context.getItemRenderer();
	}

	@Override
	public void render(AbstractBalloon balloon, float yRot, float partialTicks, PoseStack matrices, MultiBufferSource buffers, int light) {
		super.render(balloon, yRot, partialTicks, matrices, buffers, light);
		matrices.pushPose();
		matrices.scale(2, 2, 2);
		this.itemRenderer.renderStatic(
				balloon.item(),
				ItemDisplayContext.GROUND,
				light, OverlayTexture.NO_OVERLAY,
				matrices, buffers,
				null, 0
		);
		matrices.popPose();

		BalloonAttachment attachment = balloon.attachment();
		if (attachment != null) {
			this.renderString(balloon, partialTicks, matrices, buffers, attachment.getPos());
		}
	}

	@Override
	public ResourceLocation getTextureLocation(AbstractBalloon balloon) {
		return InventoryMenu.BLOCK_ATLAS;
	}

	// based on EntityRenderer lead rendering

	private void renderString(AbstractBalloon balloon, float partialTicks, PoseStack matrices, MultiBufferSource buffers, Vec3 target) {
		matrices.pushPose();
		double rot = (balloon.getPreciseBodyRotation(partialTicks) * (Math.PI / 180.0)) + (Math.PI / 2);
		Vec3 relativeStringOffset = balloon.getLeashOffset(partialTicks);
		double stringOffsetX = Math.cos(rot) * relativeStringOffset.z + Math.sin(rot) * relativeStringOffset.x;
		double stringOffsetZ = Math.sin(rot) * relativeStringOffset.z - Math.cos(rot) * relativeStringOffset.x;
		double targetX = Mth.lerp(partialTicks, balloon.xo, balloon.getX()) + stringOffsetX;
		double targetY = Mth.lerp(partialTicks, balloon.yo, balloon.getY()) + relativeStringOffset.y;
		double targetZ = Mth.lerp(partialTicks, balloon.zo, balloon.getZ()) + stringOffsetZ;
		matrices.translate(stringOffsetX, relativeStringOffset.y, stringOffsetZ);
		float dx = (float) (target.x - targetX);
		float dy = (float) (target.y - targetY);
		float dz = (float) (target.z - targetZ);
		float n = 0.025F;
		VertexConsumer vertices = buffers.getBuffer(RenderType.leash());
		Matrix4f matrix4f = matrices.last().pose();
		float o = Mth.invSqrt(dx * dx + dz * dz) * n / 2.0F;
		float p = dz * o;
		float q = dx * o;
		BlockPos blockPos = BlockPos.containing(balloon.getEyePosition(partialTicks));
		BlockPos blockPos2 = BlockPos.containing(target);
		int r = this.getBlockLightLevel(balloon, blockPos);
		int s = balloon.level().getBrightness(LightLayer.BLOCK, blockPos);
		int t = balloon.level().getBrightness(LightLayer.SKY, blockPos);
		int u = balloon.level().getBrightness(LightLayer.SKY, blockPos2);

		for(int v = 0; v <= 24; ++v) {
			addVertexPair(vertices, matrix4f, dx, dy, dz, r, s, t, u, n, n, p, q, v, 0xFFFFFFFF, 0xEEEEEEEE);
		}

		for(int v = 24; v >= 0; --v) {
			addVertexPair(vertices, matrix4f, dx, dy, dz, r, s, t, u, n, 0.0F, p, q, v, 0xEEEEEEEE, 0xFFFFFFFF);
		}

		matrices.popPose();
	}

	private static void addVertexPair(
			VertexConsumer vertexConsumer,
			Matrix4f matrix4f,
			float dx,
			float dy,
			float dz,
			int i,
			int j,
			int k,
			int l,
			float m,
			float n,
			float o,
			float p,
			int index,
			int colorA, int colorB
	) {
		float progress = index / 24f;
		int s = (int) Mth.lerp(progress, i, j);
		int t = (int) Mth.lerp(progress, k, l);
		int u = LightTexture.pack(s, t);
		int color = index % 2 == 0 ? colorA : colorB;
		float r = FastColor.ARGB32.red(color) / 255f;
		float g = FastColor.ARGB32.green(color) / 255f;
		float b = FastColor.ARGB32.blue(color) / 255f;
		float z = dx * progress;
		float aa = dy > 0.0F ? dy * progress * progress : dy - dy * (1.0F - progress) * (1.0F - progress);
		float ab = dz * progress;
		vertexConsumer.addVertex(matrix4f, z - o, aa + n, ab + p).setColor(r, g, b, 1.0F).setLight(u);
		vertexConsumer.addVertex(matrix4f, z + o, aa + m - n, ab - p).setColor(r, g, b, 1.0F).setLight(u);
	}
}
