package one.devos.nautical.up_and_away.framework.item;

import java.util.List;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.phys.AABB;

public class UseOnEntityDispenseBehavior extends DefaultDispenseItemBehavior {
	public static final DispenseItemBehavior INSTANCE = new UseOnEntityDispenseBehavior();

	@Override
	protected ItemStack execute(BlockSource source, ItemStack stack) {
		if (!(stack.getItem() instanceof UsableOnEntityItem item))
			return super.execute(source, stack);

		BlockPos inFront = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
		List<Entity> entities = source.level().getEntities(null, new AABB(inFront));
		if (!entities.isEmpty()) {
			Entity entity = Util.getRandom(entities, source.level().random);
			item.useOnEntity(stack, entity, null);
			return stack;
		}

		return super.execute(source, stack);
	}
}
