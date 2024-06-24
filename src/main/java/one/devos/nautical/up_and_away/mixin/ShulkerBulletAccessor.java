package one.devos.nautical.up_and_away.mixin;

import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ShulkerBullet;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(ShulkerBullet.class)
public interface ShulkerBulletAccessor {
	@Accessor
	void setFinalTarget(Entity finalTarget);

	@Invoker
	void callSelectNextMoveDirection(@Nullable Direction.Axis axis);
}
