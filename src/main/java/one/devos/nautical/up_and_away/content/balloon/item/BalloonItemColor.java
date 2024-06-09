package one.devos.nautical.up_and_away.content.balloon.item;

import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DyedItemColor;

public class BalloonItemColor implements ItemColor {
	public static final int BALLOON = 0;
	public static final int WATER = 1;

	public static final int WATER_COLOR = 4159204; // plains color, OverworldBiomes

	@Override
	public int getColor(ItemStack stack, int tintIndex) {
		return switch (tintIndex) {
			case BALLOON -> DyedItemColor.getOrDefault(stack, AbstractBalloon.DEFAULT_COLOR);
			case WATER -> WATER_COLOR;
			default -> -1;
		};
	}
}
