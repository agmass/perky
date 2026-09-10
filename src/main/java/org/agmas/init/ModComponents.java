package org.agmas.init;

import com.mojang.serialization.Codec;
import org.agmas.attachment.BloodstreamAttachment;
import org.agmas.porting.PPComponentOrNBT;

public class ModComponents {
    public static final PPComponentOrNBT<BloodstreamAttachment> BLOOD_COMPONENT =
            new PPComponentOrNBT<>(
                    "blood_component",
                    BloodstreamAttachment.CODEC
                    //? if >1.20.4
                    , BloodstreamAttachment.STREAM_CODEC
            );

    public static void init() {
        BLOOD_COMPONENT.register();
    }
}
