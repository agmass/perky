package org.agmas.attachment;


import io.netty.buffer.ByteBuf;
import net.minecraft.network.Utf8String;
import net.minecraft.network.codec.StreamCodec;
import org.agmas.Perky;
import org.agmas.init.ModRegistries;
import org.agmas.porting.PPIdentifier;

import java.util.ArrayList;

public class PerkyPlayerAttachment {

    public ArrayList<PerkyItem> appliedItems = new ArrayList<>();

    public static StreamCodec<ByteBuf, PerkyPlayerAttachment> STREAM_CODEC = new StreamCodec<ByteBuf, PerkyPlayerAttachment>() {
        public PerkyPlayerAttachment decode(ByteBuf byteBuf) {
            PerkyPlayerAttachment playerAttachment = new PerkyPlayerAttachment();
            while (byteBuf.isReadable()) {
                String id = Utf8String.read(byteBuf,32767);
                if (id.equals("")) break;

                if (ModRegistries.PERKY_ITEM.getOptional(PPIdentifier.parse(id).id).isPresent()) {
                    playerAttachment.appliedItems.add(ModRegistries.PERKY_ITEM.getOptional(PPIdentifier.parse(id).id).get());
                }
            }
            return playerAttachment;
        }



        public void encode(ByteBuf byteBuf, PerkyPlayerAttachment perkyAttachment) {
            for (PerkyItem item : perkyAttachment.appliedItems) {
                Utf8String.write(byteBuf, PerkyItem.getKey(item).id.toString(), 32767);
            }
        }
    };
}
