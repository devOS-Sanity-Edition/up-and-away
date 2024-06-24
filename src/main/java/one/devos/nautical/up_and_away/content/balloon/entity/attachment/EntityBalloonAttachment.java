package one.devos.nautical.up_and_away.content.balloon.entity.attachment;

import java.util.List;
import java.util.Locale;

import com.mojang.serialization.Codec;

import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import one.devos.nautical.up_and_away.framework.ext.EntityExt;
import one.devos.nautical.up_and_away.framework.util.Utils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public non-sealed class EntityBalloonAttachment extends BalloonAttachment {
	public static final String BALLOONS_KEY = UpAndAway.id("balloons").toString();

	public final Type type;
	public final Entity entity;

	public EntityBalloonAttachment(Entity entity, double stringLength) {
		this(Type.ENTITY, entity, stringLength);
	}

	protected EntityBalloonAttachment(Type type, Entity entity, double stringLength) {
		super(stringLength);
		this.type = type;
		this.entity = entity;
	}

	@Override
	public boolean validate() {
		return !this.entity.isRemoved();
	}

	@Override
	public Vec3 getPos(float partialTicks) {
		float height = this.entity.getBbHeight();
		return this.entity.getPosition(partialTicks).add(0, height / 2f, 0);
	}

	@Override
	protected void toNbt(CompoundTag nbt) {
	}

	@Override
	protected String typeName() {
		return this.type.name;
	}

	@Override
	public void onRemove(AbstractBalloon balloon) {
		getBalloons(this.entity).remove(balloon);
	}

	@Override
	public void onSet(AbstractBalloon balloon) {
		getBalloons(this.entity).add(balloon);
	}

	public static List<AbstractBalloon> getBalloons(Entity entity) {
		return ((EntityExt) entity).up_and_away$getBalloons();
	}

	public static EntityBalloonAttachment fromNbt(CompoundTag nbt, Entity attachedTo) {
		Type type = Utils.simpleDecode(Type.CODEC, nbt, TYPE_KEY);
		double stringLength = Utils.simpleDecode(STRING_LENGTH_CODEC, nbt, STRING_LENGTH_KEY);
		return type.factory.create(nbt, stringLength, attachedTo);
	}

	private static EntityBalloonAttachment fromNbt(CompoundTag nbt, double stringLength, Entity attachedTo) {
		return new EntityBalloonAttachment(attachedTo, stringLength);
	}

	public interface Factory {
		EntityBalloonAttachment create(CompoundTag nbt, double stringLength, Entity attachedTo);
	}

	public enum Type implements StringRepresentable {
		ENTITY(EntityBalloonAttachment::fromNbt),
		ENTITY_HAND(EntityHandBalloonAttachment::fromNbt);

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
