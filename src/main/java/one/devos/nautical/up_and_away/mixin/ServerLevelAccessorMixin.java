package one.devos.nautical.up_and_away.mixin;

import one.devos.nautical.up_and_away.content.balloon.entity.attachment.EntityBalloonAttachment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ServerLevelAccessor;

@Mixin(ServerLevelAccessor.class)
public interface ServerLevelAccessorMixin {
	@Shadow
	void addFreshEntityWithPassengers(Entity entity);

	@Inject(method = "addFreshEntityWithPassengers", at = @At("TAIL"))
	private void addBalloons(Entity entity, CallbackInfo ci) {
		EntityBalloonAttachment.getBalloons(entity).forEach(this::addFreshEntityWithPassengers);
	}
}
