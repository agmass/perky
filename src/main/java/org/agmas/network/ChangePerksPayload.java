package org.agmas.network;

//? if <1.21.1 {
/*import net.minecraft.resources.ResourceLocation;
import net.fabricmc.fabric.api.networking.v1.FabricPacket;
import net.fabricmc.fabric.api.networking.v1.PacketType;
*///? } else {
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
//? }
        import org.agmas.attachment.BloodstreamAttachment;
        import org.agmas.porting.PPIdentifier;

//? if >=1.21.1 {
public record ChangePerksPayload(BloodstreamAttachment chosenPerks) implements CustomPacketPayload
{
    public static final PPIdentifier CHANGE_PERKS_ID = PPIdentifier.of("change_perks");
    public static final CustomPacketPayload.Type<ChangePerksPayload> ID = new CustomPacketPayload.Type<>(CHANGE_PERKS_ID.id);
    public static final StreamCodec<RegistryFriendlyByteBuf, ChangePerksPayload> CODEC = new StreamCodec<RegistryFriendlyByteBuf, ChangePerksPayload>() {
        @Override
        public ChangePerksPayload decode(RegistryFriendlyByteBuf object) {
            return new ChangePerksPayload(BloodstreamAttachment.decode(object));
        }

        @Override
        public void encode(RegistryFriendlyByteBuf object, ChangePerksPayload object2) {
            object2.chosenPerks().encode(object,object2.chosenPerks());
        }
    };


    @Override
    public BloodstreamAttachment chosenPerks() {
        return chosenPerks;
    }

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return ID;
    }
}
//? } else {
/*public record ChangePerksPayload(PerkyPlayerAttachment chosenPerks) implements FabricPacket
{
    public static final PPIdentifier CHANGE_PERKS_ID = PPIdentifier.of("change_perks");

    public static PacketType<ChangePerksPayload> PACKET_TYPE = PacketType.create(CHANGE_PERKS_ID.id, ChangePerksPayload::read);
    @Override
    public void write(FriendlyByteBuf friendlyByteBuf) {
        chosenPerks.encode(friendlyByteBuf,chosenPerks);
    }

    public static ChangePerksPayload read(FriendlyByteBuf friendlyByteBuf) {
        return new ChangePerksPayload(PerkyPlayerAttachment.decode(friendlyByteBuf));
    }

    @Override
    public PacketType<?> getType() {
        return PACKET_TYPE;
    }
}
*///? }