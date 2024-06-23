package one.devos.nautical.up_and_away.content.balloon.entity;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import one.devos.nautical.up_and_away.content.UpAndAwayItems;

import org.jetbrains.annotations.Nullable;

import java.util.EnumMap;
import java.util.Map;

public class BalloonCart extends Entity {
	public static final double WIDTH = 28 / 16d;
	public static final double HEIGHT = 24 / 16d;
	public static final double LENGTH = 56 / 16d;
	public static final double Z_OFFSET = 22 / 16d;

	private int lerpSteps;
	private double lerpX;
	private double lerpY;
	private double lerpZ;
	private float lerpYRot;

	private static final EnumMap<BalloonCartInteractable, EntityDataAccessor<Boolean>> INTERACTABLE_STATES = Util.make(
		new EnumMap<>(BalloonCartInteractable.class),
		map -> {
			for (BalloonCartInteractable interactable : BalloonCartInteractable.VALUES) {
				map.put(interactable, SynchedEntityData.defineId(BalloonCart.class, EntityDataSerializers.BOOLEAN));
			}
		}
	);

	public final EnumMap<BalloonCartInteractable, InteractableAnimationState> interactableAnimationStates = Util.make(
		new EnumMap<>(BalloonCartInteractable.class),
		map -> {
			for (BalloonCartInteractable interactable : BalloonCartInteractable.VALUES) {
				map.put(interactable, new InteractableAnimationState(interactable));
			}
		}
	);

	public BalloonCart(EntityType<?> entityType, Level level) {
		super(entityType, level);
		this.blocksBuilding = true;
	}

	public void setInteractableState(BalloonCartInteractable interactable, boolean open) {
		this.getEntityData().set(INTERACTABLE_STATES.get(interactable), open);
	}

	public boolean getInteractableState(BalloonCartInteractable interactable) {
		return this.getEntityData().get(INTERACTABLE_STATES.get(interactable));
	}

	@Override
	protected void defineSynchedData(SynchedEntityData.Builder builder) {
		for (EntityDataAccessor<Boolean> data : INTERACTABLE_STATES.values()) {
			builder.define(data, false);
		}
	}

	@Override
	protected void readAdditionalSaveData(CompoundTag compoundTag) {

	}

	@Override
	protected void addAdditionalSaveData(CompoundTag compoundTag) {

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
	public float lerpTargetYRot() {
		return this.lerpSteps > 0 ? this.lerpYRot : this.getYRot();
	}

	@Override
	public void lerpTo(double x, double y, double z, float yaw, float pitch, int interpolationSteps) {
		this.lerpX = x;
		this.lerpY = y;
		this.lerpZ = z;
		this.lerpYRot = yaw;
		this.lerpSteps = interpolationSteps;
	}

	@Override
	public InteractionResult interact(Player player, InteractionHand hand) {
		InteractionResult result = super.interact(player, hand);
		if (!result.consumesAction()) {
			Vec3 start = player
					.getEyePosition()
					.subtract(this.position());
			Vec3 end = start
					.add(player
							.calculateViewVector(player.getXRot(), player.getYRot())
							.scale(player.entityInteractionRange())
					);
			if (BalloonCartInteractable
					.raycast(start, end)
					.map(interactable -> interactable.interact(this, player))
					.orElse(false)
			)
				return InteractionResult.sidedSuccess(this.level().isClientSide);
			return InteractionResult.FAIL;
		}
		return result;
	}

	@Override
	public void onSyncedDataUpdated(EntityDataAccessor<?> data) {
		super.onSyncedDataUpdated(data);
		for (Map.Entry<BalloonCartInteractable, EntityDataAccessor<Boolean>> entry : INTERACTABLE_STATES.entrySet()) {
			if (entry.getValue().equals(data)) {
				InteractableAnimationState animationState = this.interactableAnimationStates.get(entry.getKey());
				if ((boolean) this.getEntityData().get(data)) {
					animationState.open.start(this.tickCount);
					animationState.close.stop();
				} else {
					animationState.close.start(this.tickCount);
					animationState.open.stop();
				}
				return;
			}
		}
	}

	@Override
	public void tick() {
		super.tick();

		if (this.isControlledByLocalInstance()) {
			this.lerpSteps = 0;
			this.syncPacketPositionCodec(this.getX(), this.getY(), this.getZ());

			this.applyGravity();
			this.move(MoverType.SELF, this.getDeltaMovement());
		} else {
			this.setDeltaMovement(Vec3.ZERO);
		}

		if (this.lerpSteps > 0) {
			this.lerpPositionAndRotationStep(this.lerpSteps, this.lerpX, this.lerpY, this.lerpZ, this.lerpYRot, this.getXRot());
			--this.lerpSteps;
		}
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

	@Nullable
	@Override
	public ItemStack getPickResult() {
		return UpAndAwayItems.BALLOON_CART.getDefaultInstance();
	}

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

	public record InteractableAnimationState(AnimationState open, AnimationState close) {
		public InteractableAnimationState(BalloonCartInteractable interactable) {
			this(
					new AnimationState(),
					interactable == BalloonCartInteractable.TABLE ? Util.make(new AnimationState(), state -> {
						state.start(0);
						state.fastForward(10, 1f);
					}) : new AnimationState()
			);
		}
	}
}
