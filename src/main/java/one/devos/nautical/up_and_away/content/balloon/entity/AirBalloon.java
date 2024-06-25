package one.devos.nautical.up_and_away.content.balloon.entity;

import net.minecraft.world.item.Item;
import one.devos.nautical.up_and_away.content.UpAndAwayItems;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class AirBalloon extends AbstractBalloon {
	protected AirBalloon(EntityType<?> type, Level level) {
		super(type, level);
	}

	public AirBalloon(EntityType<?> type, Level level, ItemStack stack, @Nullable BalloonAttachment attachment) {
		super(type, level, stack, attachment);
	}

	public static AirBalloon createClient(EntityType<?> type, Level level) {
		return new AirBalloon(type, level);
	}

	@Override
	protected Item baseItem() {
		return UpAndAwayItems.AIR.get(this.shape());
	}

	@Override
	protected double getDefaultGravity() {
		return 0.01;
	}
}
