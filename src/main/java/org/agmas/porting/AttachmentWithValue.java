package org.agmas.porting;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;

public class AttachmentWithValue<T> {
    PPAttachment<T> attachment;
    T value;

    public AttachmentWithValue(PPAttachment<T> attachment, T value) {
        this.attachment = attachment;
        this.value = value;
    }

    public PPAttachment<T> getAttachment() {
        return attachment;
    }

    public DataResult<Tag> encodeTag() {
        return attachment.codec.encodeStart(NbtOps.INSTANCE, value);
    }

    public T getValue() {
        return value;
    }
}
