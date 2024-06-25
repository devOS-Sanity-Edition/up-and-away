package one.devos.nautical.up_and_away.content.balloon.entity.attachment;

import java.util.EnumMap;
import java.util.Map;

import one.devos.nautical.up_and_away.framework.util.Utils;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockFaceBalloonAttachment extends BlockBalloonAttachment {
	public static final String FACE_KEY = "face";

	public static final Map<Direction, VoxelShape> SUPPORT_SHAPES = Util.make(new EnumMap<>(Direction.class), map -> {
		map.put(Direction.UP, Block.box(7, 15, 7, 9, 16, 9));
		map.put(Direction.DOWN, Block.box(7, 0, 7, 9, 1, 9));
		map.put(Direction.WEST, Block.box(0, 7, 7, 1, 9, 9));
		map.put(Direction.EAST, Block.box(15, 7, 7, 16, 9, 9));
		map.put(Direction.NORTH, Block.box(7, 7, 0, 9, 9, 1));
		map.put(Direction.SOUTH, Block.box(7, 7, 15, 9, 9, 16));
	});

	private final Direction face;
	private final VoxelShape supportShape;

	public BlockFaceBalloonAttachment(Level level, BlockPos blockPos, Direction face, double stringLength) {
		super(Type.BLOCK_FACE, level, blockPos, Vec3.atCenterOf(blockPos).relative(face, 0.5), stringLength);
		this.face = face;
		this.supportShape = SUPPORT_SHAPES.get(face);
	}

	@Override
	protected VoxelShape getRequiredSupportShape() {
		return this.supportShape;
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
