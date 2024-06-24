package one.devos.nautical.up_and_away.content.balloon.entity.packet;

import java.util.Objects;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.Context;
import one.devos.nautical.up_and_away.content.UpAndAwayPackets;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.EntityBalloonAttachment;
import one.devos.nautical.up_and_away.framework.packet.ClientboundPacket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;

public record EntityBalloonAttachmentPacket(int balloonId, int attachedEntityId, CompoundTag nbt) implements ClientboundPacket {
	public static final StreamCodec<RegistryFriendlyByteBuf, EntityBalloonAttachmentPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, EntityBalloonAttachmentPacket::balloonId,
			ByteBufCodecs.VAR_INT, EntityBalloonAttachmentPacket::attachedEntityId,
			ByteBufCodecs.COMPOUND_TAG, EntityBalloonAttachmentPacket::nbt,
			EntityBalloonAttachmentPacket::new
	);

	private static final Logger logger = LoggerFactory.getLogger(EntityBalloonAttachmentPacket.class);

	public EntityBalloonAttachmentPacket(AbstractBalloon balloon, EntityBalloonAttachment attachment) {
		this(balloon.getId(), attachment.entity.getId(), attachment.toNbt());
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void handle(Context ctx) {
		ClientLevel level = Objects.requireNonNull(ctx.client().level);
		Entity entity = level.getEntity(this.balloonId);
		if (!(entity instanceof AbstractBalloon balloon)) {
			logger.error("No balloon with id {}, got {}", this.balloonId, entity);
			return;
		}

		Entity attachedTo = level.getEntity(this.attachedEntityId);
		if (attachedTo == null) {
			logger.error("No entity with id {}", this.attachedEntityId);
			return;
		}

		EntityBalloonAttachment attachment = EntityBalloonAttachment.fromNbt(this.nbt, attachedTo);
		balloon.setAttachment(attachment);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return UpAndAwayPackets.ENTITY_BALLOON_ATTACHMENT;
	}
}
