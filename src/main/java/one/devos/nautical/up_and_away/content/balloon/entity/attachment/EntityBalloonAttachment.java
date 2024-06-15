package one.devos.nautical.up_and_away.content.balloon.entity.attachment;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public record EntityBalloonAttachment(Entity entity, double stringLength) implements BalloonAttachment {
	@Override
	public boolean validate() {
		return !this.entity.isRemoved();
	}

	@Override
	public Vec3 getPos() {
		return this.entity.getBoundingBox().getCenter();
	}

	@Override
	public double getStringLength() {
		return this.stringLength;
	}
}
