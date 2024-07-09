package one.devos.nautical.up_and_away.content.balloon.entity.packet;

import java.util.Objects;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.Context;
import one.devos.nautical.up_and_away.content.UpAndAwayPackets;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BlockBalloonAttachment;
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

public record BlockBalloonAttachmentPacket(int balloonId, CompoundTag nbt) implements ClientboundPacket {
	public static final StreamCodec<RegistryFriendlyByteBuf, BlockBalloonAttachmentPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, BlockBalloonAttachmentPacket::balloonId,
			ByteBufCodecs.COMPOUND_TAG, BlockBalloonAttachmentPacket::nbt,
			BlockBalloonAttachmentPacket::new
	);

	private static final Logger logger = LoggerFactory.getLogger(BlockBalloonAttachmentPacket.class);

	public BlockBalloonAttachmentPacket(AbstractBalloon balloon, BlockBalloonAttachment attachment) {
		this(balloon.getId(), attachment.toNbt());
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void handle(Context ctx) {
		logger.info("BlockBalloonAttachmentPacket");
		ClientLevel level = Objects.requireNonNull(ctx.client().level);
		Entity entity = level.getEntity(this.balloonId);
		if (!(entity instanceof AbstractBalloon balloon)) {
			logger.error("No balloon with id {}, got {}", this.balloonId, entity);
			return;
		}

		BalloonAttachment attachment = BlockBalloonAttachment.fromNbt(nbt, level);
		balloon.setAttachment(attachment);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return UpAndAwayPackets.BLOCK_BALLOON_ATTACHMENT;
	}
}
