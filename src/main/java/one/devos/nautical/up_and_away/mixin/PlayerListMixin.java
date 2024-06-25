package one.devos.nautical.up_and_away.mixin;

import java.util.Optional;
import java.util.function.Function;

import com.llamalad7.mixinextras.sugar.Local;

import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.EntityBalloonAttachment;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Connection;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;

@Mixin(PlayerList.class)
public class PlayerListMixin {
	@Inject(method = "placeNewPlayer", at = @At(value = "INVOKE", target = "Ljava/util/Optional;isPresent()Z"))
	private void loadPlayerBalloons(Connection connection, ServerPlayer player, CommonListenerCookie commonListenerCookie, CallbackInfo ci,
									@Local(ordinal = 1) ServerLevel level,
									@Local(ordinal = 0) Optional<CompoundTag> maybeNbt) {
		if (maybeNbt.isEmpty())
			return;

		CompoundTag nbt = maybeNbt.get();
		if (nbt.contains(EntityBalloonAttachment.BALLOONS_KEY, Tag.TAG_LIST)) {
			ListTag list = nbt.getList(EntityBalloonAttachment.BALLOONS_KEY, Tag.TAG_COMPOUND);
			for (int i = 0; i < list.size(); i++) {
				CompoundTag tag = list.getCompound(i);
				BalloonAttachment attachment = EntityBalloonAttachment.fromNbt(tag.getCompound("attachment"), player);
				if (attachment == null)
					continue;

				Entity balloon = EntityType.loadEntityRecursive(tag.getCompound("entity"), level, Function.identity());

				if (balloon instanceof AbstractBalloon b) {
					b.setAttachment(attachment);
				}

				level.addFreshEntityWithPassengers(balloon);
			}
		}
	}
}
