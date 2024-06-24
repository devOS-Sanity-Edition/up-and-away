package one.devos.nautical.up_and_away.content.balloon.entity.packet;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking.Context;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.world.entity.Entity;
import one.devos.nautical.up_and_away.content.UpAndAwayPackets;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import one.devos.nautical.up_and_away.framework.packet.ClientboundPacket;

public record BalloonDetachPacket(int balloonId) implements ClientboundPacket {
	public static final StreamCodec<RegistryFriendlyByteBuf, BalloonDetachPacket> CODEC = StreamCodec.composite(
			ByteBufCodecs.VAR_INT, BalloonDetachPacket::balloonId,
			BalloonDetachPacket::new
	);

	private static final Logger logger = LoggerFactory.getLogger(BalloonDetachPacket.class);

	public BalloonDetachPacket(AbstractBalloon balloon) {
		this(balloon.getId());
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

		balloon.setAttachment(null);
	}

	@Override
	public Type<? extends CustomPacketPayload> type() {
		return UpAndAwayPackets.BALLOON_DETACH;
	}
}
