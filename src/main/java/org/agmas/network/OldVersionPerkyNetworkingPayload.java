package org.agmas.network;

//? if <=1.20.4 {
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.network.FriendlyByteBuf;
import org.agmas.attachment.PerkyPlayerAttachment;
import org.agmas.porting.PPIdentifier;

import java.util.UUID;

public record OldVersionPerkyNetworkingPayload(int id, PerkyPlayerAttachment playerAttachment) implements FabricPacket
{
    public static final PPIdentifier OLD_VERSION_PERKY_NETWORKING = PPIdentifier.of("old_version_perky_networking");
    public static PacketType<OldVersionPerkyNetworkingPayload> PACKET_TYPE = PacketType.create(OLD_VERSION_PERKY_NETWORKING.id, OldVersionPerkyNetworkingPayload::read);

    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        friendlyByteBuf.writeInt(id);
        playerAttachment.encode(friendlyByteBuf,playerAttachment);
    }

    public static OldVersionPerkyNetworkingPayload read(FriendlyByteBuf friendlyByteBuf) {
        int i = friendlyByteBuf.readInt();
        PerkyPlayerAttachment playerAttachment1 = PerkyPlayerAttachment.decode(friendlyByteBuf);
        return new OldVersionPerkyNetworkingPayload(i, playerAttachment1);
    }

    @Override
    public PacketType<?> getType() {
        return PACKET_TYPE;
    }
}
//? }