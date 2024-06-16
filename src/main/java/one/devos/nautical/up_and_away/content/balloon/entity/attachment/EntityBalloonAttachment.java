package one.devos.nautical.up_and_away.content.balloon.entity.attachment;

import java.util.UUID;

import one.devos.nautical.up_and_away.mixin.LevelAccessor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
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

	public static BalloonAttachment fromNbt(CompoundTag nbt, Level level) {
		UUID id = nbt.getUUID("id");
		Entity entity = ((LevelAccessor) level).callGetEntities().get(id);
		return null;
	}
}
