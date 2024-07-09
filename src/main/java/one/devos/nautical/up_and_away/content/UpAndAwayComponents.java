package one.devos.nautical.up_and_away.content;

import java.util.function.UnaryOperator;

import com.mojang.serialization.Codec;

import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;

public class UpAndAwayComponents {
	public static DataComponentType<AbstractBalloon.Mode> BALLOON_MODE = register("balloon_mode", builder -> builder.persistent(AbstractBalloon.Mode.CODEC));

	public static final DataComponentType<Boolean> RANDOM_COLOR = register("random_color", builder -> builder.persistent(Codec.BOOL));

	private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> operator) {
		ResourceLocation id = UpAndAway.id(name);
		return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, id, operator.apply(DataComponentType.builder()).build());
	}

	public static void init() {
	}
}
