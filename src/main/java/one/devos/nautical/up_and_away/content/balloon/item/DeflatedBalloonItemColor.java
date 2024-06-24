package one.devos.nautical.up_and_away.content.balloon.item;

import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class DeflatedBalloonItemColor implements ItemColor {
	public static final DeflatedBalloonItemColor INSTANCE = new DeflatedBalloonItemColor();
	public static final int DEFAULT_COLOR = 0xFFFFFFFF;

	@Override
	public int getColor(ItemStack stack, int tintIndex) {
		if (tintIndex == 0)
			return DyedItemColor.getOrDefault(stack, DEFAULT_COLOR);
		return -1;
	}
}
