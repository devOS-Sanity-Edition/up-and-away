package one.devos.nautical.up_and_away.content.balloon.item;

import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public abstract class FilledBalloonItem extends BalloonItem {
	protected FilledBalloonItem(BalloonShape shape, Properties properties) {
		super(shape, properties);
	}

	public abstract AbstractBalloon createEntity(Level level, ItemStack stack, @Nullable BalloonAttachment attachment);

	public float throwingForce() {
		return 0.2f;
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		ItemStack stack = player.getItemInHand(hand);
		if (!level.isClientSide) {
			AbstractBalloon balloon = this.createEntity(level, stack, null);
			balloon.setPos(player.getEyePosition());
			Vec3 lookVec = player.getLookAngle().normalize();
			balloon.setDeltaMovement(lookVec.scale(this.throwingForce()));
			level.addFreshEntity(balloon);
			stack.consume(1, player);
		}
		return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
	}
}
