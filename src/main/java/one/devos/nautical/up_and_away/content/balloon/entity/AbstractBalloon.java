package one.devos.nautical.up_and_away.content.balloon.entity;

import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public abstract class AbstractBalloon extends Entity {
	public static final EntityDataAccessor<Integer> SHAPE = SynchedEntityData.defineId(AbstractBalloon.class, EntityDataSerializers.INT);

	public static final String SHAPE_KEY = "shape";

	protected BalloonShape shape;

	protected AbstractBalloon(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected void defineSynchedData(Builder builder) {
		builder.define(SHAPE, BalloonShape.ROUND.ordinal());
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		super.onSyncedDataUpdated(accessor);
		if (accessor == SHAPE) {
			int ordinal = this.entityData.get(SHAPE);
			this.shape = BalloonShape.ofOrdinal(ordinal);
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {
		this.entityData.set(SHAPE, BalloonShape.ofName(nbt.getString(SHAPE_KEY)).ordinal());
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
		nbt.putString(SHAPE_KEY, this.shape.name);
	}

	public BalloonShape shape() {
		return this.shape;
	}
}
