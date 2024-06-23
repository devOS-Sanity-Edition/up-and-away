package one.devos.nautical.up_and_away.content.balloon.entity.attachment;

import java.util.Objects;
import java.util.UUID;

import one.devos.nautical.up_and_away.framework.util.Utils;
import one.devos.nautical.up_and_away.mixin.LevelAccessor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.UUIDUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class EntityBalloonAttachment extends BalloonAttachment {
	public static final String ID_KEY = "id";

	private final Entity entity;

	public EntityBalloonAttachment(Entity entity, double stringLength) {
		super(Type.ENTITY, stringLength);
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
	public void toNbt(CompoundTag nbt) {
		nbt.putUUID(ID_KEY, this.entity.getUUID());
	}

	public static BalloonAttachment fromNbt(Level level, CompoundTag nbt, double stringLength) {
		UUID id = Utils.simpleDecode(UUIDUtil.CODEC, nbt, ID_KEY);
		Entity entity = ((LevelAccessor) level).callGetEntities().get(id);
		Objects.requireNonNull(entity, "entity");
		return new EntityBalloonAttachment(entity, stringLength);
	}
}
