package org.agmas.init;

import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Encoder;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
//? if >1.21.1
//import net.fabricmc.fabric.api.attachment.v1.AttachmentSyncPredicate;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.core.UUIDUtil;
import net.minecraft.util.ExtraCodecs;
import org.agmas.attachment.PerkyPlayerAttachment;
import org.agmas.porting.PPIdentifier;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class ModAttachments {

    public static final AttachmentType<PerkyPlayerAttachment> PERKY_ATTACHMENT =
            //? if >1.21.1
            //AttachmentRegistry.create(
            //? if <=1.21.1
            AttachmentRegistry.createPersistent(
                    PPIdentifier.of("perky_attachment").id,
                    //? if >1.21.1 {
                    /*integerBuilder -> integerBuilder.initializer(() -> new PerkyPlayerAttachment())
                            .copyOnDeath()
                            .syncWith(PerkyPlayerAttachment.STREAM_CODEC, AttachmentSyncPredicate.targetOnly())
                            .persistent(
                    *///? }
                        PerkyPlayerAttachment.CODEC
                    //? if >1.21.1
                    //)
            );

    public static void init() {}
}
