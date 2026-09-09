package org.agmas.attachment;


import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
//? if >1.20.1
import net.fabricmc.loader.impl.util.log.Log;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.EncoderException;
import net.fabricmc.loader.impl.util.log.LogCategory;
//? if >=1.21.1 {
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.component.TooltipProvider;
import net.minecraft.network.Utf8String;

//? }
//? if >1.21.1
import net.minecraft.core.component.DataComponentGetter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import org.agmas.init.ModRegistries;
import org.agmas.porting.PPIdentifier;

import java.awt.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

public class BloodstreamAttachment
    //? if >1.21.1
    implements TooltipProvider
{

    public static final Codec<BloodstreamAttachment> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.STRING.listOf().fieldOf("applied_items").forGetter(BloodstreamAttachment::getAppliedItemsStrings)
    ).apply(instance, BloodstreamAttachment::new));

    public boolean usingConsole = false;
    public BloodstreamAttachment(List<String> perkyItemStrings) {
        for (String perkyItemString : perkyItemStrings) {
            //? if >=1.21.11 {
            appliedItems.add(ModRegistries.PERKY_ITEM.get(PPIdentifier.parse(perkyItemString).id).get().value());
             //? } else {
            /*appliedItems.add(ModRegistries.PERKY_ITEM.get(PPIdentifier.parse(perkyItemString).id));
            *///? }
        }
    }
    public BloodstreamAttachment() {}

    public ArrayList<PerkyItem> appliedItems = new ArrayList<>();

    public ArrayList<UUID> networkedTo = new ArrayList<>();
    // This is like, Really Jank, LOL
    //? if >=1.21.1 {
    public static StreamCodec<ByteBuf, BloodstreamAttachment> STREAM_CODEC = new StreamCodec<ByteBuf, BloodstreamAttachment>() {
        public BloodstreamAttachment decode(ByteBuf byteBuf) {
            return BloodstreamAttachment.decode(byteBuf);
        }

        public void encode(ByteBuf byteBuf, BloodstreamAttachment perkyAttachment) {
            perkyAttachment.encode(byteBuf,perkyAttachment);
        }
    };
    //?}


    public ArrayList<String> getAppliedItemsStrings() {
        ArrayList<String> stringarr = new ArrayList<>();
        for (PerkyItem appliedItem : appliedItems) {
            //? if <1.21.11
            //stringarr.add(ModRegistries.PERKY_ITEM.getResourceKey(appliedItem).get().location().toString());
            //? if >=1.21.11
            stringarr.add(ModRegistries.PERKY_ITEM.getResourceKey(appliedItem).get().identifier().toString());
        }
        return stringarr;
    }

    public static BloodstreamAttachment decode(ByteBuf byteBuf) {
        BloodstreamAttachment playerAttachment = new BloodstreamAttachment();
        while (byteBuf.isReadable()) {
            if (byteBuf.readByte() == (byte)254) break;
            byteBuf.readerIndex(byteBuf.readerIndex()-1);
            //? if <=1.20.1 {
            /*String id = readString(byteBuf,32767);
             *///? } else {
            String id = Utf8String.read(byteBuf, 32767);
            //? }
            if (id.equals("")) break;

            if (ModRegistries.PERKY_ITEM.getOptional(PPIdentifier.parse(id).id).isPresent()) {
                playerAttachment.appliedItems.add(ModRegistries.PERKY_ITEM.getOptional(PPIdentifier.parse(id).id).get());
            }
        }
        return playerAttachment;
    }

    public void encode(ByteBuf byteBuf, BloodstreamAttachment perkyAttachment) {
        for (PerkyItem item : perkyAttachment.appliedItems) {
            if (PerkyItem.getKey(item) != null) {
                //? if <=1.20.1 {
                /*writeString(byteBuf, PerkyItem.getKey(item).id.toString(), 32767);
                *///? } else {
                Utf8String.write(byteBuf, PerkyItem.getKey(item).id.toString(), 32767);
                 //? }
            }
        }
        byteBuf.writeByte(254);
    }

    //? if >1.21.1 {
    @Override
    public void addToTooltip(Item.TooltipContext tooltipContext, Consumer<Component> consumer, TooltipFlag tooltipFlag, DataComponentGetter dataComponentGetter) {
        consumer.accept(Component.empty());        consumer.accept(Component.translatable("perky.bloodstream").withColor(new Color(130,0,0).getRGB()));
        for (PerkyItem item : appliedItems) {
            PPIdentifier id = new PPIdentifier(ModRegistries.PERKY_ITEM.getResourceKey(item).get().identifier());
            consumer.accept(Component.translatable("perky." + (item.worth > 0 ? "perk" : "binding") + "." + id.id.getNamespace() + "." + id.id.getPath()));
        }
    }
    //? }

    //? if <=1.20.1 {

    /*public static String readString(ByteBuf byteBuf, int i) {
        int j = ByteBufUtil.utf8MaxBytes(i);
        int k = readVInt(byteBuf);
        if (k > j) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + k + " > " + j + ")");
        } else if (k < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        } else {
            int l = byteBuf.readableBytes();
            if (k > l) {
                throw new DecoderException("Not enough bytes in buffer, expected " + k + ", but got " + l);
            } else {
                String string = byteBuf.toString(byteBuf.readerIndex(), k, StandardCharsets.UTF_8);
                byteBuf.readerIndex(byteBuf.readerIndex() + k);
                if (string.length() > i) {
                    int var10002 = string.length();
                    throw new DecoderException("The received string length is longer than maximum allowed (" + var10002 + " > " + i + ")");
                } else {
                    return string;
                }
            }
        }
    }

    public static void writeString(ByteBuf byteBuf, CharSequence charSequence, int i) {
        if (charSequence.length() > i) {
            int var10002 = charSequence.length();
            throw new EncoderException("String too big (was " + var10002 + " characters, max " + i + ")");
        } else {
            int j = ByteBufUtil.utf8MaxBytes(charSequence);
            ByteBuf byteBuf2 = byteBuf.alloc().buffer(j);

            try {
                int k = ByteBufUtil.writeUtf8(byteBuf2, charSequence);
                int l = ByteBufUtil.utf8MaxBytes(i);
                if (k > l) {
                    throw new EncoderException("String too big (was " + k + " bytes encoded, max " + l + ")");
                }

                writeVInt(byteBuf,k);
                byteBuf.writeBytes(byteBuf2);
            } finally {
                byteBuf2.release();
            }

        }
    }

    public static int readVInt(ByteBuf byteBuf) {
        int i = 0;
        int j = 0;

        byte b;
        do {
            b = byteBuf.readByte();
            i |= (b & 127) << j++ * 7;
            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while(hasContinuationBit(b));

        return i;
    }
    public static boolean hasContinuationBit(byte b) {
        return (b & 128) == 128;
    }


    public static ByteBuf writeVInt(ByteBuf byteBuf, int i) {
        while((i & -128) != 0) {
            byteBuf.writeByte(i & 127 | 128);
            i >>>= 7;
        }

        byteBuf.writeByte(i);
        return byteBuf;
    }
    *///? }
}
