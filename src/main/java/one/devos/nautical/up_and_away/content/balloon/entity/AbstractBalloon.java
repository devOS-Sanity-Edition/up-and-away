package one.devos.nautical.up_and_away.content.balloon.entity;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import one.devos.nautical.up_and_away.content.UpAndAwayItems;
import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;

import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachmentHolder;

import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Entity.MovementEmission;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractBalloon extends Entity {
	public static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.defineId(AbstractBalloon.class, EntityDataSerializers.ITEM_STACK);
	public static final String ITEM_KEY = "item";
	public static final String ATTACHMENT_KEY = "attachment";

	private static final ItemStack itemFallback = new ItemStack(UpAndAwayItems.FLOATY_BALLOONS.get(BalloonShape.ROUND));

	private BalloonAttachmentHolder attachmentHolder;

	protected AbstractBalloon(EntityType<?> entityType, Level level) {
		super(entityType, level);
		this.blocksBuilding = true;
		this.setAttachment(null);
	}

	protected AbstractBalloon(EntityType<?> type, Level level, ItemStack stack, @Nullable BalloonAttachment attachment) {
		this(type, level);
		this.entityData.set(ITEM, stack.copy());
		this.setAttachment(attachment);
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
		if (nbt.contains(ATTACHMENT_KEY, CompoundTag.TAG_COMPOUND)) {
			this.attachmentHolder = new BalloonAttachmentHolder(nbt.getCompound(ATTACHMENT_KEY));
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
		nbt.put(ITEM_KEY, this.entityData.get(ITEM).save(this.registryAccess()));
		BalloonAttachment attachment = this.attachment();
		if (attachment != null) {
			nbt.put(ATTACHMENT_KEY, attachment.toNbt());
		}
	}

	@Override
	public void tick() {
		super.tick();
		this.applyGravity();
		this.applyAirDrag();
		this.move(MoverType.SELF, this.getDeltaMovement());
		this.handleAttachment();
	}

	private void applyAirDrag() {
		this.setDeltaMovement(this.getDeltaMovement().scale(0.91));
	}

	private void handleAttachment() {
		BalloonAttachment attachment = this.attachment();
		if (attachment == null)
			return;

		if (!this.level().isClientSide && !attachment.validate()) {
			this.setAttachment(null);
			return;
		}

		if (attachment.shouldTeleport(this.position())) {
			Vec3 pos = attachment.getPos();
			this.teleportTo(pos.x, pos.y, pos.z);
		} else if (attachment.isTooFar(this.position())) {
			Vec3 to = this.position().vectorTo(attachment.getPos());
			Vec3 vel = to.scale(0.02);
			this.setDeltaMovement(vel);
		}
	}

	@Override
	public AABB getBoundingBoxForCulling() {
		AABB bounds = super.getBoundingBoxForCulling();
		BalloonAttachment attachment = this.attachment();
		if (attachment != null) {
			Vec3 relativeOffset = this.position().vectorTo(attachment.getPos());
			bounds = bounds.expandTowards(relativeOffset);
		}
		return bounds;
	}

	@Override
	protected Vec3 getLeashOffset() {
		return Vec3.ZERO;
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

	@Nullable
	public BalloonAttachment attachment() {
		return this.attachmentHolder.get(this.level());
	}

	public void setAttachment(@Nullable BalloonAttachment attachment) {
		this.attachmentHolder = new BalloonAttachmentHolder(attachment);

		if (!this.level().isClientSide) {
			BalloonAttachmentPacket packet = new BalloonAttachmentPacket(this, attachment);
			PlayerLookup.tracking(this).forEach(player -> ServerPlayNetworking.send(player, packet));
		}
	}
}
