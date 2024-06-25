package one.devos.nautical.up_and_away.content.balloon.item.filled;

import one.devos.nautical.up_and_away.content.UpAndAwayEntities;
import one.devos.nautical.up_and_away.content.UpAndAwayItems;
import one.devos.nautical.up_and_away.content.UpAndAwaySounds;
import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.FloatyBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.attachment.BalloonAttachment;
import one.devos.nautical.up_and_away.content.balloon.item.FilledBalloonItem;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class FloatyBalloonItem extends FilledBalloonItem {
	public FloatyBalloonItem(BalloonShape shape, Properties properties) {
		super(shape, properties);
	}

	@Override
	public AbstractBalloon createEntity(Level level, ItemStack stack, @Nullable BalloonAttachment attachment) {
		return new FloatyBalloon(UpAndAwayEntities.FLOATY_BALLOON, level, stack, attachment);
	}

	public static class Long extends FloatyBalloonItem {
		public Long(BalloonShape shape, Properties properties) {
			super(shape, properties);
		}

		@Override
		public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
			return ItemUtils.startUsingInstantly(world, user, hand);
		}

		@Override
		public int getUseDuration(ItemStack stack, LivingEntity livingEntity) {
			return AirBalloonItem.Long.ANIMAL_TICKS;
		}

		@Override
		public UseAnim getUseAnimation(ItemStack stack) {
			return UseAnim.BRUSH;
		}

		@Override
		public void onUseTick(Level world, LivingEntity user, ItemStack stack, int remainingUseTicks) {
			if (remainingUseTicks % 40 == 0) {
				user.playSound(UpAndAwaySounds.BALLOON_ANIMAL_TIE);
			}
		}

		@Override
		public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
			return AirBalloonItem.Long.handleFinishMakingAnimal(stack, entity, UpAndAwayItems.FLOATY);
		}
	}
}
