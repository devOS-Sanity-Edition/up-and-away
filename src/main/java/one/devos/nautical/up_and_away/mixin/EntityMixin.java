package one.devos.nautical.up_and_away.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;

import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

@Mixin(Entity.class)
public abstract class EntityMixin {
	@Shadow
	public abstract Vec3 position();

	@ModifyExpressionValue(
			method = "move",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/Entity;maybeBackOffFromEdge(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/entity/MoverType;)Lnet/minecraft/world/phys/Vec3;"
			)
	)
	private Vec3 balloonStringCollision(Vec3 motion) {
		if (!((Object) this instanceof AbstractBalloon self))
			return motion;

		BalloonAttachment attachment = self.attachment();
		if (attachment == null)
			return motion;

		Vec3 pos = this.position();
		if (attachment.isTooFar(pos)) {
			// let logic in tick handle it
			return motion;
		}

		Vec3 nextPos = pos.add(motion);
		if (!attachment.isTooFar(nextPos)) {
			// all is well
			return motion;
		}

		// next position is too far
		Vec3 attachmentToNext = attachment.getPos().vectorTo(nextPos);
		Vec3 atMaxLength = attachmentToNext.normalize().scale(attachment.stringLength - 0.01); // a little shorter, to avoid float imprecision
		return pos.vectorTo(attachment.getPos().add(atMaxLength));
	}
}
