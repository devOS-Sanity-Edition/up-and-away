package one.devos.nautical.up_and_away;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import one.devos.nautical.up_and_away.content.UpAndAwayEntities;
import one.devos.nautical.up_and_away.content.UpAndAwayItems;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.BalloonCartModel;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.BalloonCartRenderer;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.BalloonRenderer;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.model.CubeBalloonModel;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.model.DogBalloonModel;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.model.HeartBalloonModel;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.model.LongBalloonModel;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.model.RoundBalloonModel;
import one.devos.nautical.up_and_away.content.balloon.item.BalloonItemColor;
import one.devos.nautical.up_and_away.content.balloon.item.BalloonItemRenderer;
import net.minecraft.world.level.ItemLike;

public class UpAndAwayClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		EntityModelLayerRegistry.registerModelLayer(DogBalloonModel.LAYER_LOCATION, DogBalloonModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(CubeBalloonModel.LAYER_LOCATION, CubeBalloonModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(HeartBalloonModel.LAYER_LOCATION, HeartBalloonModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(LongBalloonModel.LAYER_LOCATION, LongBalloonModel::createBodyLayer);
		EntityModelLayerRegistry.registerModelLayer(RoundBalloonModel.LAYER_LOCATION, RoundBalloonModel::createBodyLayer);

		EntityModelLayerRegistry.registerModelLayer(BalloonCartModel.LAYER_LOCATION, BalloonCartModel::createBodyLayer);

		EntityRendererRegistry.register(UpAndAwayEntities.AIR_BALLOON, BalloonRenderer::new);
		EntityRendererRegistry.register(UpAndAwayEntities.FLOATY_BALLOON, BalloonRenderer::new);
		EntityRendererRegistry.register(UpAndAwayEntities.BALLOON_CART, BalloonCartRenderer::new);

		ColorProviderRegistry.ITEM.register(new BalloonItemColor(), UpAndAwayItems.ALL_BALLOONS.toArray(ItemLike[]::new));
		UpAndAwayItems.ALL_BALLOONS.forEach(item -> BuiltinItemRendererRegistry.INSTANCE.register(item, BalloonItemRenderer.INSTANCE));
	}
}
