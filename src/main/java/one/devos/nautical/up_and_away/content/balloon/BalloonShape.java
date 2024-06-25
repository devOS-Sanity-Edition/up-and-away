package one.devos.nautical.up_and_away.content.balloon;

import java.util.Locale;
import java.util.function.IntFunction;

import com.mojang.serialization.Codec;

import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import org.jetbrains.annotations.NotNull;

public enum BalloonShape implements StringRepresentable {
	CUBE,
	DOG,
	HEART,
	LONG,
	ROUND;

	public static final Codec<BalloonShape> CODEC = StringRepresentable.fromEnum(BalloonShape::values);
	public static final IntFunction<BalloonShape> BY_ID = ByIdMap.continuous(shape -> shape.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

	public final String name = this.name().toLowerCase(Locale.ROOT);
	public final byte id = (byte) ordinal();

	@Override
	@NotNull
	public String getSerializedName() {
		return this.name;
	}
}
