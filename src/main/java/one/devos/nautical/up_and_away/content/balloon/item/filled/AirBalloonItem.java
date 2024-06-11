package one.devos.nautical.up_and_away.content.balloon.item.filled;

import one.devos.nautical.up_and_away.content.UpAndAwayEntities;
import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.AirBalloon;
import one.devos.nautical.up_and_away.content.balloon.item.FilledBalloonItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AirBalloonItem extends FilledBalloonItem {
	public AirBalloonItem(BalloonShape shape, Properties properties) {
		super(shape, properties);
	}

	@Override
	public AbstractBalloon createEntity(Level level, ItemStack stack) {
		return new AirBalloon(UpAndAwayEntities.AIR_BALLOON, level, stack);
	}
}
