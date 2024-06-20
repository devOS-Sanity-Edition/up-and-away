package one.devos.nautical.up_and_away.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;

import com.mojang.math.Axis;

import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;

import net.minecraft.world.entity.LivingEntity;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import one.devos.nautical.up_and_away.content.UpAndAwayItems;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandLayer.class)
public class ItemInHandLayerMixin {
	@Shadow
	@Final
	private ItemInHandRenderer itemInHandRenderer;

	@Inject(
			method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/entity/layers/ItemInHandLayer;renderArmWithItem(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemDisplayContext;Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V",
					ordinal = 0,
					shift = At.Shift.BEFORE
			),
			cancellable = true
	)
	private void renderBalloonCartInHand(
			PoseStack matrices,
			MultiBufferSource vertexConsumers,
			int light,
			LivingEntity entity,
			float limbAngle,
			float limbDistance,
			float tickDelta,
			float animationProgress,
			float headYaw,
			float headPitch,
			CallbackInfo ci,
			@Local(ordinal = 0) ItemStack offHandStack,
			@Local(ordinal = 1) ItemStack mainHandStack
	) {
		boolean offHand = offHandStack.is(UpAndAwayItems.BALLOON_CART);
		boolean mainHand = mainHandStack.is(UpAndAwayItems.BALLOON_CART);
		if (entity instanceof Player && (offHand || mainHand)) {
			matrices.scale(2.0F, 2.0F, 2.0F);
			matrices.translate(0.5F, 0.5F, 0.5F);
			matrices.mulPose(Axis.YP.rotationDegrees(90));
			matrices.scale(-1.0F, -1.0F, 1.0F);
			matrices.translate(-0.7F, 0.33F, -0.2F);
			this.itemInHandRenderer.renderItem(
					entity,
					mainHand ? mainHandStack : offHandStack,
					ItemDisplayContext.NONE,
					false,
					matrices,
					vertexConsumers,
					light
			);
			matrices.popPose();
			ci.cancel();
		}
	}
}
