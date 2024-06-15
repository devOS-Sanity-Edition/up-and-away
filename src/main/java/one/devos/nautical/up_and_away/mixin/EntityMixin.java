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
					target = "Lnet/minecraft/world/entity/Entity;collide(Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;"
			)
	)
	private Vec3 balloonStringCollision(Vec3 motion) {
		if (!((Object) this instanceof AbstractBalloon self))
			return motion;

		BalloonAttachment attachment = self.attachment();
		if (attachment == null)
			return motion;

		if (this.position().distanceTo(attachment.getPos()) > attachment.getStringLength()) {
			// current position too far, let logic in tick handle it
			return motion;
		}

		Vec3 target = this.position().add(motion);
		Vec3 delta = target.vectorTo(attachment.getPos());
		if (delta.length() > attachment.getStringLength()) {
			// next pos will be too far away
			return Vec3.ZERO;
		}

		// all is well
		return motion;
	}
}
