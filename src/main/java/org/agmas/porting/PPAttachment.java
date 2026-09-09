package org.agmas.porting;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
//? if >1.19 {
/*import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
*///? }
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import org.agmas.accessors.AttachmentAccessor;
//? if >1.21.1
 //import net.minecraft.network.codec.StreamCodec;

import java.util.HashMap;
import java.util.function.Supplier;

public class PPAttachment<T> {
    //? if >1.19
    //public AttachmentType<T> PERKY_ATTACHMENT;

    public String name;
    public Supplier<T> initializer;
    public Codec<T> codec;

    public static HashMap<String, PPAttachment<?>> reallyShitWayToDoARegistryButIDontCareNoOnePlaysTheseVersions = new HashMap<>();


    //? if >1.21.1 {
    /*public PPAttachment(String name, Supplier<T> initializer, StreamCodec<ByteBuf,T> streamCodec, Codec<T> codec) {
        PERKY_ATTACHMENT =
        //? if >1.21.1
        //AttachmentRegistry.create(
        //? if <=1.21.1
        AttachmentRegistry.createPersistent(
                PPIdentifier.of(name).id,
                //? if >1.21.1 {
                /^integerBuilder -> integerBuilder.initializer(initializer)
                        .copyOnDeath()
                        .syncWith(streamCodec, AttachmentSyncPredicate.all())
                        .persistent(
                                ^///? }
                                codec
                                //? if >1.21.1
                        //)
        );
    }
    *///? }
    public PPAttachment(String name, Supplier<T> initializer, Codec<T> codec) {
        //? if >1.19 {
        /*PERKY_ATTACHMENT =
                //? if >1.21.1
                //AttachmentRegistry.create(
                        //? if <=1.21.1
                        AttachmentRegistry.createPersistent(
                        PPIdentifier.of(name).id,
                        //? if >1.21.1 {
                        /^integerBuilder -> integerBuilder.initializer(initializer)
                                .copyOnDeath()
                                .persistent(
                                        ^///? }
                                        codec
                                        //? if >1.21.1
                                //)
                );
        *///? }
        this.name = name;
        this.codec = codec;
        this.initializer = initializer;
        reallyShitWayToDoARegistryButIDontCareNoOnePlaysTheseVersions.put(name, this);
    }

    public PPAttachment(String name, Supplier<T> initializer) {
        //? if >1.19 {
        /*PERKY_ATTACHMENT =
                //? if >1.21.1
                //AttachmentRegistry.create(
                //? if <=1.21.1
                AttachmentRegistry.create(
                        PPIdentifier.of(name).id
                        //? if >1.21.1 {
                                /^, integerBuilder -> integerBuilder.initializer(initializer)
                                .copyOnDeath()
                        ^///? }

                );
        *///? }
        this.name = name;
        this.initializer = initializer;
        reallyShitWayToDoARegistryButIDontCareNoOnePlaysTheseVersions.put(name, this);
    }

    //? if >1.19 {
    /*public void set(Entity e, T val) {
        e.setAttached(PERKY_ATTACHMENT, val);
    }
    public boolean has(Entity e) {
        return e.hasAttached(PERKY_ATTACHMENT);
    }
    public T get(Entity e) {
        return e.getAttached(PERKY_ATTACHMENT);
    }
    *///? } else {
    public void set(Entity e, T val) {
        ((AttachmentAccessor) e).perky$attachments().put(this, new AttachmentWithValue<>(this, val));
    }
    public boolean has(Entity e) {
        return ((AttachmentAccessor) e).perky$attachments().containsKey(this);
    }
    public T get(Entity e) {
        return (T) (((AttachmentAccessor) e).perky$attachments().get(this).getValue());
    }
    public T getOrElse(Entity e, T t) {
        if (!has(e)) {
            return t;
        }
        return (T) (((AttachmentAccessor) e).perky$attachments().get(this).getValue());
    }
    //? }
}
