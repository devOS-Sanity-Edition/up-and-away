package one.devos.nautical.up_and_away.content.misc;

import com.google.common.collect.Iterables;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.projectile.ShulkerBullet;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import one.devos.nautical.up_and_away.mixin.ShulkerBulletAccessor;

import java.util.List;

public class SparkBottleDispenseBehavior extends DefaultDispenseItemBehavior {
	@Override
	protected ItemStack execute(BlockSource pointer, ItemStack stack) {
		Level world = pointer.level();
		Direction facing = pointer.state().getValue(DispenserBlock.FACING);
		BlockPos pos = pointer.pos();
		BlockPos posFacing = pos.relative(facing);
		if (world.getBlockState(posFacing).isAir()) {
			List<Entity> targets = world.getEntities(
					(Entity) null,
					AABB
							.unitCubeFromLowerCorner(Vec3.atLowerCornerOf(pos))
							.expandTowards(Vec3.atLowerCornerOf(facing.getNormal()).scale(SparkBottle.RANGE)),
					EntitySelector.NO_SPECTATORS
			);

			ShulkerBullet bullet = new ShulkerBullet(EntityType.SHULKER_BULLET, world);
			bullet.moveTo(posFacing.getX() + .5, posFacing.getY() + .5, posFacing.getZ() + .5, 0, 0);
			((ShulkerBulletAccessor) bullet).setFinalTarget(Iterables.getFirst(targets, null));
			((ShulkerBulletAccessor) bullet).callSelectNextMoveDirection(null);
			world.addFreshEntity(bullet);

			return this.consumeWithRemainder(pointer, stack, Items.GLASS_BOTTLE.getDefaultInstance());
		} else {
			return super.execute(pointer, stack);
		}
	}
}
