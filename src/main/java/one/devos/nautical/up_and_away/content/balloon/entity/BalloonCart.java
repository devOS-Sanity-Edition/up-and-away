package one.devos.nautical.up_and_away.content.balloon.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class BalloonCart extends Entity {
	public static final double WIDTH = 28 / 16d;
	public static final double HEIGHT = 24 / 16d;
	public static final double LENGTH = 58 / 16d;
	public static final double Z_OFFSET = 34 / 16d;

	private int lerpSteps;
	private double lerpX;
	private double lerpY;
	private double lerpZ;
	private float lerpYRot;

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
	public InteractionResult interactAt(Player player, Vec3 hitPos, InteractionHand hand) {
		InteractionResult result = super.interactAt(player, hitPos, hand);
		if (!result.consumesAction()) {
			Vec3 start = hitPos.yRot(this.getYRot() * Mth.DEG_TO_RAD);
			Vec3 end = start
					.add(player
							.calculateViewVector(player.getXRot(), player.getYRot())
							.scale(player.entityInteractionRange())
					);
			for (BalloonCartInteraction interaction : BalloonCartInteraction.VALUES) {
				if (interaction.hitBox.intersects(start, end) && interaction.interact(this, player))
					return InteractionResult.sidedSuccess(this.level().isClientSide);
			}
			return InteractionResult.FAIL;
		}
		return result;
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

	@Override
	public boolean canBeCollidedWith() {
		return true;
	}
}
