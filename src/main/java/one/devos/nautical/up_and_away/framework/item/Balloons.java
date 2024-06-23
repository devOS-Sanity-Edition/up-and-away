package one.devos.nautical.up_and_away.framework.item;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry.DynamicItemRenderer;
import net.fabricmc.loader.api.FabricLoader;
import one.devos.nautical.up_and_away.UpAndAway;
import one.devos.nautical.up_and_away.content.balloon.BalloonShape;
import one.devos.nautical.up_and_away.content.balloon.item.BalloonItem;

import org.apache.commons.lang3.Validate;

import net.minecraft.core.Registry;
import net.minecraft.core.dispenser.DispenseItemBehavior;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.DispenserBlock;

public record Balloons(Map<BalloonShape, BalloonItem> items) {

	public BalloonItem get(BalloonShape shape) {
		return this.items.get(shape);
	}

	public void forEach(Consumer<BalloonItem> consumer) {
		this.items.values().forEach(consumer);
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private String template;
		private final Set<BalloonShape> excluded = EnumSet.noneOf(BalloonShape.class);
		private final Map<BalloonShape, BalloonItem.Factory> factories = new EnumMap<>(BalloonShape.class);
		private DispenseItemBehavior dispenseBehavior;
		private Supplier<Supplier<Object>> renderer;

		public Builder template(String template) {
			this.template = template;
			return this;
		}

		public Builder factory(BalloonItem.Factory factory) {
			for (BalloonShape shape : BalloonShape.values()) {
				this.factory(shape, factory);
			}
			return this;
		}

		public Builder factory(BalloonShape shape, BalloonItem.Factory factory) {
			this.factories.put(shape, factory);
			return this;
		}

		public Builder exclude(BalloonShape shape) {
			this.excluded.add(shape);
			return this;
		}

		public Builder dispenseBehavior(DispenseItemBehavior behavior) {
			this.dispenseBehavior = behavior;
			return this;
		}

		// specifying DynamicItemRenderer will cause a crash
		public Builder renderer(Supplier<Supplier<Object>> renderer) {
			this.renderer = renderer;
			return this;
		}

		public Balloons build() {
			Objects.requireNonNull(this.template, "template");
			Validate.isTrue(!this.factories.isEmpty(), "factories");

			Map<BalloonShape, BalloonItem> items = new EnumMap<>(BalloonShape.class);
			for (BalloonShape shape : BalloonShape.values()) {
				BalloonItem.Factory factory = this.factories.get(shape);
				if (factory == null || this.excluded.contains(shape))
					continue;
				BalloonItem item = factory.create(shape, new Properties());
				items.put(shape, item);

				String name = this.template.formatted(shape.name);
				Registry.register(BuiltInRegistries.ITEM, UpAndAway.id(name), item);

				if (this.dispenseBehavior != null) {
					DispenserBlock.registerBehavior(item, this.dispenseBehavior);
				}

				if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) {
					this.buildClient(item);
				}
			}

			return new Balloons(items);
		}

		@Environment(EnvType.CLIENT)
		private void buildClient(BalloonItem item) {
			if (this.renderer != null) {
				BuiltinItemRendererRegistry.INSTANCE.register(item, (DynamicItemRenderer) this.renderer.get().get());
			}
		}
	}
}
