package one.devos.nautical.up_and_away.content;

import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.content.balloon.entity.AirBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.BalloonCart;
import one.devos.nautical.up_and_away.content.balloon.entity.FloatyBalloon;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class UpAndAwayEntities {
	public static final EntityType<AirBalloon> AIR_BALLOON = register(
			"air_balloon", EntityType.Builder.of(AirBalloon::createClient, MobCategory.MISC)
					.sized(1, 1)
					.build()
	);

	public static final EntityType<FloatyBalloon> FLOATY_BALLOON = register(
			"floaty_balloon", EntityType.Builder.of(FloatyBalloon::createClient, MobCategory.MISC)
					.sized(1, 1)
					.build()
	);

	public static final EntityType<BalloonCart> BALLOON_CART = register(
			"balloon_cart", EntityType.Builder.of(BalloonCart::new, MobCategory.MISC)
					.build()
	);

	private static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, UpAndAway.id(name), type);
	}

	public static void init() {
	}
}
