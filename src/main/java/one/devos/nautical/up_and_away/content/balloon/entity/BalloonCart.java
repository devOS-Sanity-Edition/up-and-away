package one.devos.nautical.up_and_away.content.balloon.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;

public class BalloonCart extends Entity {
	public static final double WIDTH = 28 / 16d;
	public static final double HEIGHT = 24 / 16d;
	public static final double LENGTH = 58 / 16d;
	public static final double Z_OFFSET = 34 / 16d;

	public BalloonCart(EntityType<?> entityType, Level level) {
		super(entityType, level);
		this.blocksBuilding = true;
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {

	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compoundTag) {

	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compoundTag) {

	}

	@Override
	public void tick() {
		super.tick();
		this.applyGravity();
		this.move(MoverType.SELF, this.getDeltaMovement());
	}

	@Override
	protected AABB makeBoundingBox() {
		return new AABB(
				this.getX() - (WIDTH / 2),
				this.getY(),
				this.getZ() - Z_OFFSET,
				this.getX() + (WIDTH / 2),
				this.getY() + HEIGHT,
				(this.getZ() - Z_OFFSET) + LENGTH
		);
	}

	@Override
	protected double getDefaultGravity() {
		return this.isInWater() ? 0.005 : 0.04;
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
}
