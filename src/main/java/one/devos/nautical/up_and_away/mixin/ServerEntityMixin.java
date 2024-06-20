package one.devos.nautical.up_and_away.mixin;

import java.util.function.Consumer;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;

import one.devos.nautical.up_and_away.content.balloon.entity.BalloonAttachmentPacket;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.server.level.ServerEntity;
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
	private void addBalloonSpawnData(Consumer<?> instance, Object packet, Operation<Void> original) {
		original.call(instance, packet);
		if (this.entity instanceof AbstractBalloon balloon) {
			original.call(instance, ServerPlayNetworking.createS2CPacket(
					new BalloonAttachmentPacket(balloon, balloon.attachment())
			));
		}
	}
}
