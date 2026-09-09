package org.agmas.porting;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
//? if >1.20.1 {
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.codec.StreamCodec;
//? }

public class PPComponentOrNBT<T> {
    //? if >1.20.1
    public final DataComponentType<T> COMPONENT_FORM;
    public final Codec<T> codec;
    public final String name;

    public PPComponentOrNBT(
            String name,
            Codec<T> persistentCodec
                            //? if >1.20.1
                            , StreamCodec<ByteBuf, T> networkCodec
    ) {
        //? if >1.20.1 {
        COMPONENT_FORM = DataComponentType.<T>builder()
                .networkSynchronized(networkCodec)
                .persistent(persistentCodec).build();
         //? }
        this.codec = persistentCodec;
        this.name = name;
    }

    public void register() {
        //? if >1.20.1 {
        Registry.register(
                BuiltInRegistries.DATA_COMPONENT_TYPE,
                PPIdentifier.of(name).id,
                COMPONENT_FORM
        );
        //? }
    }

    public void remove(ItemStack item) {
        //? if <=1.20.1 {
        /*CompoundTag tag = item.getOrCreateTag();
        tag.remove(name);
        item.setTag(tag);
        *///? } else {
        item.remove(COMPONENT_FORM);
         //? }
    }
    public void set(ItemStack item, T value) {
        //? if <=1.20.1 {
        /*CompoundTag tag = item.getOrCreateTag();
        tag.put(name, codec.encodeStart(NbtOps.INSTANCE, value).getOrThrow(false, (a)->{}));
        item.setTag(tag);
        *///? } else {
        item.set(COMPONENT_FORM, value);
        //? }
    }
    public boolean has(ItemStack item) {
        //? if <=1.20.1 {
        /*CompoundTag tag = item.getOrCreateTag();
        return tag.contains(name);
        *///? } else {
        return item.has(COMPONENT_FORM);
        //? }
    }

    public T get(ItemStack item) {
        //? if <=1.20.1 {
        /*CompoundTag tag = item.getOrCreateTag();
        return codec.decode(NbtOps.INSTANCE, tag.get(name)).getOrThrow(false, (a)->{}).getFirst();
        *///? } else {
        return item.get(COMPONENT_FORM);
        //? }
    }


}
