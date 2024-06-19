package one.devos.nautical.up_and_away.content.balloon.item;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import one.devos.nautical.up_and_away.content.UpAndAwayEntities;

public class BalloonCartItem extends Item {
	public BalloonCartItem(Properties settings) {
		super(settings);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (context.getLevel() instanceof ServerLevel level) {
			BlockPos pos = context.getClickedPos();
			Direction face = context.getClickedFace();
			BlockState state = level.getBlockState(pos);

			boolean invertY = false;
			if (!state.getCollisionShape(level, pos).isEmpty()) {
				pos = pos.relative(face);
				invertY = face == Direction.UP;
			}

			if (UpAndAwayEntities.BALLOON_CART.spawn(
					level,
					cart -> cart.setYRot(Mth.wrapDegrees((context.getRotation() - 180) - 90)), // use post consumer or else we will set the rotation after spawn, and it will cause a lerp on the client
					pos,
					MobSpawnType.SPAWN_EGG,
					true,
					invertY
			) != null) {
				context.getItemInHand().shrink(1);
				level.gameEvent(context.getPlayer(), GameEvent.ENTITY_PLACE, pos);
			}
		}
		return InteractionResult.sidedSuccess(context.getLevel().isClientSide);
	}
}
