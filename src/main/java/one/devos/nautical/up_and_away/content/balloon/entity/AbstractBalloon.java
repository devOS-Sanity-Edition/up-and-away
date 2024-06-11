package one.devos.nautical.up_and_away.content.balloon.entity;

import one.devos.nautical.up_and_away.content.UpAndAwayItems;
import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class AbstractBalloon extends Entity {
	public static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.defineId(AbstractBalloon.class, EntityDataSerializers.ITEM_STACK);

	private static final ItemStack itemFallback = new ItemStack(UpAndAwayItems.FLOATY_BALLOONS.get(BalloonShape.ROUND));

	public static final String ITEM_KEY = "item";

	protected AbstractBalloon(EntityType<?> entityType, Level level) {
		super(entityType, level);
		this.blocksBuilding = true;
	}

	protected AbstractBalloon(EntityType<?> type, Level level, ItemStack stack) {
		this(type, level);
		this.entityData.set(ITEM, stack.copy());
	}

	@Override
	protected void defineSynchedData(Builder builder) {
		builder.define(ITEM, ItemStack.EMPTY);
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {
		ItemStack stack = ItemStack.parse(this.registryAccess(), nbt.getCompound(ITEM_KEY))
				.orElseGet(itemFallback::copy);
		this.entityData.set(ITEM, stack);
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
		nbt.put(ITEM_KEY, this.entityData.get(ITEM).save(this.registryAccess()));
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

	public ItemStack item() {
		return this.entityData.get(ITEM);
	}
}
