package one.devos.nautical.up_and_away.content.balloon.item;

import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;

import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BlockFaceBalloonAttachment;

import one.devos.nautical.up_and_away.content.balloon.entity.attachment.EntityBalloonAttachment;
import one.devos.nautical.up_and_away.framework.item.UsableOnEntityItem;

import org.jetbrains.annotations.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public abstract class FilledBalloonItem extends BalloonItem implements UsableOnEntityItem {
	protected FilledBalloonItem(BalloonShape shape, Properties properties) {
		super(shape, properties);
	}

	public abstract AbstractBalloon createEntity(Level level, ItemStack stack, @Nullable BalloonAttachment attachment);

	@Override
	public InteractionResult useOn(UseOnContext context) {
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Direction face = context.getClickedFace();
		BlockState state = level.getBlockState(pos);

		if (!Block.isFaceFull(state.getShape(level, pos), face))
			return InteractionResult.FAIL;

		double stringLength = BalloonAttachment.getStringLength(level.random);
		BalloonAttachment attachment = new BlockFaceBalloonAttachment(level, pos, face, stringLength);

		ItemStack stack = context.getItemInHand();
		Vec3 inFront = Vec3.atCenterOf(pos.relative(face));
		return this.spawnAttachedBalloon(level, stack, context.getPlayer(), attachment, inFront);
	}

	@Override
	public InteractionResult useOnEntity(ItemStack stack, Entity entity, Player user) {
		double stringLength = BalloonAttachment.getStringLength(entity.getRandom());
		BalloonAttachment attachment = new EntityBalloonAttachment(entity, stringLength);
		return this.spawnAttachedBalloon(entity.level(), stack, user, attachment, entity.getEyePosition());
	}

	protected InteractionResult spawnAttachedBalloon(Level level, ItemStack stack, @Nullable Player user, BalloonAttachment attachment, Vec3 spawnPos) {
		if (!level.isClientSide) {
			AbstractBalloon balloon = this.createEntity(level, stack, attachment);

			balloon.setPos(spawnPos);
			level.addFreshEntity(balloon);

			if (user != null) {
				stack.consume(1, user);
			}
		}
		return InteractionResult.SUCCESS;
	}
}
