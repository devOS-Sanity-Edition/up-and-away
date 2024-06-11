package one.devos.nautical.up_and_away.content.balloon.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class FloatyBalloon extends AirBalloon {
	protected FloatyBalloon(EntityType<?> type, Level level) {
		super(type, level);
	}

	public FloatyBalloon(EntityType<?> type, Level level, ItemStack stack) {
		super(type, level, stack);
	}

	public static FloatyBalloon createClient(EntityType<?> type, Level level) {
		return new FloatyBalloon(type, level);
	}

	@Override
	protected double getDefaultGravity() {
		return -0.04;
	}
}
