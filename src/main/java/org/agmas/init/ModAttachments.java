package org.agmas.init;

import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
//? if >1.21.1
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import org.agmas.attachment.BloodstreamAttachment;
import org.agmas.porting.PPIdentifier;

public class ModAttachments {

    public static final AttachmentType<BloodstreamAttachment> PERKY_ATTACHMENT =
            //? if >1.21.1
            AttachmentRegistry.create(
            //? if <=1.21.1
            //AttachmentRegistry.createPersistent(
                    PPIdentifier.of("perky_attachment").id,
                    //? if >1.21.1 {
                    integerBuilder -> integerBuilder.initializer(() -> new BloodstreamAttachment())
                            .copyOnDeath()
                            .syncWith(BloodstreamAttachment.STREAM_CODEC, AttachmentSyncPredicate.all())
                            .persistent(
                    //? }
                        BloodstreamAttachment.CODEC
                    //? if >1.21.1
                    )
            );

    public static void init() {}
}
