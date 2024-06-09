package one.devos.nautical.up_and_away.content.balloon.entity;

import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;

public abstract class AbstractBalloon extends Entity {
	public static final EntityDataAccessor<Integer> SHAPE = SynchedEntityData.defineId(AbstractBalloon.class, EntityDataSerializers.INT);
	public static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(AbstractBalloon.class, EntityDataSerializers.INT);

	public static final String SHAPE_KEY = "shape";
	public static final String COLOR_KEY = "color";

	public static final int DEFAULT_COLOR = 0xFFFFFFFF;

	private BalloonShape shape;
	private int color;

	protected AbstractBalloon(EntityType<?> entityType, Level level) {
		super(entityType, level);
		this.blocksBuilding = true;
	}

	@Override
	protected void defineSynchedData(Builder builder) {
		builder.define(SHAPE, (this.shape = BalloonShape.DEFAULT).ordinal());
		builder.define(COLOR, this.color = DEFAULT_COLOR);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> accessor) {
		super.onSyncedDataUpdated(accessor);
		if (accessor == SHAPE) {
			int ordinal = this.entityData.get(SHAPE);
			this.shape = BalloonShape.ofOrdinal(ordinal);
		} else if (accessor == COLOR) {
			this.color = this.entityData.get(COLOR);
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {
		this.entityData.set(SHAPE, BalloonShape.ofName(nbt.getString(SHAPE_KEY)).ordinal());
		this.entityData.set(COLOR, nbt.contains(COLOR_KEY, CompoundTag.TAG_INT) ? nbt.getInt(COLOR_KEY) : DEFAULT_COLOR);
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
		nbt.putString(SHAPE_KEY, this.shape.name);
		nbt.putInt(COLOR_KEY, this.color);
	}

	@Override
	public void tick() {
		super.tick();
		this.applyGravity();
		this.applyAirDrag();
		this.move(MoverType.SELF, this.getDeltaMovement());
	}

	private void applyAirDrag() {
		this.setDeltaMovement(this.getDeltaMovement().scale(0.91));
	}

	@Override
	public boolean canCollideWith(Entity entity) {
		return entity instanceof AbstractBalloon;
	}

	@Override
	public boolean isPushable() {
		return true;
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	protected MovementEmission getMovementEmission() {
		return MovementEmission.EVENTS;
	}

	public BalloonShape shape() {
		return this.shape;
	}

	public int color() {
		return this.color;
	}
}
