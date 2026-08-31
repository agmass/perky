package org.agmas.init;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import org.agmas.attachment.PerkyPlayerAttachment;
import org.agmas.porting.PPIdentifier;

public class ModAttachments {

    public static final AttachmentType<PerkyPlayerAttachment> PERKY_ATTACHMENT = AttachmentRegistry.create(
            PPIdentifier.of("perky_attachment").id,
            integerBuilder -> integerBuilder.initializer(() -> new PerkyPlayerAttachment()).copyOnDeath().syncWith(PerkyPlayerAttachment.STREAM_CODEC, AttachmentSyncPredicate.targetOnly())
    );

    public static void init() {}
}
