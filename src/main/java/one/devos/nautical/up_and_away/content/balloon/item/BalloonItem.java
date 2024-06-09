package one.devos.nautical.up_and_away.content.balloon.item;

import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import net.minecraft.world.item.Item;

public class BalloonItem extends Item {
	public final BalloonShape shape;

	public BalloonItem(BalloonShape shape, Properties properties) {
		super(properties);
		this.shape = shape;
	}

	public interface Factory {
		BalloonItem create(BalloonShape shape, Properties properties);
	}
}
