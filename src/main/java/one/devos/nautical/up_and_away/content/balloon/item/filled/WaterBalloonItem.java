package one.devos.nautical.up_and_away.content.balloon.item.filled;

import one.devos.nautical.up_and_away.content.UpAndAwayEntities;
import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.WaterBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;
import one.devos.nautical.up_and_away.content.balloon.item.FilledBalloonItem;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WaterBalloonItem extends FilledBalloonItem {
	public WaterBalloonItem(BalloonShape shape, Properties properties) {
		super(shape, properties);
	}

	@Override
	public AbstractBalloon createEntity(Level level, ItemStack stack, @Nullable BalloonAttachment attachment) {
		return new WaterBalloon(UpAndAwayEntities.WATER_BALLOON, level, stack, attachment);
	}

	@Override
	public float throwingForce() {
		return 1;
	}
}
