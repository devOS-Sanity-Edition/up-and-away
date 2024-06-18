package one.devos.nautical.up_and_away.content.balloon.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public enum BalloonCartInteraction {
	TEST(
			AABB.ofSize(new Vec3(0, BalloonCart.HEIGHT, 0), 2 / 16d, 2 / 16d, 8 / 16d),
			new Vec3(255, 255, 0)
	) {
		@Override
		public boolean interact(BalloonCart cart, Player player) {
			if (player.level() instanceof ServerLevel level) {
				if (player.getRandom().nextInt(0, 40) <= 20) {
					player.sendSystemMessage(Component.literal("You just won the game!"));
					for (int i = 0; i < 8; i++) {
						EntityType.SQUID.spawn(level, player.blockPosition().above(i), MobSpawnType.BREEDING);
						BlockPos funnyPos = player.blockPosition().relative(Direction.getRandom(player.getRandom()), i).relative(Direction.getRandom(player.getRandom()), (int) (i / 2));
						BuiltInRegistries.BLOCK.getRandom(player.getRandom())
								.map(Holder.Reference::value)
								.ifPresent(block -> level.setBlock(funnyPos, block.defaultBlockState(), Block.UPDATE_CLIENTS));
					}
				} else {
					player.sendSystemMessage(Component.literal("You just lost the game"));
				}
			}
			return true;
		}
	};

	public static final BalloonCartInteraction[] VALUES = values();

	public final AABB hitBox;
	public final Vec3 debugColor;

	BalloonCartInteraction(AABB hitBox, Vec3 debugColor) {
		this.hitBox = hitBox;
		this.debugColor = debugColor;
	}

	public abstract boolean interact(BalloonCart cart, Player player);
}
