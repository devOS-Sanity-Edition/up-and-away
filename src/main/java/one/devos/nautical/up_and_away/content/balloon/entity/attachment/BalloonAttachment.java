package one.devos.nautical.up_and_away.content.balloon.entity.attachment;

import com.mojang.serialization.Codec;

import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import one.devos.nautical.up_and_away.framework.util.Utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.phys.Vec3;

public abstract sealed class BalloonAttachment permits BlockBalloonAttachment, EntityBalloonAttachment {
	public static final String TYPE_KEY = "type";
	public static final String STRING_LENGTH_KEY = "string_length";
	public static final Codec<Double> STRING_LENGTH_CODEC = Codec.doubleRange(0, Double.MAX_VALUE);
	public static final double BASE_STRING_LENGTH = 4;
	public static final double STRING_LENGTH_DEVIATION = 1;

	public static final double TELEPORT_THRESHOLD = 15;

	public final double stringLength;

	protected BalloonAttachment(double stringLength) {
		this.stringLength = stringLength;
	}

	/**
	 * Check if still valid. Will be removed immediately after if not. Only called server side.
	 * @return true if still valid
	 */
	public abstract boolean validate();

	public abstract Vec3 getPos(float partialTicks);

	protected abstract void toNbt(CompoundTag nbt);

	protected abstract String typeName();

	/**
	 * Called when this attachment's balloon is removed for any reason.
	 */
	public void onRemove(AbstractBalloon balloon) {
	}

	/**
	 * Called when this attachment is assigned to a balloon.
	 */
	public void onSet(AbstractBalloon balloon) {
	}

	// utilities

	public final CompoundTag toNbt() {
		CompoundTag nbt = new CompoundTag();
		nbt.putString(TYPE_KEY, this.typeName());
		nbt.putDouble(STRING_LENGTH_KEY, this.stringLength);
		this.toNbt(nbt);
		return nbt;
	}

	public final Vec3 getPos() {
		return this.getPos(1);
	}

	public final boolean isTooFar(Vec3 pos) {
		return pos.distanceTo(this.getPos()) > this.stringLength;
	}

	public final boolean shouldTeleport(Vec3 pos) {
		return pos.distanceTo(this.getPos()) > TELEPORT_THRESHOLD;
	}

	public static double getStringLength(RandomSource random) {
		return Utils.nextDouble(random, BASE_STRING_LENGTH - STRING_LENGTH_DEVIATION, BASE_STRING_LENGTH + STRING_LENGTH_DEVIATION);
	}
}
