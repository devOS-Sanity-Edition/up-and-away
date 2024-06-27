package one.devos.nautical.up_and_away.content.balloon.entity.renderer.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;

import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import one.devos.nautical.up_and_away.content.balloon.entity.AbstractBalloon;

public abstract class AbstractBalloonModel extends EntityModel<AbstractBalloon> {
	@Override
	public final void setupAnim(AbstractBalloon entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
	}

	@Override
	public final void renderToBuffer(PoseStack matrices, VertexConsumer vertexConsumer, int i, int j, int k) {
		this.root().render(matrices, vertexConsumer, i, j, k);
	}

	public abstract ModelPart root();
}
