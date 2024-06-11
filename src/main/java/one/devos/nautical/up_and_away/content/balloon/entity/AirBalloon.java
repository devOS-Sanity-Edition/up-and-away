package one.devos.nautical.up_and_away.content.balloon.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AirBalloon extends AbstractBalloon {
	protected AirBalloon(EntityType<?> type, Level level) {
		super(type, level);
	}

	public AirBalloon(EntityType<?> type, Level level, ItemStack stack) {
		super(type, level, stack);
	}

	public static AirBalloon createClient(EntityType<?> type, Level level) {
		return new AirBalloon(type, level);
	}

	@Override
	protected double getDefaultGravity() {
		return 0.01;
	}
}
