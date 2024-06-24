package one.devos.nautical.up_and_away.content.balloon.item.filled;

import one.devos.nautical.up_and_away.content.UpAndAwayEntities;
import one.devos.nautical.up_and_away.content.UpAndAwayItems;
import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.AirBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;
import one.devos.nautical.up_and_away.content.balloon.item.BalloonItem;
import one.devos.nautical.up_and_away.content.balloon.item.FilledBalloonItem;

import one.devos.nautical.up_and_away.framework.item.Balloons;

import org.jetbrains.annotations.Nullable;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class AirBalloonItem extends FilledBalloonItem {
	public AirBalloonItem(BalloonShape shape, Properties properties) {
		super(shape, properties);
	}

	@Override
	public AbstractBalloon createEntity(Level level, ItemStack stack, @Nullable BalloonAttachment attachment) {
		return new AirBalloon(UpAndAwayEntities.AIR_BALLOON, level, stack, attachment);
	}

	public static class Long extends AirBalloonItem {
		public static final int ANIMAL_TICKS = 40;

		public Long(BalloonShape shape, Properties properties) {
			super(shape, properties);
		}

		@Override
		public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
			return ItemUtils.startUsingInstantly(world, user, hand);
		}

		@Override
		public int getUseDuration(ItemStack stack, LivingEntity livingEntity) {
			return ANIMAL_TICKS;
		}

		@Override
		public UseAnim getUseAnimation(ItemStack stack) {
			return UseAnim.BRUSH;
		}

		@Override
		public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
			if (remainingUseTicks % 10 == 0) {
				user.playSound(SoundEvents.SLIME_BLOCK_PLACE);
			}
		}

		@Override
		public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
			return handleFinishMakingAnimal(stack, entity, UpAndAwayItems.AIR);
		}

		public static ItemStack handleFinishMakingAnimal(ItemStack stack, LivingEntity entity, Balloons balloons) {
			// based on honey bottles
			BalloonItem item = balloons.get(BalloonShape.DOG);
			if (item == null) { // oh no
				return ItemStack.EMPTY;
			}

			ItemStack dog = new ItemStack(item);
			dog.applyComponents(stack.getComponents());

			if (entity instanceof Player player) {
				player.getCooldowns().addCooldown(stack.getItem(), 10);
				return ItemUtils.createFilledResult(stack, player, dog, false);
			} else {
				stack.consume(1, entity);
			}

			return stack;
		}
	}
}
