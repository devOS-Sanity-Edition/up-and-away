package one.devos.nautical.up_and_away.framework.util;

import java.util.Objects;

import com.mojang.serialization.Codec;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

import net.minecraft.world.phys.AABB;

import net.minecraft.world.phys.Vec3;

import org.joml.Matrix4d;
import org.joml.Vector3d;

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

	private static final Vector3d TEMP_CENTER = new Vector3d();
	private static final Matrix4d TEMP_MAT = new Matrix4d();

	public static AABB aabbRotateY(float yRot, AABB aabb) {
		TEMP_MAT.identity().rotateY(yRot * Mth.DEG_TO_RAD);

		Vec3 center = aabb.getCenter();
		TEMP_MAT.transformPosition(center.x, center.y, center.z, TEMP_CENTER);

		double extentX = aabb.getXsize() / 2;
		double extentY = aabb.getYsize() / 2;
		double extentZ = aabb.getZsize() / 2;
		double transformedExtentX = Math.abs(TEMP_MAT.m00()) * extentX + (Math.abs(TEMP_MAT.m10()) * extentY + (Math.abs(TEMP_MAT.m20()) * extentZ));
		double transformedExtentY = Math.abs(TEMP_MAT.m01()) * extentX + (Math.abs(TEMP_MAT.m11()) * extentY + (Math.abs(TEMP_MAT.m21()) * extentZ));
		double transformedExtentZ = Math.abs(TEMP_MAT.m02()) * extentX + (Math.abs(TEMP_MAT.m12()) * extentY + (Math.abs(TEMP_MAT.m22()) * extentZ));

		return new AABB(
				TEMP_CENTER.x - transformedExtentX,
				TEMP_CENTER.y - transformedExtentY,
				TEMP_CENTER.z - transformedExtentZ,
				TEMP_CENTER.x + transformedExtentX,
				TEMP_CENTER.y + transformedExtentY,
				TEMP_CENTER.z + transformedExtentZ
		);
	}
}
