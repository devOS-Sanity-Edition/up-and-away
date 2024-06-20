package one.devos.nautical.up_and_away.content.balloon.entity;

import java.util.Objects;
import java.util.Optional;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.Context;
import one.devos.nautical.up_and_away.content.UpAndAwayPackets;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;
import one.devos.nautical.up_and_away.framework.packet.ClientboundPacket;

import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;

public record BalloonAttachmentPacket(int entityId, Optional<CompoundTag> nbt) implements ClientboundPacket {
	public static final StreamCodec<RegistryFriendlyByteBuf, BalloonAttachmentPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, BalloonAttachmentPacket::entityId,
			ByteBufCodecs.OPTIONAL_COMPOUND_TAG, BalloonAttachmentPacket::nbt,
			BalloonAttachmentPacket::new
	);

	private static final Logger logger = LoggerFactory.getLogger(BalloonAttachmentPacket.class);

	public BalloonAttachmentPacket(AbstractBalloon balloon, @Nullable BalloonAttachment attachment) {
		this(balloon.getId(), Optional.ofNullable(attachment).map(BalloonAttachment::toNbt));
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void handle(Context ctx) {
		ClientLevel level = Objects.requireNonNull(ctx.client().level);
		Entity entity = level.getEntity(this.entityId);
		if (!(entity instanceof AbstractBalloon balloon)) {
			logger.error("No balloon with id {}, got {}", this.entityId, entity);
			return;
		}

		BalloonAttachment attachment = this.nbt.map(nbt -> BalloonAttachment.fromNbt(level, nbt)).orElse(null);
		balloon.setAttachment(attachment);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return UpAndAwayPackets.BALLOON_ATTACHMENT;
	}
}
