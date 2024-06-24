package one.devos.nautical.up_and_away.content.balloon.entity.attachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;

import one.devos.nautical.up_and_away.framework.util.Utils;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;

public class EntityHandBalloonAttachment extends EntityBalloonAttachment {
	public static final Codec<InteractionHand> HAND_CODEC = Codec.STRING.comapFlatMap(
			string -> switch (string) {
				case "main" -> DataResult.success(InteractionHand.MAIN_HAND);
				case "offhand" -> DataResult.success(InteractionHand.OFF_HAND);
				default -> DataResult.error(() -> string);
			},
			hand -> hand == InteractionHand.MAIN_HAND ? "main" : "offhand"
	);

	public static final String HAND_KEY = "hand";

	private final InteractionHand hand;

	public EntityHandBalloonAttachment(Entity entity, InteractionHand hand, double stringLength) {
		super(Type.ENTITY_HAND, entity, stringLength);
		this.hand = hand;
	}

	@Override
	public boolean validate() {
		if (!super.validate() || !(this.entity instanceof LivingEntity entity))
			return false;

		ItemStack stack = entity.getItemInHand(this.hand);
		return true;
	}

	@Override
	protected void toNbt(CompoundTag nbt) {
		nbt.put(HAND_KEY, HAND_CODEC.encodeStart(NbtOps.INSTANCE, this.hand).getOrThrow());
	}

	@Override
	public Vec3 getPos(float partialTicks) {
		return this.entity.getRopeHoldPosition(partialTicks);
	}

	public static EntityHandBalloonAttachment fromNbt(CompoundTag nbt, double stringLength, Entity attachedTo) {
		InteractionHand hand = Utils.simpleDecode(HAND_CODEC, nbt, HAND_KEY);
		return new EntityHandBalloonAttachment(attachedTo, hand, stringLength);
	}
}
