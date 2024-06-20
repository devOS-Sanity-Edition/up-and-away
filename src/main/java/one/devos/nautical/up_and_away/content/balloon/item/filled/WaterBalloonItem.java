package one.devos.nautical.up_and_away.content.balloon.item.filled;

import one.devos.nautical.up_and_away.content.UpAndAwayEntities;
import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.WaterBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;
import one.devos.nautical.up_and_away.content.balloon.item.FilledBalloonItem;
import org.jetbrains.annotations.Nullable;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class WaterBalloonItem extends FilledBalloonItem {
	public WaterBalloonItem(BalloonShape shape, Properties properties) {
		super(shape, properties);
	}

	@Override
	public AbstractBalloon createEntity(Level level, ItemStack stack, @Nullable BalloonAttachment attachment) {
		return new WaterBalloon(UpAndAwayEntities.WATER_BALLOON, level, stack, attachment);
	}

	@Override
	public InteractionResult useOn(UseOnContext context) {
		if (context.isSecondaryUseActive())
			return super.useOn(context);
		return InteractionResult.PASS; // continue to use
	}

	@Override
	public InteractionResult useOnEntity(ItemStack stack, Entity entity, Player user) {
		if (user.isSecondaryUseActive())
			return super.useOnEntity(stack, entity, user);
		return InteractionResult.PASS; // continue to use
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!level.isClientSide) {
			AbstractBalloon balloon = this.createEntity(level, stack, null);
			balloon.setPos(player.getEyePosition());
			Vec3 lookVec = player.getLookAngle();
			balloon.setDeltaMovement(lookVec.normalize());
			level.addFreshEntity(balloon);
			stack.consume(1, player);
		}
		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
	}
}
