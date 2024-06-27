package one.devos.nautical.up_and_away.content.balloon.item;

import net.minecraft.core.particles.ItemParticleOption;
import one.devos.nautical.up_and_away.content.UpAndAwayItems;
import one.devos.nautical.up_and_away.content.UpAndAwaySounds;
import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class DeflatedBalloonItem extends BalloonItem {
	public static final int TICKS_PER_PUFF = 40; // sound is 1.93s
	public static final int DEFLATE_THRESHOLD = TICKS_PER_PUFF;
	public static final int POP_THRESHOLD = TICKS_PER_PUFF * 3;
	public static final int INFLATE_THRESHOLD = POP_THRESHOLD - 10;

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
	public void onUseTick(Level level, LivingEntity entity, ItemStack stack, int remainingTicks) {
		entity.setAirSupply(entity.getAirSupply() - 6);
		if (entity.getAirSupply() <= -20) {
			entity.setAirSupply(0);
			entity.hurt(level.damageSources().drown(), 2);
		}

		int ticksUsedFor = this.getUseDuration(stack, entity) - remainingTicks;
		// not sure why but rarely this will be called 1 extra time at POP_THRESHOLD
		if (ticksUsedFor < POP_THRESHOLD && ticksUsedFor % TICKS_PER_PUFF == 0) {
			entity.playSound(UpAndAwaySounds.BALLOON_INFLATE);
		}
	}

	@Override
	public void releaseUsing(ItemStack stack, Level level, LivingEntity entity, int remainingTicks) {
		// released early
		int ticksUsedFor = this.getUseDuration(stack, entity) - remainingTicks;
		if (ticksUsedFor < INFLATE_THRESHOLD) {
			// not inflated enough
			if (ticksUsedFor > DEFLATE_THRESHOLD) {
				entity.playSound(UpAndAwaySounds.BALLOON_DEFLATE);
			}

			return;
		}

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
		pop(entity.level(), entity.getEyePosition(), entity.getSoundSource());
		if (entity instanceof Player player) {
			player.getCooldowns().addCooldown(stack.getItem(), 20);
			return ItemUtils.createFilledResult(stack, player, ItemStack.EMPTY, false);
		} else {
			stack.consume(1, entity);
			return stack;
		}
	}

	public static void pop(Level level, Vec3 pos, SoundSource source) {
		level.playSound(null, pos.x, pos.y, pos.z, UpAndAwaySounds.BALLOON_POP, source);
		if (level instanceof ServerLevel serverLevel) {
			ItemParticleOption particles = new ItemParticleOption(ParticleTypes.ITEM, UpAndAwayItems.STRETCHY_SHEET.getDefaultInstance());
			serverLevel.sendParticles(particles, pos.x, pos.y, pos.z, 10, 0, 0, 0, 1);
		}
	}
}
