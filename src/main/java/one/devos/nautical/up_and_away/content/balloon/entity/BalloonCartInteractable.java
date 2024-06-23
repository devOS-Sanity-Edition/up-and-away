package one.devos.nautical.up_and_away.content.balloon.entity;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.BalloonCartAnimations;

import java.util.Optional;
import java.util.function.Supplier;

public enum BalloonCartInteractable {
	HATCH(
			AABB.ofSize(new Vec3(0, BalloonCart.HEIGHT, 0), 2 / 16d, 2 / 16d, 8 / 16d),
			new Vec3(255, 0, 0),
			() -> () -> BalloonCartAnimations.HATCH_OPEN,
			() -> () -> BalloonCartAnimations.HATCH_CLOSE
	) {
		@Override
		public boolean interact(BalloonCart cart, Player player) {
			cart.setInteractableState(this, !cart.getInteractableState(this));
			return true;
		}
	},
	CHEST(
			AABB.ofSize(new Vec3(3/16d, BalloonCart.HEIGHT, 0), 2 / 16d, 2 / 16d, 8 / 16d),
			new Vec3(0, 255, 0),
			() -> () -> BalloonCartAnimations.CHEST_OPEN,
			() -> () -> BalloonCartAnimations.CHEST_CLOSE
	) {
		@Override
		public boolean interact(BalloonCart cart, Player player) {
			cart.setInteractableState(this, !cart.getInteractableState(this));
			return true;
		}
	},
	TABLE(
			AABB.ofSize(new Vec3(5/16d, BalloonCart.HEIGHT, 0), 2 / 16d, 2 / 16d, 8 / 16d),
			new Vec3(0, 0, 255),
			() -> () -> BalloonCartAnimations.TABLE_OPEN,
			() -> () -> BalloonCartAnimations.TABLE_CLOSE
	) {
		@Override
		public boolean interact(BalloonCart cart, Player player) {
			cart.setInteractableState(this, !cart.getInteractableState(this));
			return true;
		}
	};

	public static final BalloonCartInteractable[] VALUES = values();

	public final AABB hitBox;
	public final Vec3 debugColor;
	private final Supplier<Supplier<AnimationDefinition>> openAnimationDefinition;
	private final Supplier<Supplier<AnimationDefinition>> closeAnimationDefinition;

	BalloonCartInteractable(
			AABB hitBox,
			Vec3 debugColor,
			Supplier<Supplier<AnimationDefinition>> openAnimationDefinition,
			Supplier<Supplier<AnimationDefinition>> closeAnimationDefinition
	) {
		this.hitBox = hitBox;
		this.debugColor = debugColor;
		this.openAnimationDefinition = openAnimationDefinition;
		this.closeAnimationDefinition = closeAnimationDefinition;
	}

	public abstract boolean interact(BalloonCart cart, Player player);

	public static Optional<BalloonCartInteractable> raycast(Vec3 start, Vec3 end) {
		double lastDist = Double.MAX_VALUE;
		Optional<BalloonCartInteractable> closest = Optional.empty();
		for (BalloonCartInteractable interactable : VALUES) {
			Optional<Vec3> clipPoint = interactable.hitBox.clip(start, end);
			if (clipPoint.isPresent()) {
				double dist = start.distanceToSqr(clipPoint.get());
				if (dist < lastDist) {
					closest = Optional.of(interactable);
					lastDist = dist;
				}
			}
		}
		return closest;
	}

	@Environment(EnvType.CLIENT)
	public AnimationDefinition openAnimationDefinition() {
		return this.openAnimationDefinition.get().get();
	}

	@Environment(EnvType.CLIENT)
	public AnimationDefinition closeAnimationDefinition() {
		return this.closeAnimationDefinition.get().get();
	}
}
