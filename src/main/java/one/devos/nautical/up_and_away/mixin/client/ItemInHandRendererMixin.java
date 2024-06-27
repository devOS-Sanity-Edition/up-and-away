package one.devos.nautical.up_and_away.mixin.client;

import one.devos.nautical.up_and_away.content.UpAndAwayItems;

import one.devos.nautical.up_and_away.content.balloon.item.BalloonItemRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {
	@Inject(
			method = "renderArmWithItem",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/UseAnim;"
			),
			cancellable = true
	)
	private void handleBalloonInflate(AbstractClientPlayer player, float tickDelta, float pitch, InteractionHand hand, float swingProgress,
									  ItemStack stack, float equipProgress, PoseStack matrices, MultiBufferSource buffers, int light,
									  CallbackInfo ci) {

		if (UpAndAwayItems.DEFLATED.contains(stack.getItem())) {
			BalloonItemRenderer.renderInflatingBalloon(matrices, buffers, stack, player, tickDelta, light);
			ci.cancel();
		}
	}
}
