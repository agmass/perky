package org.agmas.network;

import net.minecraft.network.FriendlyByteBuf;
//? if <1.21.1 {
/*import net.minecraft.resources.ResourceLocation;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
*///? } else {
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//? }
import net.minecraft.util.Unit;
import org.agmas.attachment.BloodstreamAttachment;
import org.agmas.porting.PPIdentifier;

//? if >=1.21.1 {
public record OpenPerkyMenuPayload(BloodstreamAttachment options) implements CustomPacketPayload
{
    public static final PPIdentifier OPEN_PERKY_MENU_ID = PPIdentifier.of("open_perky_menu");
    public static final CustomPacketPayload.Type<OpenPerkyMenuPayload> ID = new CustomPacketPayload.Type<>(OPEN_PERKY_MENU_ID.id);
    public static final StreamCodec<RegistryFriendlyByteBuf, OpenPerkyMenuPayload> CODEC = new StreamCodec<RegistryFriendlyByteBuf, OpenPerkyMenuPayload>() {
        @Override
        public OpenPerkyMenuPayload decode(RegistryFriendlyByteBuf object) {
            return new OpenPerkyMenuPayload(BloodstreamAttachment.decode(object));
        }

        @Override
        public void encode(RegistryFriendlyByteBuf object, OpenPerkyMenuPayload object2) {
            object2.options.encode(object, object2.options);
        }
    };


    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
//? } else {
/*public record OpenPerkyMenuPayload() implements FabricPacket
{
    public static final PPIdentifier OPEN_PERKY_MENU_ID = PPIdentifier.of("open_perky_menu");

    public static PacketType<OpenPerkyMenuPayload> PACKET_TYPE = PacketType.create(OPEN_PERKY_MENU_ID.id,OpenPerkyMenuPayload::read);
    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {

    }

    public static OpenPerkyMenuPayload read(FriendlyByteBuf friendlyByteBuf) {
        return new OpenPerkyMenuPayload();
    }

    @Override
    public PacketType<?> getType() {
        return PACKET_TYPE;
    }
}
*///? }