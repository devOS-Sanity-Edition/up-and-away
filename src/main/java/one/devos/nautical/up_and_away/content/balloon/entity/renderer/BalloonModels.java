package one.devos.nautical.up_and_away.content.balloon.entity.renderer;

import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.model.DogBalloonModel;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.model.CubeBalloonModel;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.model.HeartBalloonModel;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.model.LongBalloonModel;
import one.devos.nautical.up_and_away.content.balloon.entity.renderer.model.RoundBalloonModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;

public class BalloonModels {
	private final CubeBalloonModel cubeModel;
	private final DogBalloonModel dogModel;
	private final HeartBalloonModel heartModel;
	private final LongBalloonModel longModel;
	private final RoundBalloonModel roundModel;

	public BalloonModels(EntityModelBakery bakery) {
		this.cubeModel = new CubeBalloonModel(bakery.bake(CubeBalloonModel.LAYER_LOCATION));
		this.dogModel = new DogBalloonModel(bakery.bake(DogBalloonModel.LAYER_LOCATION));
		this.heartModel = new HeartBalloonModel(bakery.bake(HeartBalloonModel.LAYER_LOCATION));
		this.longModel = new LongBalloonModel(bakery.bake(LongBalloonModel.LAYER_LOCATION));
		this.roundModel = new RoundBalloonModel(bakery.bake(RoundBalloonModel.LAYER_LOCATION));
	}

	public EntityModel<AbstractBalloon> choose(BalloonShape shape) {
		return switch (shape) {
			case CUBE -> this.cubeModel;
			case DOG -> this.dogModel;
			case HEART -> this.heartModel;
			case LONG -> this.longModel;
			case ROUND -> this.roundModel;
		};
	}

	public interface EntityModelBakery {
		ModelPart bake(ModelLayerLocation layer);
	}
}
