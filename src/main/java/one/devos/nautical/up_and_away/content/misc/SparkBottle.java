package one.devos.nautical.up_and_away.content.misc;

import net.minecraft.Optionull;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

import net.minecraft.world.phys.Vec3;
import one.devos.nautical.up_and_away.content.UpAndAwayItems;

import org.jetbrains.annotations.Nullable;

public class SparkBottle extends Item {
	public static final double RANGE = 64;

	public SparkBottle(Properties settings) {
		super(settings);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		if (!world.isClientSide) {
			user.awardStat(Stats.ITEM_USED.get(this));
			Vec3 start = user.getEyePosition();
			Vec3 delta = user
					.calculateViewVector(user.getXRot(), user.getYRot())
					.scale(RANGE);
			Entity target = Optionull.mapOrDefault(ProjectileUtil.getEntityHitResult(
					user,
					start,
					start.add(delta),
					user.getBoundingBox().expandTowards(delta).inflate(1),
					EntitySelector.NO_SPECTATORS,
					Mth.square(RANGE)
			), EntityHitResult::getEntity, user);
			world.addFreshEntity(new ShulkerBullet(world, user, target, null));
		}

		user.playSound(SoundEvents.SHULKER_BULLET_HIT);

		ItemStack stack = user.getItemInHand(hand);
		return InteractionResultHolder.sidedSuccess(
				!user.hasInfiniteMaterials() ?
						ItemUtils.createFilledResult(user.getItemInHand(hand), user, Items.GLASS_BOTTLE.getDefaultInstance()) : stack,
				world.isClientSide
		);
	}

	public static InteractionResult onUseEntity(Player player, Level world, InteractionHand hand, Entity entity, @Nullable EntityHitResult hitResult) {
		ItemStack stack = player.getItemInHand(hand);
		if (entity.getType().equals(EntityType.SHULKER_BULLET) && stack.is(Items.GLASS_BOTTLE)) {
			if (!world.isClientSide) {
				player.awardStat(Stats.ITEM_USED.get(Items.GLASS_BOTTLE));
				// neutral sound source for consistency+ with dragon breath filling
				world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1f, 1f);
				player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, UpAndAwayItems.SPARK_BOTTLE.getDefaultInstance()));
				entity.kill();
			}
			return InteractionResult.sidedSuccess(world.isClientSide);
		}
		return InteractionResult.PASS;
	}
}
