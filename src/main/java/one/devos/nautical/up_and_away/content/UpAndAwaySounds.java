package one.devos.nautical.up_and_away.content;

import one.devos.nautical.up_and_away.UpAndAway;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class UpAndAwaySounds {
	public static final SoundEvent BALLOON_POP = register("balloon_pop");
	public static final SoundEvent BALLOON_INFLATE = register("balloon_inflate");
	public static final SoundEvent BALLOON_DEFLATE = register("balloon_deflate");
	public static final SoundEvent BALLOON_ANIMAL_TIE = register("balloon_animal_tie");

	private static SoundEvent register(String name) {
		ResourceLocation id = UpAndAway.id(name);
		return Registry.register(BuiltInRegistries.SOUND_EVENT, id, SoundEvent.createVariableRangeEvent(id));
	}

	public static void init() {
	}
}
