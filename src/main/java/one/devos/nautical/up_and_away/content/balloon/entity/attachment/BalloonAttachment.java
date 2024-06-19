package one.devos.nautical.up_and_away.content.balloon.entity.attachment;

import java.util.Locale;
import java.util.function.BiFunction;

import com.mojang.serialization.Codec;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public interface BalloonAttachment {
	/**
	 * Check if still valid.
	 * @return true if still valid
	 */
	boolean validate();

	Vec3 getPos();

	double getStringLength();

	default double getTeleportThreshold() {
		return 15;
	}

	void toNbt(CompoundTag nbt);

	// utilities

	default CompoundTag toNbt() {
		CompoundTag nbt = new CompoundTag();
		this.toNbt(nbt);
		return nbt;
	}

	default boolean isTooFar(Vec3 pos) {
		return pos.distanceTo(this.getPos()) > this.getStringLength();
	}

	default boolean shouldTeleport(Vec3 pos) {
		return pos.distanceTo(this.getPos()) > this.getTeleportThreshold();
	}

	static BalloonAttachment fromNbt(CompoundTag nbt, Level level) {
		Type type = Type.CODEC.decode(NbtOps.INSTANCE, nbt.getCompound("type")).getOrThrow().getFirst();
		return type.factory.apply(nbt, level);
	}

	enum Type implements StringRepresentable {
		BLOCK_FACE(BlockFaceBalloonAttachment::fromNbt),
		ENTITY(EntityBalloonAttachment::fromNbt);

		public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

		public final String name = this.name().toLowerCase(Locale.ROOT);
		public final BiFunction<CompoundTag, Level, BalloonAttachment> factory;

		Type(BiFunction<CompoundTag, Level, BalloonAttachment> factory) {
			this.factory = factory;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}
