package one.devos.nautical.up_and_away.content.balloon.entity.attachment;

import java.util.Locale;

import com.mojang.serialization.Codec;

import one.devos.nautical.up_and_away.UpAndAway;

import one.devos.nautical.up_and_away.framework.util.Utils;

import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class BalloonAttachment {
	public static final String TYPE_KEY = "type";
	public static final String STRING_LENGTH_KEY = "string_length";
	public static final Codec<Double> STRING_LENGTH_CODEC = Codec.doubleRange(0, Double.MAX_VALUE);
	public static final double BASE_STRING_LENGTH = 4;
	public static final double STRING_LENGTH_DEVIATION = 1;

	public static final double TELEPORT_THRESHOLD = 15;

	public final Type type;
	public final double stringLength;

	protected BalloonAttachment(Type type, double stringLength) {
		this.type = type;
		this.stringLength = stringLength;
	}

	/**
	 * Check if still valid. Will be removed immediately after if not. Only called server side.
	 * @return true if still valid
	 */
	public abstract boolean validate();

	public abstract Vec3 getPos();

	protected abstract void toNbt(CompoundTag nbt);

	// utilities

	public CompoundTag toNbt() {
		CompoundTag nbt = new CompoundTag();
		nbt.putString(TYPE_KEY, this.type.name);
		nbt.putDouble(STRING_LENGTH_KEY, this.stringLength);
		this.toNbt(nbt);
		return nbt;
	}

	public boolean isTooFar(Vec3 pos) {
		return pos.distanceTo(this.getPos()) > this.stringLength;
	}

	public boolean shouldTeleport(Vec3 pos) {
		return pos.distanceTo(this.getPos()) > TELEPORT_THRESHOLD;
	}

	@Nullable
	public static BalloonAttachment fromNbt(Level level, CompoundTag nbt) {
		try {
			Type type = Utils.simpleDecode(Type.CODEC, nbt, TYPE_KEY);
			double stringLength = Utils.simpleDecode(STRING_LENGTH_CODEC, nbt, STRING_LENGTH_KEY);
			return type.factory.create(level, nbt, stringLength);
		} catch (Throwable t) {
			UpAndAway.LOGGER.error("Failed to deserialize balloon attachment", t);
			return null;
		}
	}

	public static double getStringLength(RandomSource random) {
		return Utils.nextDouble(random, BASE_STRING_LENGTH - STRING_LENGTH_DEVIATION, BASE_STRING_LENGTH + STRING_LENGTH_DEVIATION);
	}

	public interface Factory {
		BalloonAttachment create(Level level, CompoundTag nbt, double stringLength);
	}

	public enum Type implements StringRepresentable {
		BLOCK_FACE(BlockFaceBalloonAttachment::fromNbt),
		ENTITY(EntityBalloonAttachment::fromNbt);

		public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

		public final String name = this.name().toLowerCase(Locale.ROOT);
		public final Factory factory;

		Type(Factory factory) {
			this.factory = factory;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}
