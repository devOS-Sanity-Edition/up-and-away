package one.devos.nautical.up_and_away.framework.util;

import java.util.Objects;

import com.mojang.serialization.Codec;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.util.RandomSource;

public class Utils {
	public static <T> T simpleDecode(Codec<T> codec, CompoundTag nbt, String key) {
		Tag tag = Objects.requireNonNull(nbt.get(key), key);
		return codec.decode(NbtOps.INSTANCE, tag).getOrThrow().getFirst();
	}

	public static double nextDouble(RandomSource random, double min, double maxExclusive) {
		int minI = (int) min * 1000;
		int maxI = (int) maxExclusive * 1000;
		return random.nextInt(minI, maxI) / 1000d;
	}
}
