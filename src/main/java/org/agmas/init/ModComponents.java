package org.agmas.init;

import com.mojang.serialization.Codec;
//? if >=1.21.1
import net.minecraft.network.codec.ByteBufCodecs;
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

    public static final PPComponentOrNBT<Integer> STACK_SIZE_CHANGED_BY_PERKS =
            new PPComponentOrNBT<>(
                    "stack_size_changed_by_perky",
                    Codec.INT
                    //? if >1.20.4
                    , ByteBufCodecs.INT
            );


    public static void init() {
        BLOOD_COMPONENT.register();
        STACK_SIZE_CHANGED_BY_PERKS.register();
    }
}
