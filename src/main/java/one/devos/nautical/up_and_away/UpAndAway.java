package one.devos.nautical.up_and_away;

import net.fabricmc.api.ModInitializer;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import one.devos.nautical.up_and_away.entity.Balloon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpAndAway implements ModInitializer {
	public static final String ID = "up_and_away";
	public static final Logger LOGGER = LoggerFactory.getLogger(ID);

	@Override
	public void onInitialize() {
	}

	public static ResourceLocation id(String path) {
		return ResourceLocation.fromNamespaceAndPath(ID, path);
	}
}
