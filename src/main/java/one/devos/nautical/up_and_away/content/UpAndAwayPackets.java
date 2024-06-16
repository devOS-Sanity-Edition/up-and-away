package one.devos.nautical.up_and_away.content;

import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.loader.api.FabricLoader;
import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.content.balloon.entity.BalloonAttachmentPacket;
import one.devos.nautical.up_and_away.framework.packet.ClientboundPacket;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public class UpAndAwayPackets {
	public static final CustomPacketPayload.Type<? extends CustomPacketPayload>
			BALLOON_ATTACHMENT = clientbound("balloon_attachment", BalloonAttachmentPacket.CODEC);

	private static <T extends ClientboundPacket> CustomPacketPayload.Type<T> clientbound(String name, StreamCodec<RegistryFriendlyByteBuf, T> codec) {
		CustomPacketPayload.Type<T> type = new CustomPacketPayload.Type<>(UpAndAway.id(name));
		PayloadTypeRegistry.playS2C().register(type, codec);

		if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
			ClientPlayNetworking.registerGlobalReceiver(type, ClientboundPacket::handle);
		}

		return type;
	}
}
