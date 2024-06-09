package one.devos.nautical.up_and_away.content.balloon.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class FloatyBalloon extends AirBalloon {
	public FloatyBalloon(EntityType<?> entityType, Level level) {
		super(entityType, level);
	}

	@Override
	protected double getDefaultGravity() {
		return -0.04;
	}
}
