package one.devos.nautical.up_and_away.framework.entity;

import java.util.function.Consumer;

import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.ClientboundCustomPayloadPacket;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.game.ClientGamePacketListener;

public interface ExtraSpawnPacketsEntity {
	/**
	 * Send extra packets to clients when this entity is synced.
	 * These packets are bundled, so they will be executed immediately after the base spawn packet.
	 */
	void addSpawnPackets(PacketConsumer consumer);

	record PacketConsumer(Consumer<Packet<? super ClientGamePacketListener>> consumer) {
		public void add(Packet<? super ClientGamePacketListener> packet) {
			this.consumer.accept(packet);
		}

		public void add(CustomPacketPayload payload) {
			this.add(new ClientboundCustomPayloadPacket(payload));
		}
	}
}
