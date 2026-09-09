package org.agmas.mixin.porting;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.agmas.accessors.AttachmentAccessor;
import org.agmas.attachment.BloodstreamAttachment;
import org.agmas.init.ModAttachments;
//? if <=1.20.4
//import org.agmas.network.OldVersionPerkyNetworkingPayload;
import org.agmas.porting.AttachmentWithValue;
import org.agmas.porting.PPAttachment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Mixin(Entity.class)
public class AttachmentMixin implements AttachmentAccessor {

    @Unique
    public HashMap<PPAttachment<?>, AttachmentWithValue<?>> attachments = new HashMap<>();
    //? if <=1.19 {
    /*@Inject(method = "saveWithoutId", at = @At("HEAD"))
    public void saveAttachments(CompoundTag compoundTag, CallbackInfoReturnable<Boolean> cir) {
        CompoundTag attachmentTag = new CompoundTag();
        for (AttachmentWithValue<?> attachment : attachments.values()) {
            attachmentTag.put(attachment.getAttachment().name, attachment.encodeTag().get().left().get());
        }
        compoundTag.put("pp_attachments", attachmentTag);
    }


    @Inject(method = "load", at = @At("HEAD"))
    public void loadAttachments(CompoundTag compoundTag, CallbackInfo ci) {
        Set<String> attachmentKeys = compoundTag.getCompound("pp_attachments").getAllKeys();
        for (String attachmentKey : attachmentKeys) {
            if (PPAttachment.reallyShitWayToDoARegistryButIDontCareNoOnePlaysTheseVersions.containsKey(attachmentKey)) {
                PPAttachment ppAttachment = PPAttachment.reallyShitWayToDoARegistryButIDontCareNoOnePlaysTheseVersions.get(attachmentKey);
                Pair ohMyGodWhatTheFuckAmIDoing = (Pair) ppAttachment.codec.decode(NbtOps.INSTANCE, compoundTag.getCompound("pp_attachments").get(attachmentKey)).get().left().get();
                attachments.put(ppAttachment, new AttachmentWithValue<>(ppAttachment, ohMyGodWhatTheFuckAmIDoing.getFirst()));
            }
        }
    }
    *///? }

    @Inject(method = "tick", at = @At("HEAD"))
    public void pptick(CallbackInfo ci) {


        Entity p = ((Entity) (Object)this);
        //? if <=1.20.4 {
        /*if (ModAttachments.PERKY_ATTACHMENT.has(p) && !p.level
                //? if =1.20.1
                        ()
                .isClientSide()) {
            BloodstreamAttachment playerAttachment = ModAttachments.PERKY_ATTACHMENT.get(p);
            for (Player player : p.level
                    //? if =1.20.1
                            ()
                    .players()) {
                if (!playerAttachment.networkedTo.contains(player.getUUID())) {
                    if (player.distanceTo(p) < 250) {
                        playerAttachment.networkedTo.add(player.getUUID());
                        ServerPlayNetworking.send((ServerPlayer) player, new OldVersionPerkyNetworkingPayload(p.getId(),playerAttachment));
                    }
                }
            }
        }
        *///? }
    }

    @Override
    public HashMap<PPAttachment<?>, AttachmentWithValue<?>> perky$attachments() {
        return attachments;
    }
}
