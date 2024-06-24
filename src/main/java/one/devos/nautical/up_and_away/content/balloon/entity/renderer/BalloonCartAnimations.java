package one.devos.nautical.up_and_away.content.balloon.entity.renderer;// Save this class in your mod and generate all required imports

import net.minecraft.client.animation.AnimationChannel;
import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.Keyframe;
import net.minecraft.client.animation.KeyframeAnimations;

/**
 * Made with Blockbench 4.10.3
 * Exported for Minecraft version 1.19 or later with Mojang mappings
 * @author Carter
 */
public class BalloonCartAnimations {
	public static final AnimationDefinition HATCH_OPEN = AnimationDefinition.Builder.withLength(0.5F)
		.addAnimation("left_hatch", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 37.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 37.5F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();

	public static final AnimationDefinition HATCH_CLOSE = AnimationDefinition.Builder.withLength(0.5F)
		.addAnimation("left_hatch", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 37.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.3F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 37.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.36F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.42F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 1.61F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();

	public static final AnimationDefinition CHEST_OPEN = AnimationDefinition.Builder.withLength(1.0F)
		.addAnimation("chest", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.34F, KeyframeAnimations.posVec(-13.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.52F, KeyframeAnimations.posVec(-12.81F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.8F, KeyframeAnimations.posVec(-13.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("lid", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.56F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.72F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 42.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.84F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 55.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 55.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();

	public static final AnimationDefinition CHEST_CLOSE = AnimationDefinition.Builder.withLength(1.0F)
		.addAnimation("chest", new AnimationChannel(AnimationChannel.Targets.POSITION,
			new Keyframe(0.44F, KeyframeAnimations.posVec(-13.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.72F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.9F, KeyframeAnimations.posVec(-0.06F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(1.0F, KeyframeAnimations.posVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.addAnimation("lid", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 55.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 55.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.16F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 42.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.28F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.34F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 2.5F), AnimationChannel.Interpolations.CATMULLROM),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM)
		))
		.build();

	public static final AnimationDefinition TABLE_OPEN = AnimationDefinition.Builder.withLength(0.5F)
		.addAnimation("table", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.04F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -87.5F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -80.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("bottom", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.08F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 45.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.18F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 28.64F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.24F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 21.3657F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.addAnimation("top", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.02F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -176.59F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.1F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -161.59F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.14F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -147.774F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.18F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -114.55F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.24F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -70.46F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.3F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
		))
		.build();

	public static final AnimationDefinition TABLE_CLOSE = AnimationDefinition.Builder.withLength(0.5F)
			.addAnimation("table", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.24F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -80.0F), AnimationChannel.Interpolations.CATMULLROM),
					new Keyframe(0.5F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -87.5F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("bottom", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.08F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 21.3657F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.14F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 28.64F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.26F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 45.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.addAnimation("top", new AnimationChannel(AnimationChannel.Targets.ROTATION,
					new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.08F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -70.46F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.14F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -114.55F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.2F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -147.774F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.24F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -161.59F), AnimationChannel.Interpolations.LINEAR),
					new Keyframe(0.34F, KeyframeAnimations.degreeVec(0.0F, 0.0F, -176.59F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition VALVE_OPEN = AnimationDefinition.Builder.withLength(1.0F)
			.addAnimation("left_valve", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.18F, KeyframeAnimations.degreeVec(0.0F, 60.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.32F, KeyframeAnimations.degreeVec(0.0F, 62.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.38F, KeyframeAnimations.degreeVec(0.0F, 62.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.64F, KeyframeAnimations.degreeVec(0.0F, 170.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.78F, KeyframeAnimations.degreeVec(0.0F, 175.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.86F, KeyframeAnimations.degreeVec(0.0F, 175.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();

	public static final AnimationDefinition VALVE_CLOSE = AnimationDefinition.Builder.withLength(1.0F)
			.addAnimation("left_valve", new AnimationChannel(AnimationChannel.Targets.ROTATION,
			new Keyframe(0.0F, KeyframeAnimations.degreeVec(0.0F, 175.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.08F, KeyframeAnimations.degreeVec(0.0F, 175.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.22F, KeyframeAnimations.degreeVec(0.0F, 170.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.48F, KeyframeAnimations.degreeVec(0.0F, 62.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.54F, KeyframeAnimations.degreeVec(0.0F, 62.5F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.68F, KeyframeAnimations.degreeVec(0.0F, 60.0F, 0.0F), AnimationChannel.Interpolations.LINEAR),
			new Keyframe(0.86F, KeyframeAnimations.degreeVec(0.0F, 0.0F, 0.0F), AnimationChannel.Interpolations.LINEAR)
			))
			.build();
}
