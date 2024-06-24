package one.devos.nautical.up_and_away.mixin.client;

import java.util.Map;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import com.llamalad7.mixinextras.sugar.Local;

import com.llamalad7.mixinextras.sugar.ref.LocalRef;

import com.mojang.math.Axis;

import one.devos.nautical.up_and_away.content.UpAndAwayItems;
import one.devos.nautical.up_and_away.content.balloon.item.BalloonItem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

@Mixin(ItemInHandRenderer.class)
public class ItemInHandRendererMixin {
	@WrapOperation(
			method = "renderArmWithItem",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/ItemInHandRenderer;applyItemArmTransform(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/world/entity/HumanoidArm;F)V",
					ordinal = 2 // first one in use animation switch
			)
	)
	private void handleBalloonInflate(ItemInHandRenderer instance, PoseStack matrices, HumanoidArm arm, float equipProgress, Operation<Void> original,
									  @Local(argsOnly = true) LocalRef<ItemStack> item,
									  @Local(argsOnly = true) AbstractClientPlayer player) {
		ItemStack stack = item.get();
		if (!(stack.getItem() instanceof BalloonItem balloonItem) || !UpAndAwayItems.DEFLATED.contains(balloonItem)) {
			original.call(instance, matrices, arm, equipProgress);
			return;
		}

		// deflated balloon item, get inflated version
		Item inflatedItem = UpAndAwayItems.AIR.get(balloonItem.shape);
		ItemStack inflatedStack = new ItemStack(inflatedItem);
		inflatedStack.applyComponents(stack.getComponents());
		item.set(inflatedStack);

		int mult = arm == HumanoidArm.RIGHT ? -1 : 1;
		int totalDuration = balloonItem.getUseDuration(stack, player);
		int remaining = player.getUseItemRemainingTicks();
		float progress = 1 - (remaining / (float) totalDuration);
		// FIXME: why does scale do nothing
		matrices.scale(progress, progress, progress);
		matrices.mulPose(Axis.XN.rotationDegrees(60));
		matrices.translate(mult, 0.5, 0);
		original.call(instance, matrices, arm, equipProgress);
	}
}
