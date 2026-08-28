package org.agmas.init;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.world.entity.player.Player;
import org.agmas.attachment.PlayerAttachment;
import org.agmas.porting.PPIdentifier;
import org.agmas.porting.PPRegistrar;

public class ModAttachments {

    public static final AttachmentType<PlayerAttachment> PERKY_ATTACHMENT = AttachmentRegistry.create(
            PPIdentifier.of("perky_attachment").id,
            integerBuilder -> integerBuilder.initializer(() -> new PlayerAttachment()).syncWith(PlayerAttachment.STREAM_CODEC, AttachmentSyncPredicate.targetOnly())
    );

    public static void init() {}
}
