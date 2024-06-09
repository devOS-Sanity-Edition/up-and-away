package one.devos.nautical.up_and_away;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import one.devos.nautical.up_and_away.content.UpAndAwayEntities;
import one.devos.nautical.up_and_away.content.UpAndAwayItems;
import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.BalloonRenderer;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.model.DogBalloonModel;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.model.CubeBalloonModel;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.model.HeartBalloonModel;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.model.LongBalloonModel;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.model.RoundBalloonModel;
import one.devos.nautical.up_and_away.content.balloon.item.BalloonItemColor;
import net.minecraft.world.item.Item;

public class UpAndAwayClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityModelLayerRegistry.registerModelLayer(DogBalloonModel.LAYER_LOCATION, DogBalloonModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(CubeBalloonModel.LAYER_LOCATION, CubeBalloonModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(HeartBalloonModel.LAYER_LOCATION, HeartBalloonModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(LongBalloonModel.LAYER_LOCATION, LongBalloonModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(RoundBalloonModel.LAYER_LOCATION, RoundBalloonModel::createBodyLayer);

		EntityRendererRegistry.register(UpAndAwayEntities.AIR_BALLOON, BalloonRenderer::new);
		EntityRendererRegistry.register(UpAndAwayEntities.FLOATY_BALLOON, BalloonRenderer::new);
		EntityRendererRegistry.register(UpAndAwayEntities.WATER_BALLOON, BalloonRenderer::new);

		Item[] allBalloons = Stream.of(
				UpAndAwayItems.DEFLATED_BALLOONS, UpAndAwayItems.AIR_BALLOONS,
				UpAndAwayItems.FLOATY_BALLOONS, UpAndAwayItems.WATER_BALLOONS
		).map(Map::values).flatMap(Collection::stream).toArray(Item[]::new);

		ColorProviderRegistry.ITEM.register(new BalloonItemColor(), allBalloons);
	}
}