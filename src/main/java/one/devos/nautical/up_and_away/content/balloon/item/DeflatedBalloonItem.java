package one.devos.nautical.up_and_away.content.balloon.item;

import one.devos.nautical.up_and_away.content.UpAndAwayItems;
import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class DeflatedBalloonItem extends BalloonItem {
	public static final int INFLATE_THRESHOLD = 20 * 2;
	public static final int POP_THRESHOLD = 20 * 4;

	public DeflatedBalloonItem(BalloonShape shape, Properties properties) {
		super(shape, properties);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
		return ItemUtils.startUsingInstantly(level, player, hand);
	}

	@Override
	public int getUseDuration(ItemStack stack, LivingEntity entity) {
		return POP_THRESHOLD;
	}

	@Override
	public UseAnim getUseAnimation(ItemStack stack) {
		return UseAnim.TOOT_HORN;
	}

	@Override
	public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingTicks) {
		entity.setAirSupply(entity.getAirSupply() - 6);
		if (entity.getAirSupply() <= -20) {
			entity.setAirSupply(0);
			entity.hurt(level.damageSources().drown(), 2);
		}
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int remainingTicks) {
		// released early
		int ticksUsedFor = this.getUseDuration(stack, entity) - remainingTicks;
		if (ticksUsedFor < INFLATE_THRESHOLD)
			return;

		Item inflated = UpAndAwayItems.AIR.get(this.shape);
		if (inflated == null)
			return;

		if (entity instanceof Player player) {
			ItemStack newStack = new ItemStack(inflated);
			newStack.applyComponents(stack.getComponents());
			ItemUtils.createFilledResult(stack, player, newStack, false);
		} else {
			stack.consume(1, entity);
		}
	}

	@Override
	public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
		// fully used, popped
		// based on milk buckets
		if (entity instanceof Player player) {
			return ItemUtils.createFilledResult(stack, player, ItemStack.EMPTY, false);
		} else {
			stack.consume(1, entity);
			return stack;
		}
	}
}
