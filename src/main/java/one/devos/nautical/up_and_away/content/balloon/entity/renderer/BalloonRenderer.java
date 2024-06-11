package one.devos.nautical.up_and_away.content.balloon.entity.renderer;


import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ItemDisplayContext;

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
	}

	@Override
	public ResourceLocation getTextureLocation(AbstractBalloon balloon) {
		return InventoryMenu.BLOCK_ATLAS;
	}
}
