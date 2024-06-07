package one.devos.nautical.up_and_away.content;

import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.entity.Balloon;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class UpAndAwayEntities {
	public static final EntityType<Balloon> BALLOON = register(
			"balloon", EntityType.Builder.of(Balloon::new, MobCategory.MISC)
					.sized(1, 1)
					.build()
	);

	private static <T extends Entity> EntityType<T> register(String name, EntityType<T> type) {
		return Registry.register(BuiltInRegistries.ENTITY_TYPE, UpAndAway.id(name), type);
	}
}
