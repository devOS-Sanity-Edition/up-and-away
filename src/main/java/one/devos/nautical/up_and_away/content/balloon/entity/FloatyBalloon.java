package one.devos.nautical.up_and_away.content.balloon.entity;

import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FloatyBalloon extends AirBalloon {
	public static final int BALLOONS_TO_NEGATE = 4;
	public static final double GRAVITY_PER_BALLOON = (LivingEntity.DEFAULT_BASE_GRAVITY / BALLOONS_TO_NEGATE) - 0.001; // a tiny bit less so you aren't stuck
	public static final double GRAVITY = -LivingEntity.DEFAULT_BASE_GRAVITY;
	public static final int MAX_HEIGHT_OFFSET = 64;

	protected FloatyBalloon(EntityType<?> type, Level level) {
		super(type, level);
	}

	public FloatyBalloon(EntityType<?> type, Level level, ItemStack stack, @Nullable BalloonAttachment attachment) {
		super(type, level, stack, attachment);
	}

	public static FloatyBalloon createClient(EntityType<?> type, Level level) {
		return new FloatyBalloon(type, level);
	}

	@Override
	public void tick() {
		super.tick();
		if (this.getY() >= (this.level().getHeight() + MAX_HEIGHT_OFFSET))
			this.pop();
	}

	@Override
	protected double getDefaultGravity() {
		return GRAVITY;
	}
}
