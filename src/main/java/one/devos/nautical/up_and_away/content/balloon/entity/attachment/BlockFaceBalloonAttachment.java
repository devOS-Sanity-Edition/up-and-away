package one.devos.nautical.up_and_away.content.balloon.entity.attachment;

import java.util.Objects;

import one.devos.nautical.up_and_away.framework.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockFaceBalloonAttachment extends BalloonAttachment {
	public static final String POS_KEY = "pos";
	public static final String FACE_KEY = "face";

	private final Level level;
	private final BlockPos blockPos;
	private final Direction face;
	private final Vec3 pos;

	private BlockState state;

	public BlockFaceBalloonAttachment(Level level, BlockPos blockPos, Direction face, double stringLength) {
		super(Type.BLOCK_FACE, stringLength);
		this.level = level;
		this.blockPos = blockPos;
		this.face = face;
		this.pos = Vec3.atCenterOf(blockPos).relative(face, 0.5);
	}

	@Override
	public boolean validate() {
		BlockState state = this.level.getBlockState(this.blockPos);
		if (state == this.state)
			return true; // already checked

		this.state = state;
		VoxelShape shape = state.getCollisionShape(this.level, this.blockPos);
		return Block.isFaceFull(shape, this.face);
	}

	@Override
	public Vec3 getPos(float partialTicks) {
		return this.pos;
	}

	@Override
	public void toNbt(CompoundTag nbt) {
		nbt.put("pos", NbtUtils.writeBlockPos(this.blockPos));
		nbt.putString("face", this.face.getSerializedName());
	}

	public static BalloonAttachment fromNbt(Level level, CompoundTag nbt, double stringLength) {
		BlockPos pos = Utils.simpleDecode(BlockPos.CODEC, nbt, POS_KEY);
		Direction face = Utils.simpleDecode(Direction.CODEC, nbt, FACE_KEY);
		return new BlockFaceBalloonAttachment(level, pos, face, stringLength);
	}
}
