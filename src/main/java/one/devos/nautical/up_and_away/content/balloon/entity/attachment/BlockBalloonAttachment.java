package one.devos.nautical.up_and_away.content.balloon.entity.attachment;

import java.util.Locale;

import com.mojang.serialization.Codec;

import one.devos.nautical.up_and_away.framework.util.Utils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public non-sealed class BlockBalloonAttachment extends BalloonAttachment {
	public static final String POS_KEY = "pos";

	public static final VoxelShape CENTER_SHAPE = Block.box(7, 7, 7, 9, 9, 9);

	private final Type type;
	private final Level level;
	private final BlockPos blockPos;
	private final Vec3 pos;

	private BlockState state;

	public BlockBalloonAttachment(Level level, BlockPos blockPos, double stringLength) {
		this(Type.BLOCK, level, blockPos, Vec3.atCenterOf(blockPos), stringLength);
	}

	protected BlockBalloonAttachment(Type type, Level level, BlockPos blockPos, Vec3 pos, double stringLength) {
		super(stringLength);
		this.type = type;
		this.level = level;
		this.blockPos = blockPos;
		this.pos = pos;
	}

	@Override
	public boolean validate() {
		BlockState state = this.level.getBlockState(this.blockPos);
		if (state == this.state)
			return true; // already checked

		this.state = state;
		// based on SupportType
		VoxelShape shape = state.getCollisionShape(this.level, this.blockPos);
		return !Shapes.joinIsNotEmpty(shape, this.getRequiredSupportShape(), BooleanOp.ONLY_SECOND);
	}

	protected VoxelShape getRequiredSupportShape() {
		return CENTER_SHAPE;
	}

	@Override
	public Vec3 getPos(float partialTicks) {
		return this.pos;
	}

	@Override
	protected String typeName() {
		return this.type.name;
	}

	@Override
	public void toNbt(CompoundTag nbt) {
		nbt.put(POS_KEY, NbtUtils.writeBlockPos(this.blockPos));
	}

	public static BlockBalloonAttachment fromNbt(CompoundTag nbt, Level level) {
		BlockBalloonAttachment.Type type = Utils.simpleDecode(BlockBalloonAttachment.Type.CODEC, nbt, TYPE_KEY);
		double stringLength = Utils.simpleDecode(STRING_LENGTH_CODEC, nbt, STRING_LENGTH_KEY);
		BlockPos pos = Utils.simpleDecode(BlockPos.CODEC, nbt, POS_KEY);
		return type.factory.create(nbt, stringLength, level, pos);
	}

	private static BlockBalloonAttachment fromNbt(CompoundTag nbt, double stringLength, Level level, BlockPos blockPos) {
		return new BlockBalloonAttachment(level, blockPos, stringLength);
	}

	public interface Factory {
		BlockBalloonAttachment create(CompoundTag nbt, double stringLength, Level level, BlockPos blockPos);
	}

	public enum Type implements StringRepresentable {
		BLOCK(BlockBalloonAttachment::fromNbt),
		BLOCK_FACE(BlockFaceBalloonAttachment::fromNbt);

		public static final Codec<Type> CODEC = StringRepresentable.fromEnum(Type::values);

		public final String name = this.name().toLowerCase(Locale.ROOT);
		public final Factory factory;

		Type(Factory factory) {
			this.factory = factory;
		}

		@Override
		public String getSerializedName() {
			return this.name;
		}
	}
}
