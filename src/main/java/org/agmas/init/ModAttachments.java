package org.agmas.init;

//? if >1.21.1
import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import org.agmas.attachment.BloodstreamAttachment;
import org.agmas.porting.PPAttachment;
import org.agmas.porting.PPIdentifier;

public class ModAttachments {

    public static final PPAttachment<BloodstreamAttachment> PERKY_ATTACHMENT =
            new PPAttachment(
                    "perky_attachment",
                    ()-> new BloodstreamAttachment(),
                    //? if >1.21.1
                    BloodstreamAttachment.STREAM_CODEC,
                    BloodstreamAttachment.CODEC
            );

    public static void init() {}
}
