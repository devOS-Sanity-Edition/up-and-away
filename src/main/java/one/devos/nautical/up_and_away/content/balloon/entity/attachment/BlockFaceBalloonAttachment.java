package one.devos.nautical.up_and_away.content.balloon.entity.attachment;

import one.devos.nautical.up_and_away.framework.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class BlockFaceBalloonAttachment extends BlockBalloonAttachment {
	public static final String FACE_KEY = "face";

	private final Direction face;

	public BlockFaceBalloonAttachment(Level level, BlockPos blockPos, Direction face, double stringLength) {
		super(Type.BLOCK_FACE, level, blockPos, Vec3.atCenterOf(blockPos).relative(face, 0.5), stringLength);
		this.face = face;
	}

	@Override
	public void toNbt(CompoundTag nbt) {
		super.toNbt(nbt);
		nbt.putString(FACE_KEY, this.face.getSerializedName());
	}

	public static BlockBalloonAttachment fromNbt(CompoundTag nbt, double stringLength, Level level, BlockPos blockPos) {
		Direction face = Utils.simpleDecode(Direction.CODEC, nbt, FACE_KEY);
		return new BlockFaceBalloonAttachment(level, blockPos, face, stringLength);
	}
}
