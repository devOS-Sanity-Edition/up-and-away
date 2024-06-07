package one.devos.nautical.up_and_away.content;

import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.content.balloon.entity.AirBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.FloatyBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.WaterBalloon;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class UpAndAwayEntities {
	public static final EntityType<AirBalloon> AIR_BALLOON = register(
			"air_balloon", EntityType.Builder.of(AirBalloon::new, MobCategory.MISC)
					.sized(1, 1)
					.build()
	);

	public static final EntityType<FloatyBalloon> FLOATY_BALLOON = register(
			"floaty_balloon", EntityType.Builder.of(FloatyBalloon::new, MobCategory.MISC)
					.sized(1, 1)
					.build()
	);

	public static final EntityType<WaterBalloon> WATER_BALLOON = register(
			"water_balloon", EntityType.Builder.of(WaterBalloon::new, MobCategory.MISC)
					.sized(1, 1)
					.build()
	);

	private static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, UpAndAway.id(name), type);
	}
}
