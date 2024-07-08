package one.devos.nautical.up_and_away.mixin.client;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;

import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CapeLayer;

import net.minecraft.client.renderer.entity.layers.RenderLayer;

import one.devos.nautical.up_and_away.framework.ext.HumanoidModelExt;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(CapeLayer.class)
public abstract class CapeLayerMixin extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
	private CapeLayerMixin(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> context) {
		super(context);
	}

	@ModifyExpressionValue(
			method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V",
			at = @At(
					value = "INVOKE",
					target = "Lnet/minecraft/client/player/AbstractClientPlayer;isCrouching()Z"
			)
	)
	private boolean balloonCartExtraCrouch(boolean original) {
		return original || ((HumanoidModelExt) this.getParentModel()).up_and_away$holdingBalloonCart();
	}
}
