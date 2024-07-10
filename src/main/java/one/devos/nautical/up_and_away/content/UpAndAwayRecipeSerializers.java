package one.devos.nautical.up_and_away.content;

import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.content.balloon.item.FloatyBalloonInflatingRecipe;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.SimpleCraftingRecipeSerializer;

public class UpAndAwayRecipeSerializers {
	public static final SimpleCraftingRecipeSerializer<FloatyBalloonInflatingRecipe> FLOATY_BALLOON_INFLATING = Registry.register(
			BuiltInRegistries.RECIPE_SERIALIZER,
			UpAndAway.id("floaty_balloon_inflating"),
			new SimpleCraftingRecipeSerializer<>(FloatyBalloonInflatingRecipe::new)
	);

	public static void init() {
	}
}
