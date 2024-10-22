package one.devos.nautical.up_and_away.mixin;

import java.util.ArrayList;
import java.util.List;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;

import net.minecraft.world.entity.EntityDimensions;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;

import one.devos.nautical.up_and_away.content.balloon.entity.FloatyBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;

import one.devos.nautical.up_and_away.content.balloon.entity.attachment.EntityBalloonAttachment;
import one.devos.nautical.up_and_away.framework.entity.SometimesSerializableEntity;

import one.devos.nautical.up_and_away.framework.ext.EntityExt;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityExt {
	@Shadow
	public abstract Vec3 position();

	@Unique
	private int floatyBalloons = 0;
	@Unique
	private final List<AbstractBalloon> balloons = new ArrayList<>();

	@ModifyExpressionValue(
			method = "<init>",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/EntityType;getDimensions()Lnet/minecraft/world/entity/EntityDimensions;"
			)
	)
	private EntityDimensions balloonDefaultDimensions(EntityDimensions dimensions) {
		if ((Object) this instanceof AbstractBalloon)
			return AbstractBalloon.DEFAULT_SHAPE.dimensions;
		return dimensions;
	}

	@ModifyExpressionValue(
			method = "getEncodeId",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/EntityType;canSerialize()Z"
			)
	)
	private boolean sometimesSerializable(boolean original) {
		if (this instanceof SometimesSerializableEntity entity)
			return entity.isSerializable();
		return original;
	}

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
		Vec3 nextPos = pos.add(motion);

		if (!attachment.isTooFar(nextPos)) {
			// all is well
			return motion;
		}

		// next position is too far
		Vec3 attachmentToNext = attachment.getPos().vectorTo(nextPos);
		Vec3 atMaxLength = attachmentToNext.normalize().scale(attachment.stringLength);
		return pos.vectorTo(attachment.getPos().add(atMaxLength));
	}

	@ModifyExpressionValue(
			method = "getGravity",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/world/entity/Entity;getDefaultGravity()D"
			)
	)
	private double lowBalloonGravity(double gravity) {
		if (this.floatyBalloons == 0 || gravity <= FloatyBalloon.GRAVITY)
			return gravity;

		// each balloon subtracts from gravity
		double subtracted = this.floatyBalloons * FloatyBalloon.GRAVITY_PER_BALLOON;

		// gravity caps at matching floaty balloons
		return Math.max(gravity - subtracted, FloatyBalloon.GRAVITY);
	}

	// loaded in EntityTypeMixin
	@ModifyReturnValue(method = "saveWithoutId", at = @At("RETURN"))
	private CompoundTag saveBalloons(CompoundTag nbt) {
		if (this.balloons.isEmpty())
			return nbt;

		ListTag balloonNbtList = new ListTag();
		for (AbstractBalloon balloon : this.balloons) {
			if (!balloon.hasAttachment())
				continue; // shouldn't happen, just in case

			CompoundTag tag = new CompoundTag();
			tag.put("attachment", balloon.attachment().toNbt());
			tag.put("entity", balloon.saveWithoutAttachment());
			balloonNbtList.add(tag);
		}
		nbt.put(EntityBalloonAttachment.BALLOONS_KEY, balloonNbtList);
		return nbt;
	}

	@Override
	public void up_and_away$addBalloon(AbstractBalloon balloon) {
		this.balloons.add(balloon);
		if (balloon instanceof FloatyBalloon) {
			this.floatyBalloons++;
		}
	}

	@Override
	public void up_and_away$removeBalloon(AbstractBalloon balloon) {
		if (this.balloons.remove(balloon) && balloon instanceof FloatyBalloon) {
			this.floatyBalloons--;
		}
	}

	@Override
	public List<AbstractBalloon> up_and_away$getBalloons() {
		return this.balloons;
	}
}
