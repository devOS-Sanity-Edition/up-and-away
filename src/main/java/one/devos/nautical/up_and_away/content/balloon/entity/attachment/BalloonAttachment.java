package one.devos.nautical.up_and_away.content.balloon.entity.attachment;

import java.util.Locale;

import com.mojang.serialization.Codec;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.StringRepresentable;
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

	default void toNbt(CompoundTag nbt) {

	}

	// utilities

	default void toNetwork(FriendlyByteBuf buf) {
		CompoundTag nbt = new CompoundTag();
		this.toNbt(nbt);
		buf.writeNbt(nbt);
	}

	default boolean isTooFar(Vec3 pos) {
		return pos.distanceTo(this.getPos()) > this.getStringLength();
	}

	default boolean shouldTeleport(Vec3 pos) {
		return pos.distanceTo(this.getPos()) > this.getTeleportThreshold();
	}

	static BalloonAttachment fromNbt(CompoundTag nbt) {
		return null;
	}

	static BalloonAttachment fromNetwork(FriendlyByteBuf buf) {
		CompoundTag nbt = buf.readNbt();
		return fromNbt(nbt);
	}

	enum Type implements StringRepresentable {
		BLOCK_FACE,
		ENTITY;

		public final String name = this.name().toLowerCase(Locale.ROOT);

		public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}
