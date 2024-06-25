package one.devos.nautical.up_and_away.content.balloon;

import java.util.Locale;
import java.util.function.IntFunction;

import com.mojang.serialization.Codec;

import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;

import net.minecraft.world.entity.EntityDimensions;

import org.jetbrains.annotations.NotNull;

public enum BalloonShape implements StringRepresentable {
	CUBE(EntityDimensions.fixed(14f / 16f, 1f)),
	DOG(EntityDimensions.fixed(1f, 22.025f / 16f)),
	HEART(EntityDimensions.fixed(21.25f / 16f, 18.275f / 16f)),
	LONG(EntityDimensions.fixed(3f / 16f, 2f)),
	ROUND(EntityDimensions.fixed(10f / 16f, 17f / 16f));

	public static final Codec<BalloonShape> CODEC = StringRepresentable.fromEnum(BalloonShape::values);
	public static final IntFunction<BalloonShape> BY_ID = ByIdMap.continuous(shape -> shape.id, values(), ByIdMap.OutOfBoundsStrategy.ZERO);

	public final String name = this.name().toLowerCase(Locale.ROOT);
	public final byte id = (byte) ordinal();
	public final EntityDimensions dimensions;

	BalloonShape(EntityDimensions dimensions) {
		this.dimensions = dimensions;
	}

	@Override
	@NotNull
	public String getSerializedName() {
		return this.name;
	}
}
