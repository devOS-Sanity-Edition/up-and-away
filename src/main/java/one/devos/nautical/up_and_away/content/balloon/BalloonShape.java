package one.devos.nautical.up_and_away.content.balloon;

import java.util.Locale;

import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;

import net.minecraft.Util;

public enum BalloonShape {
	CUBE,
	DOG,
	HEART,
	LONG,
	ROUND;

	public static final BiMap<String, BalloonShape> BY_NAME = Util.make(() -> {
		EnumHashBiMap<BalloonShape, String> map = EnumHashBiMap.create(BalloonShape.class);
		for (BalloonShape shape : BalloonShape.values()) {
			map.put(shape, shape.name);
		}
		return map.inverse();
	});

	public static final BalloonShape DEFAULT = ROUND;

	public final String name = this.name().toLowerCase(Locale.ROOT);

	@Override
	public String toString() {
		return this.name;
	}

	public static BalloonShape ofOrdinal(int ordinal) {
		if (ordinal < 0)
			return ROUND;

		BalloonShape[] all = values();
		if (ordinal > all.length)
			return ROUND;

		return all[ordinal];
	}

	public static BalloonShape ofName(String name) {
		return BY_NAME.getOrDefault(name, ROUND);
	}
}
