package one.devos.nautical.up_and_away.framework.item;

import org.jetbrains.annotations.Nullable;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;

public interface UsableOnEntityItem {
	InteractionResult useOnEntity(ItemStack stack, Entity entity, @Nullable Player user);

	static InteractionResult onUseEntity(Player player, Level world, InteractionHand hand, Entity entity, @Nullable EntityHitResult hitResult) {
		ItemStack stack = player.getItemInHand(hand);
		if (stack.getItem() instanceof UsableOnEntityItem item) {
			return item.useOnEntity(stack, entity, player);
		}
		return InteractionResult.PASS;
	}
}
