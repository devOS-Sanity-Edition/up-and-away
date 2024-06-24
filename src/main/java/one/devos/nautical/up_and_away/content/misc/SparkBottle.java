package one.devos.nautical.up_and_away.content.misc;

import net.minecraft.Optionull;
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
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

import net.minecraft.world.phys.Vec3;
import one.devos.nautical.up_and_away.content.UpAndAwayItems;

import org.jetbrains.annotations.Nullable;

public class SparkBottle extends Item {
	private static final double RANGE = 64;

	public SparkBottle(Properties settings) {
		super(settings);
	}

	@Override
	public InteractionResultHolder<ItemStack> use(Level world, Player user, InteractionHand hand) {
		BlockHitResult hit = getPlayerPOVHitResult(world, user, ClipContext.Fluid.ANY);
		if (hit.getType() == HitResult.Type.MISS) {
			if (!world.isClientSide) {
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
			return InteractionResultHolder.sidedSuccess(Items.GLASS_BOTTLE.getDefaultInstance(), world.isClientSide);
		}
		return super.use(world, user, hand);
	}

	public static InteractionResult onUseEntity(Player player, Level world, InteractionHand hand, Entity entity, @Nullable EntityHitResult hitResult) {
		if (entity.getType().equals(EntityType.SHULKER_BULLET) && player.getItemInHand(hand).is(Items.GLASS_BOTTLE)) {
			if (!world.isClientSide) {
				player.setItemInHand(hand, UpAndAwayItems.SPARK_BOTTLE.getDefaultInstance());
				entity.kill();
			}
			return InteractionResult.sidedSuccess(world.isClientSide);
		}
		return InteractionResult.PASS;
	}
}
