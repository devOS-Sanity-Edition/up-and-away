package one.devos.nautical.up_and_away.mixin;

import java.util.function.Consumer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.framework.entity.ExtraSpawnPacketsEntity;

import one.devos.nautical.up_and_away.framework.entity.ExtraSpawnPacketsEntity.PacketConsumer;

import org.apache.commons.lang3.mutable.MutableInt;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;

@Mixin(ServerEntity.class)
public class ServerEntityMixin {
	@Shadow
	@Final
	private Entity entity;

	@WrapOperation(
			method = "sendPairingData",
			at = @At(
					value = "INVOKE",
					target = "Ljava/util/function/Consumer;accept(Ljava/lang/Object;)V",
					ordinal = 0
			)
	)
	private void addExtraSpawnPackets(Consumer<?> instance, Object packet, Operation<Void> original,
									  ServerPlayer player, Consumer<?> consumer2) {
		original.call(instance, packet);
		if (this.entity instanceof ExtraSpawnPacketsEntity entity) {
			MutableInt packets = new MutableInt();
			PacketConsumer consumer = new PacketConsumer(extra -> {
				original.call(instance, extra);
				packets.increment();
			});
			entity.addSpawnPackets(consumer);
			UpAndAway.LOGGER.info("sending {} extra packets to {}", packets, player.getName());
		}
	}
}
