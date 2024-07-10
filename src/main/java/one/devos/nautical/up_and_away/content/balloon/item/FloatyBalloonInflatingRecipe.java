package one.devos.nautical.up_and_away.content.balloon.item;

import java.util.List;

import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.content.UpAndAwayItems;
import one.devos.nautical.up_and_away.content.UpAndAwayRecipeSerializers;
import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingInput;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

public class FloatyBalloonInflatingRecipe extends CustomRecipe {
	public static final TagKey<Item> FLOATY_BALLOON_MATERIALS = TagKey.create(Registries.ITEM, UpAndAway.id("floaty_balloon_materials"));

	public FloatyBalloonInflatingRecipe(CraftingBookCategory category) {
		super(category);
	}

	@Override
	public boolean matches(CraftingInput input, Level world) {
		return this.findInput(input) != null;
	}

	@Override
	public ItemStack assemble(CraftingInput input, HolderLookup.Provider provider) {
		Input balloonInput = this.findInput(input);
		if (balloonInput == null)
			return ItemStack.EMPTY;

		BalloonItem floatyItem = UpAndAwayItems.FLOATY.get(balloonInput.shape);
		ItemStack result = new ItemStack(floatyItem);
		result.applyComponents(balloonInput.balloon.getComponents());
		return result;
	}

	@Override
	public boolean canCraftInDimensions(int width, int height) {
		return width * height >= 2;
	}

	@Override
	public RecipeSerializer<?> getSerializer() {
		return UpAndAwayRecipeSerializers.FLOATY_BALLOON_INFLATING;
	}

	private Input findInput(CraftingInput input) {
		List<ItemStack> items = input.items().stream().filter(s -> !s.isEmpty()).toList();
		if (items.size() != 2)
			return null;

		ItemStack first = items.get(0);
		ItemStack second = items.get(1);

		if (first.getItem() instanceof DeflatedBalloonItem balloon && second.is(FLOATY_BALLOON_MATERIALS)) {
			return new Input(first, balloon.shape);
		} else if (second.getItem() instanceof DeflatedBalloonItem balloon && first.is(FLOATY_BALLOON_MATERIALS)) {
			return new Input(second, balloon.shape);
		} else {
			return null;
		}
	}

	public record Input(ItemStack balloon, BalloonShape shape) {
	}
}
