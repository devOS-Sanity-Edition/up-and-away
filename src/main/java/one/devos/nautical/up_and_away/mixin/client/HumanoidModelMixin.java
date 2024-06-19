package one.devos.nautical.up_and_away.mixin.client;

import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;

import net.minecraft.client.model.HumanoidModel;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

import one.devos.nautical.up_and_away.content.UpAndAwayItems;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HumanoidModel.class)
public class HumanoidModelMixin {
	@Shadow
	@Final
	public ModelPart rightArm;

	@Shadow
	@Final
	public ModelPart leftArm;

	@Shadow
	public boolean crouching;

	@Unique
	private boolean holdingBalloonCart;

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
	private void balloonCartHoldingAnim(
			LivingEntity entity,
			float limbAngle,
			float limbDistance,
			float animationProgress,
			float headYaw,
			float headPitch,
			CallbackInfo ci
	) {
		this.holdingBalloonCart = false;
		if (entity.getMainHandItem().is(UpAndAwayItems.BALLOON_CART) || entity.getOffhandItem().is(UpAndAwayItems.BALLOON_CART)) {
			this.crouching = true;
			this.holdingBalloonCart = true;
		}
	}

	@WrapWithCondition(
			method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/model/AnimationUtils;bobModelPart(Lnet/minecraft/client/model/geom/ModelPart;FF)V"
			)
	)
	private boolean dontBobArmsIfHoldingBalloonCart(ModelPart arm, float animationProgress, float sigma) {
		return !this.holdingBalloonCart;
	}

	@Inject(method = "poseRightArm", at = @At("TAIL"))
	private void balloonCartHoldingAnimRightArm(LivingEntity entity, CallbackInfo ci) {
		if (this.holdingBalloonCart) {
			this.rightArm.xRot = -170 * Mth.DEG_TO_RAD;
		}
	}

	@Inject(method = "poseLeftArm", at = @At("TAIL"))
	private void balloonCartHoldingAnimLeftArm(LivingEntity entity, CallbackInfo ci) {
		if (this.holdingBalloonCart) {
			this.leftArm.xRot = -170 * Mth.DEG_TO_RAD;
		}
	}
}
