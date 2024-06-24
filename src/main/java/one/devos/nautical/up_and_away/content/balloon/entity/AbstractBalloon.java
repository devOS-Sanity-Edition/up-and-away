package one.devos.nautical.up_and_away.content.balloon.entity;

import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.content.UpAndAwayItems;
import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;

import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BlockBalloonAttachment;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.EntityBalloonAttachment;
import one.devos.nautical.up_and_away.content.balloon.entity.packet.BalloonDetachPacket;
import one.devos.nautical.up_and_away.content.balloon.entity.packet.BlockBalloonAttachmentPacket;
import one.devos.nautical.up_and_away.content.balloon.entity.packet.EntityBalloonAttachmentPacket;
import one.devos.nautical.up_and_away.content.balloon.item.BalloonItem;

import one.devos.nautical.up_and_away.framework.entity.ExtraSpawnPacketsEntity;

import one.devos.nautical.up_and_away.framework.entity.SometimesSerializableEntity;

import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
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
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public abstract class AbstractBalloon extends Entity implements ExtraSpawnPacketsEntity, SometimesSerializableEntity {
	public static final EntityDataAccessor<ItemStack> ITEM = SynchedEntityData.defineId(AbstractBalloon.class, EntityDataSerializers.ITEM_STACK);
	public static final String ITEM_KEY = "item";
	public static final String ATTACHMENT_KEY = "attachment";

	public static final TagKey<Block> SHARP_BLOCKS = TagKey.create(Registries.BLOCK, UpAndAway.id("pops_balloons"));
	public static final TagKey<Item> SHARP_ITEMS = TagKey.create(Registries.ITEM, UpAndAway.id("pops_balloons"));
	public static final TagKey<EntityType<?>> SHARP_ENTITIES = TagKey.create(Registries.ENTITY_TYPE, UpAndAway.id("pops_balloons"));

	private static final ItemStack itemFallback = new ItemStack(UpAndAwayItems.FLOATY.get(BalloonShape.ROUND));

	private BalloonAttachment attachment;

	protected AbstractBalloon(EntityType<?> entityType, Level level) {
		super(entityType, level);
		this.blocksBuilding = true;
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
			CompoundTag tag = nbt.getCompound(ATTACHMENT_KEY);
			this.attachment = BlockBalloonAttachment.fromNbt(tag, this.level());
		}
	}

	@Override
	protected void addAdditionalSaveData(CompoundTag nbt) {
		nbt.put(ITEM_KEY, this.entityData.get(ITEM).save(this.registryAccess()));
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
	public void tick() {
		super.tick();
		this.applyGravity();
		this.applyAirDrag();
		this.handleAttachment();
		this.move(MoverType.SELF, this.getDeltaMovement());
	}

	private void applyAirDrag() {
		this.setDeltaMovement(this.getDeltaMovement().scale(0.91));
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
		} else if (this.attachment.isTooFar(this.position())) {
			Vec3 to = this.position().vectorTo(this.attachment.getPos());
			double dist = to.length();
			double extra = dist - this.attachment.stringLength;
			this.setDeltaMovement(to.normalize().scale(extra + 0.1));
		}
	}

	@Override
	public boolean isInvulnerableTo(DamageSource source) {
		if (super.isInvulnerableTo(source))
			return true;

		if (source.getDirectEntity() instanceof Player player) {
			// take no damage from adventure mode players
			if (!player.mayBuild())
				return true;

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

		if (source.is(DamageTypeTags.NO_KNOCKBACK))
			return true;

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

	@Override
	protected void onInsideBlock(BlockState state) {
		if (state.is(SHARP_BLOCKS))
			this.pop();
	}

	public void pop() {
		this.playSound(SoundEvents.GENERIC_EXPLODE.value(), 1, 5);
		if (this.level() instanceof ServerLevel serverLevel) {
			BlockParticleOption particles = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.SLIME_BLOCK.defaultBlockState());
			Vec3 pos = this.position().add(0, this.getBbHeight() / 2, 0);
			serverLevel.sendParticles(particles, pos.x, pos.y, pos.z, 10, 0, 0, 0, 1);
			this.discard();
		}
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

	public BalloonShape shape() {
		// TODO: this is bad
		return ((BalloonItem) this.item().getItem()).shape;
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
}
