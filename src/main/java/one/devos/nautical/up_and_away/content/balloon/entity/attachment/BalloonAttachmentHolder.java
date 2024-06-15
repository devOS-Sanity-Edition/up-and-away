package one.devos.nautical.up_and_away.content.balloon.entity.attachment;

import org.jetbrains.annotations.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;

public class BalloonAttachmentHolder {
	private CompoundTag nbt;

	private BalloonAttachment attachment;

	@Nullable
	public BalloonAttachment get(Level level) {
		if (this.attachment == null && this.nbt != null) {
			this.attachment = BalloonAttachment.fromNbt(this.nbt);
			this.nbt = null;
		}

		return this.attachment;
	}
}
