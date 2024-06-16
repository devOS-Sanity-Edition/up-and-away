package one.devos.nautical.up_and_away.content.balloon.entity.attachment;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockFaceBalloonAttachment implements BalloonAttachment {
	private final Level level;
	private final BlockPos blockPos;
	private final Direction face;
	private final double stringLength;
	private final Vec3 pos;

	private BlockState state;

	public BlockFaceBalloonAttachment(Level level, BlockPos blockPos, Direction face, double stringLength) {
		this.level = level;
		this.blockPos = blockPos;
		this.face = face;
		this.stringLength = stringLength;
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
	public Vec3 getPos() {
		return this.pos;
	}

	@Override
	public double getStringLength() {
		return this.stringLength;
	}

	public static BalloonAttachment fromNbt(CompoundTag nbt, Level level) {
		return null;
	}
}
