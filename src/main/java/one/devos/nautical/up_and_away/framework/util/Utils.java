package one.devos.nautical.up_and_away.framework.util;

import java.util.Objects;

import com.mojang.serialization.Codec;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;

import net.minecraft.world.phys.AABB;

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

	private static final ThreadLocal<Vector3d> TEMP_CENTER = ThreadLocal.withInitial(Vector3d::new);
	private static final ThreadLocal<Matrix4d> TEMP_MAT = ThreadLocal.withInitial(Matrix4d::new);

	public static AABB aabbRotateY(float yRot, AABB aabb) {
		Matrix4d mat = TEMP_MAT.get()
				.identity().rotateY(yRot * Mth.DEG_TO_RAD);
		Vector3d center = mat.transformPosition(
				Mth.lerp(0.5, aabb.minX, aabb.maxX),
				Mth.lerp(0.5, aabb.minY, aabb.maxY),
				Mth.lerp(0.5, aabb.minZ, aabb.maxZ),
				TEMP_CENTER.get()
		);

		double extentX = aabb.getXsize() / 2;
		double extentY = aabb.getYsize() / 2;
		double extentZ = aabb.getZsize() / 2;
		double transformedExtentX = Math.abs(mat.m00()) * extentX + (Math.abs(mat.m10()) * extentY + (Math.abs(mat.m20()) * extentZ));
		double transformedExtentY = Math.abs(mat.m01()) * extentX + (Math.abs(mat.m11()) * extentY + (Math.abs(mat.m21()) * extentZ));
		double transformedExtentZ = Math.abs(mat.m02()) * extentX + (Math.abs(mat.m12()) * extentY + (Math.abs(mat.m22()) * extentZ));

		return new AABB(
				center.x - transformedExtentX,
				center.y - transformedExtentY,
				center.z - transformedExtentZ,
				center.x + transformedExtentX,
				center.y + transformedExtentY,
				center.z + transformedExtentZ
		);
	}
}
