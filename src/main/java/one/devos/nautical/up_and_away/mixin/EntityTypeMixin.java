package one.devos.nautical.up_and_away.mixin;

import java.util.function.Function;

import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.EntityBalloonAttachment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

@Mixin(EntityType.class)
public abstract class EntityTypeMixin {
	@Shadow
	public static Entity loadEntityRecursive(CompoundTag nbt, Level world, Function<Entity, Entity> entityProcessor) {
		throw new RuntimeException();
	}

	// lambda in loadEntityRecursive
	@Inject(method = "method_17843", at = @At("HEAD"))
	private static void loadBalloons(CompoundTag nbt, Level level, Function<Entity, Entity> function, Entity entity, CallbackInfoReturnable<Entity> cir) {
		if (nbt.contains(EntityBalloonAttachment.BALLOONS_KEY, Tag.TAG_LIST)) {
			ListTag list = nbt.getList(EntityBalloonAttachment.BALLOONS_KEY, Tag.TAG_COMPOUND);
			for (int i = 0; i < list.size(); i++) {
				CompoundTag tag = list.getCompound(i);
				BalloonAttachment attachment = EntityBalloonAttachment.fromNbt(tag.getCompound("attachment"), entity);
				if (attachment == null)
					continue;
				Entity balloon = loadEntityRecursive(tag.getCompound("entity"), level, function);
				if (balloon instanceof AbstractBalloon b) {
					b.setAttachment(attachment);
				}
			}
		}
	}
}
