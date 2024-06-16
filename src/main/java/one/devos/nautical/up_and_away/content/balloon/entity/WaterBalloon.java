package one.devos.nautical.up_and_away.content.balloon.entity;

import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WaterBalloon extends AbstractBalloon {
	protected WaterBalloon(EntityType<?> type, Level level) {
		super(type, level);
	}

	public WaterBalloon(EntityType<?> type, Level level, ItemStack stack, @Nullable BalloonAttachment attachment) {
		super(type, level, stack, attachment);
	}

	public static WaterBalloon createClient(EntityType<?> type, Level level) {
		return new WaterBalloon(type, level);
	}

	@Override
	protected double getDefaultGravity() {
		return 0.08; // attribute default
	}
}
