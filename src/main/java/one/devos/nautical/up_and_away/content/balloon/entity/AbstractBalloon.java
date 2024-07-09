package one.devos.nautical.up_and_away.content.balloon.entity;

import java.util.Locale;
import java.util.Objects;
import java.util.function.IntFunction;

import com.mojang.serialization.Codec;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.FastColor;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.component.DyedItemColor;
import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.content.UpAndAwayComponents;
import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;

import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BlockBalloonAttachment;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.EntityBalloonAttachment;
import one.devos.nautical.up_and_away.content.balloon.entity.packet.BalloonDetachPacket;
import one.devos.nautical.up_and_away.content.balloon.entity.packet.BlockBalloonAttachmentPacket;
import one.devos.nautical.up_and_away.content.balloon.entity.packet.EntityBalloonAttachmentPacket;

import one.devos.nautical.up_and_away.content.balloon.item.BalloonItem;
import one.devos.nautical.up_and_away.content.balloon.item.DeflatedBalloonItem;
import one.devos.nautical.up_and_away.framework.entity.ExtraSpawnPacketsEntity;

import one.devos.nautical.up_and_away.framework.entity.SometimesSerializableEntity;

import one.devos.nautical.up_and_away.framework.util.Utils;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractBalloon extends Entity implements ExtraSpawnPacketsEntity, SometimesSerializableEntity {
	public static final EntityDataAccessor<Byte> SHAPE_ID = SynchedEntityData.defineId(AbstractBalloon.class, EntityDataSerializers.BYTE);
	public static final EntityDataAccessor<Byte> MODE_ID = SynchedEntityData.defineId(AbstractBalloon.class, EntityDataSerializers.BYTE);
	public static final EntityDataAccessor<Integer> COLOR = SynchedEntityData.defineId(AbstractBalloon.class, EntityDataSerializers.INT);

	public static final BalloonShape DEFAULT_SHAPE = BalloonShape.ROUND;
	public static final int DEFAULT_COLOR = 0xFFFFFFFF;
	public static final int[] RANDOM_COLORS = { 0xFFe68a25, 0xFFe14d2f, 0xFF4cc2e0, 0xFF52e5ae, 0xFFebbd33, 0xFFf03199 };

	public static final String SHAPE_KEY = "shape";
	public static final String COLOR_KEY = "color";
	public static final String ATTACHMENT_KEY = "attachment";
	public static final String MODE_KEY = "mode";

	public static final TagKey<Block> SHARP_BLOCKS = TagKey.create(Registries.BLOCK, UpAndAway.id("pops_balloons"));
	public static final TagKey<Item> SHARP_ITEMS = TagKey.create(Registries.ITEM, UpAndAway.id("pops_balloons"));
	public static final TagKey<EntityType<?>> SHARP_ENTITIES = TagKey.create(Registries.ENTITY_TYPE, UpAndAway.id("pops_balloons"));

	private BalloonAttachment attachment;

	private int lerpSteps;
	private double lerpX;
	private double lerpY;
	private double lerpZ;

	protected AbstractBalloon(EntityType<?> entityType, Level level) {
		super(entityType, level);
		this.blocksBuilding = true;
	}

	protected AbstractBalloon(EntityType<?> type, Level level, ItemStack stack, @Nullable BalloonAttachment attachment) {
		this(type, level);
		this.setAttachment(attachment);

		if (stack.getItem() instanceof BalloonItem item) {
			this.entityData.set(SHAPE_ID, item.shape.id);
		}

		if (stack.has(UpAndAwayComponents.BALLOON_MODE)) {
			Mode mode = Objects.requireNonNull(stack.get(UpAndAwayComponents.BALLOON_MODE));
			this.entityData.set(MODE_ID, mode.id);
		}

		this.entityData.set(COLOR, getColor(stack, this.random));
	}

	protected abstract Item baseItem();

	@Override
	protected void defineSynchedData(Builder builder) {
		builder.define(SHAPE_ID, DEFAULT_SHAPE.id);
		builder.define(MODE_ID, Mode.NORMAL.id);
		builder.define(COLOR, DEFAULT_COLOR);
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
		super.onSyncedDataUpdated(data);
		if (SHAPE_ID.equals(data)) {
			this.refreshDimensions();
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag nbt) {
		Utils.simpleDecodeSafe(BalloonShape.CODEC, nbt, SHAPE_KEY).ifPresent(shape -> this.entityData.set(SHAPE_ID, shape.id));
		Utils.simpleDecodeSafe(Mode.CODEC, nbt, MODE_KEY).ifPresent(mode -> this.entityData.set(MODE_ID, mode.id));

		this.entityData.set(COLOR, FastColor.ARGB32.opaque(nbt.getInt(COLOR_KEY)));

		if (nbt.contains(ATTACHMENT_KEY, CompoundTag.TAG_COMPOUND)) {
			CompoundTag tag = nbt.getCompound(ATTACHMENT_KEY);
			this.attachment = BlockBalloonAttachment.fromNbt(tag, this.level());
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
		nbt.putString(SHAPE_KEY, this.shape().name);

		if (this.mode() != Mode.NORMAL) {
			nbt.putString(MODE_KEY, this.mode().name);
		}

		nbt.putInt(COLOR_KEY, this.color());

		if (this.attachment instanceof BlockBalloonAttachment) {
			nbt.put(ATTACHMENT_KEY, this.attachment.toNbt());
		}
	}

	@Override
	public boolean isSerializable() {
		return this.attachment == null || this.attachment instanceof BlockBalloonAttachment;
	}

	public CompoundTag saveWithoutAttachment() {
		BalloonAttachment attachment = this.attachment;
		this.attachment = null;
		CompoundTag nbt = new CompoundTag();
		try {
			this.save(nbt);
		} finally {
			this.attachment = attachment;
		}
		return nbt;
	}

	@Override
	public void addSpawnPackets(PacketConsumer consumer) {
		if (this.attachment instanceof BlockBalloonAttachment block) {
			consumer.add(new BlockBalloonAttachmentPacket(this, block));
		} else if (this.attachment instanceof EntityBalloonAttachment entity) {
			consumer.add(new EntityBalloonAttachmentPacket(this, entity));
		}
	}

	@Override
	public void lerpTo(double x, double y, double z, float yaw, float pitch, int interpolationSteps) {
		this.lerpX = x;
		this.lerpY = y;
		this.lerpZ = z;
		this.lerpSteps = interpolationSteps;
	}

	@Override
	public double lerpTargetX() {
		return this.lerpSteps > 0 ? this.lerpX : this.getX();
	}

	@Override
	public double lerpTargetY() {
		return this.lerpSteps > 0 ? this.lerpY : this.getY();
	}

	@Override
	public double lerpTargetZ() {
		return this.lerpSteps > 0 ? this.lerpZ : this.getZ();
	}

	@Override
	public void tick() {
		if (this.isFixed())
			return;

		super.tick();

		if (!this.level().isClientSide) {
			this.lerpSteps = 0;
			this.applyGravity();
			this.applyAirDrag();
			this.handleAttachment();
			this.move(MoverType.SELF, this.getDeltaMovement());
			this.checkSharpBlocks();
		} else {
			this.setDeltaMovement(Vec3.ZERO);
		}

		if (this.lerpSteps > 0) {
			this.lerpPositionAndRotationStep(this.lerpSteps, this.lerpX, this.lerpY, this.lerpZ, this.getYRot(), this.getXRot());
			--this.lerpSteps;
		}
	}

	private void applyAirDrag() {
		this.setDeltaMovement(this.getDeltaMovement().multiply(0.91, 1, 0.91));
	}

	private void handleAttachment() {
		if (this.attachment == null)
			return;

		if (!this.level().isClientSide && !this.attachment.validate()) {
			this.detach();
			return;
		}

		if (this.attachment.shouldTeleport(this.position())) {
			Vec3 pos = this.attachment.getPos();
			this.teleportTo(pos.x, pos.y, pos.z);
		}
	}

	private void checkSharpBlocks() {
		if (!this.isInvulnerable() && this.anyCollidingBlocksAreSharp()) {
			this.pop();
		}
	}

	private boolean anyCollidingBlocksAreSharp() {
		return BlockPos.betweenClosedStream(this.getBoundingBox().inflate(1e-4))
				.anyMatch(pos -> this.level().getBlockState(pos).is(SHARP_BLOCKS));
	}

	@Override
	public boolean isInvulnerableTo(DamageSource source) {
		if (this.isFixed() || super.isInvulnerableTo(source))
			return true;

		if (source.getDirectEntity() instanceof Player player) {
			// take no damage from adventure mode players
			if (!player.mayBuild()) {
				// unless explicitly made vulnerable
				return this.mode() != Mode.VULNERABLE;
			}

			// invulnerable unless held item is tagged as sharp
			ItemStack held = player.getMainHandItem();
			return !held.is(SHARP_ITEMS);
		}

		// vulnerable to all other damage
		return false;
	}

	private boolean isInvulnerableTo(DamageSource source, float amount) {
		if (amount <= 0) {
			// 0 damage - invulnerable unless specially tagged
			Entity entity = source.getDirectEntity();
			return entity == null || !entity.getType().is(SHARP_ENTITIES);
		}
		return this.isInvulnerableTo(source);
	}

	@Override
	public boolean hurt(DamageSource source, float amount) {
		if (!this.isInvulnerableTo(source, amount)) {
			this.pop();
			return true;
		}

		if (this.isFixed() || source.is(DamageTypeTags.NO_KNOCKBACK) || this.level().isClientSide)
			return true;

		this.markHurt();

		// from LivingEntity.hurt
		double dx = 0.0;
		double dz = 0.0;
		if (source.getDirectEntity() instanceof Projectile projectile) {
			// based on default impl of calculateHorizontalHurtKnockbackDirection
			// can't call it because it requires a LivingEntity
			dx = -projectile.getDeltaMovement().x;
			dz = -projectile.getDeltaMovement().z;
		} else if (source.getSourcePosition() != null) {
			dx = source.getSourcePosition().x() - this.getX();
			dz = source.getSourcePosition().z() - this.getZ();
		}
		this.knockback(0.4, dx, dz);
		return true;
	}

	// also from LivingEntity
	public void knockback(double strength, double x, double z) {
		if (strength <= 0)
			return;

		this.hasImpulse = true;

		// thank you vineflower very cool
		Vec3 vec3;
		for(vec3 = this.getDeltaMovement(); x * x + z * z < 1.0E-5F; z = (Math.random() - Math.random()) * 0.01) {
			x = (Math.random() - Math.random()) * 0.01;
		}

		Vec3 vec32 = new Vec3(x, 0.0, z).normalize().scale(strength);
		this.setDeltaMovement(vec3.x / 2.0 - vec32.x, this.onGround() ? Math.min(0.4, vec3.y / 2.0 + strength) : vec3.y, vec3.z / 2.0 - vec32.z);
	}

	public void pop() {
		Vec3 center = this.position().add(0, this.getBbHeight() / 2, 0);
		DeflatedBalloonItem.pop(this.level(), center, this.getSoundSource());
		if (!this.level().isClientSide)
			this.discard();
	}

	@Override
	public AABB getBoundingBoxForCulling() {
		AABB bounds = super.getBoundingBoxForCulling();
		if (this.attachment != null) {
			Vec3 relativeOffset = this.position().vectorTo(this.attachment.getPos());
			bounds = bounds.expandTowards(relativeOffset);
		}
		return bounds;
	}

	@Override
	public void remove(RemovalReason reason) {
		super.remove(reason);
		if (this.attachment != null) {
			this.attachment.onRemove(this);
		}
	}

	@Override
	public void onClientRemoval() {
		if (this.attachment != null) {
			this.attachment.onRemove(this);
		}
	}

	@Override
	protected Vec3 getLeashOffset() {
		return Vec3.ZERO;
	}

	@Override
	public boolean isPushable() {
		return !this.isFixed();
	}

	@Override
	public boolean canCollideWith(Entity other) {
		return other instanceof AbstractBalloon;
	}

	@Override
	public boolean isPickable() {
		return true;
	}

	@Nullable
	@Override
	public ItemStack getPickResult() {
		int color = this.color();
		return Util.make(new ItemStack(this.baseItem()), color != DEFAULT_COLOR ? stack -> stack.applyComponents(
				DataComponentPatch.builder()
						.set(DataComponents.DYED_COLOR, new DyedItemColor(color, true)).
						build()
		) : stack -> {});
	}

	@Override
	protected MovementEmission getMovementEmission() {
		return MovementEmission.EVENTS;
	}

	@Override
	public EntityDimensions getDimensions(Pose pose) {
		return this.shape().dimensions;
	}

	public BalloonShape shape() {
		return BalloonShape.BY_ID.apply(this.entityData.get(SHAPE_ID));
	}

	public Mode mode() {
		return Mode.BY_ID.apply(this.entityData.get(MODE_ID));
	}

	public int color() {
		return this.entityData.get(COLOR);
	}

	public boolean hasAttachment() {
		return this.attachment != null;
	}

	@UnknownNullability("hasAttachment")
	public BalloonAttachment attachment() {
		return this.attachment;
	}

	public void detach() {
		this.attachment = null;

		if (!this.level().isClientSide) {
			BalloonDetachPacket packet = new BalloonDetachPacket(this);
			PlayerLookup.tracking(this).forEach(player -> ServerPlayNetworking.send(player, packet));
		}
	}

	public void setAttachment(@Nullable BalloonAttachment attachment) {
		this.attachment = attachment;
		if (attachment != null) {
			attachment.onSet(this);
		}
	}

	public boolean isFixed() {
		return this.mode() == Mode.FIXED;
	}

	private static int getColor(ItemStack stack, RandomSource random) {
		if (stack.getOrDefault(UpAndAwayComponents.RANDOM_COLOR, false)) {
			return Util.getRandom(RANDOM_COLORS, random);
		} else {
			return DyedItemColor.getOrDefault(stack, DEFAULT_COLOR);
		}
	}

	public enum Mode implements StringRepresentable {
		NORMAL,
		FIXED,
		VULNERABLE;

		public final String name = this.name().toLowerCase(Locale.ROOT);
		public final byte id = (byte) this.ordinal();

		public static final IntFunction<Mode> BY_ID = ByIdMap.continuous(Enum::ordinal, values(), ByIdMap.OutOfBoundsStrategy.ZERO);
		public static final Codec<Mode> CODEC = StringRepresentable.fromEnum(Mode::values);

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}
