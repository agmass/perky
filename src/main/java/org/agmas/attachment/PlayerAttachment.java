package org.agmas.attachment;


import io.netty.buffer.ByteBuf;
import net.minecraft.network.Utf8String;
import net.minecraft.network.codec.StreamCodec;
import org.agmas.init.ModRegistries;
import org.agmas.porting.PPIdentifier;

import java.util.ArrayList;

public class PlayerAttachment {

    public ArrayList<PerkyItem> appliedItems = new ArrayList<>();

    public static StreamCodec<ByteBuf, PlayerAttachment> STREAM_CODEC = new StreamCodec<ByteBuf, PlayerAttachment>() {
        public PlayerAttachment decode(ByteBuf byteBuf) {
            PlayerAttachment playerAttachment = new PlayerAttachment();
            while (byteBuf.isReadable()) {
                String id = Utf8String.read(byteBuf,32767);
                playerAttachment.appliedItems.add(ModRegistries.PERKY_ITEM.getOptional(PPIdentifier.parse(id).id).get());
            }
            return playerAttachment;
        }

        public void encode(ByteBuf byteBuf, PlayerAttachment perkyAttachment) {
            for (PerkyItem item : perkyAttachment.appliedItems) {
                Utf8String.write(byteBuf, PerkyItem.getKey(item).id.toString(), 32767);
            }
        }
    };
}
