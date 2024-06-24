package one.devos.nautical.up_and_away.content;

import java.util.List;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import one.devos.nautical.up_and_away.content.balloon.item.BalloonCartItem;
import one.devos.nautical.up_and_away.content.balloon.item.BalloonItemRenderer;
import one.devos.nautical.up_and_away.content.balloon.item.filled.AirBalloonItem;
import one.devos.nautical.up_and_away.content.balloon.item.DeflatedBalloonItem;
import one.devos.nautical.up_and_away.content.balloon.item.filled.FloatyBalloonItem;
import one.devos.nautical.up_and_away.content.misc.SparkBottle;
import one.devos.nautical.up_and_away.framework.item.Balloons;
import one.devos.nautical.up_and_away.framework.item.UseOnEntityDispenseBehavior;
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
	public static final Balloons DEFLATED = Balloons.builder()
			.template("%s_deflated_balloon")
			.factory(DeflatedBalloonItem::new)
			.exclude(BalloonShape.DOG)
			.build();

	public static final Balloons AIR = Balloons.builder()
			.template("%s_air_balloon")
			.factory(AirBalloonItem::new)
			.factory(BalloonShape.LONG, AirBalloonItem.Long::new)
			.dispenseBehavior(UseOnEntityDispenseBehavior.INSTANCE)
			.renderer(() -> () -> BalloonItemRenderer.INSTANCE)
			.build();

	public static final Balloons FLOATY = Balloons.builder()
			.template("%s_floaty_balloon")
			.factory(FloatyBalloonItem::new)
			.factory(BalloonShape.LONG, FloatyBalloonItem.Long::new)
			.dispenseBehavior(UseOnEntityDispenseBehavior.INSTANCE)
			.renderer(() -> () -> BalloonItemRenderer.INSTANCE)
			.build();

	public static final Item BALLOON_CART = register("balloon_cart", new BalloonCartItem(new Properties()));

	public static final Item STRETCHY_SHEET = register("stretchy_sheet", new Item(new Properties()));
	public static final Item SPARK_BOTTLE = register("spark_in_a_bottle", new SparkBottle(new Properties().stacksTo(1).craftRemainder(Items.GLASS_BOTTLE)));

	public static final CreativeModeTab TAB = Registry.register(
			BuiltInRegistries.CREATIVE_MODE_TAB,
			UpAndAway.id("tab"),
			FabricItemGroup.builder()
					.title(Component.translatable("itemGroup.up_and_away"))
					.icon(() -> DyedItemColor.applyDyes(
							new ItemStack(FLOATY.get(BalloonShape.ROUND)), List.of((DyeItem) Items.RED_DYE)
					))
					.displayItems((params, output) -> {
						output.accept(STRETCHY_SHEET);
						output.accept(SPARK_BOTTLE);
						DEFLATED.forEach(output::accept);
						AIR.forEach(output::accept);
						FLOATY.forEach(output::accept);
						output.accept(BALLOON_CART);
					})
					.build()
	);

	private static Item register(String name, Item item) {
		return Registry.register(BuiltInRegistries.ITEM, UpAndAway.id(name), item);
	}

	public static void init() {
	}
}
