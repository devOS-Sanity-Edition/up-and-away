package one.devos.nautical.up_and_away.content;

import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Stream;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import one.devos.nautical.up_and_away.content.balloon.item.BalloonCartItem;
import one.devos.nautical.up_and_away.content.balloon.item.filled.AirBalloonItem;
import one.devos.nautical.up_and_away.content.balloon.item.BalloonItem;
import one.devos.nautical.up_and_away.content.balloon.item.DeflatedBalloonItem;
import one.devos.nautical.up_and_away.content.balloon.item.filled.FloatyBalloonItem;
import one.devos.nautical.up_and_away.content.balloon.item.filled.WaterBalloonItem;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.DyedItemColor;

public class UpAndAwayItems {
	public static final Map<BalloonShape, Item>
			DEFLATED_BALLOONS = balloons("%s_deflated_balloon", UpAndAwayItems::noAnimals, DeflatedBalloonItem::new),
			AIR_BALLOONS = balloons("%s_air_balloon", UpAndAwayItems::all, AirBalloonItem::new),
			FLOATY_BALLOONS = balloons("%s_floaty_balloon", UpAndAwayItems::all, FloatyBalloonItem::new),
			WATER_BALLOONS = balloons("%s_water_balloon", UpAndAwayItems::noAnimals, WaterBalloonItem::new);

	public static final Item BALLOON_CART = register("balloon_cart", new BalloonCartItem(new Properties()));

	public static final CreativeModeTab TAB = Registry.register(
			BuiltInRegistries.CREATIVE_MODE_TAB,
			UpAndAway.id("tab"),
			FabricItemGroup.builder()
					.title(Component.translatable("itemGroup.up_and_away"))
					.icon(() -> DyedItemColor.applyDyes(
							new ItemStack(FLOATY_BALLOONS.get(BalloonShape.ROUND)), List.of((DyeItem) Items.RED_DYE)
					))
					.displayItems((params, output) -> {
						Stream.of(
								DEFLATED_BALLOONS, AIR_BALLOONS, FLOATY_BALLOONS, WATER_BALLOONS
						).map(Map::values).flatMap(Collection::stream).forEach(output::accept);
						output.accept(BALLOON_CART);
					})
					.build()
	);

	private static Map<BalloonShape, Item> balloons(String template, Predicate<BalloonShape> test, BalloonItem.Factory factory) {
		Map<BalloonShape, Item> map = new EnumMap<>(BalloonShape.class);
		for (BalloonShape shape : BalloonShape.values()) {
			if (test.test(shape)) {
				String name = template.formatted(shape);
				map.put(shape, register(name, factory.create(shape, new Properties())));
			}
		}
		return map;
	}

	private static boolean all(BalloonShape shape) {
		return true;
	}

	private static boolean noAnimals(BalloonShape shape) {
		return shape != BalloonShape.DOG;
	}

	private static Item register(String name, Item item) {
		return Registry.register(BuiltInRegistries.ITEM, UpAndAway.id(name), item);
	}

	public static void init() {
	}
}
