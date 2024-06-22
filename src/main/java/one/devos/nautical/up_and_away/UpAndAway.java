package one.devos.nautical.up_and_away;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.ResourceLocation;

import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import one.devos.nautical.up_and_away.content.UpAndAwayEntities;

import one.devos.nautical.up_and_away.content.UpAndAwayItems;

import one.devos.nautical.up_and_away.framework.item.UsableOnEntityItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpAndAway implements ModInitializer {
	public static final String ID = "up_and_away";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize() {
		UpAndAwayItems.init();
		UpAndAwayEntities.init();

		UseEntityCallback.EVENT.register(UsableOnEntityItem::onUseEntity);
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, path);
	}
}
