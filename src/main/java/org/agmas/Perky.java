package org.agmas;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.impl.attachment.AttachmentTargetImpl;
//? if >1.21.1
//import net.fabricmc.fabric.impl.attachment.sync.AttachmentChange;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.Holder;
//? if >=1.21.11 {
/*import net.minecraft.server.permissions.Permissions;
*///? } else if >1.21.1 {
/*import net.fabricmc.fabric.impl.attachment.sync.s2c.AttachmentSyncPayloadS2C;
*///? }
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import org.agmas.attachment.PerkyItem;
import org.agmas.attachment.PerkyPlayerAttachment;
import org.agmas.command.PerkyItemArgument;
import org.agmas.init.*;
import org.agmas.network.OpenPerkyMenuPayload;
import org.agmas.network.ChangePerksPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//? if >=1.21.1
//import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

import java.util.List;

public class Perky implements ModInitializer {
	public static final String MOD_ID = "perky";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.init();
		ModAttachments.init();
		ModRegistries.init();
		ModEntities.init();
		PerkyItems.init();
		ModArgumentTypes.init();

		PerkyCommands.init();


		//? if >=26.1 {
		/*PayloadTypeRegistry.clientboundPlay()
		 *///? } else if >=1.21.1 {
		/*PayloadTypeRegistry.playS2C()
				*///? }
				//? if >1.20.4
				//.register(OpenPerkyMenuPayload.ID, OpenPerkyMenuPayload.CODEC);


		//? if >=26.1 {
		/*PayloadTypeRegistry.serverboundPlay()
		 *///? } else if >=1.21.1 {
		/*PayloadTypeRegistry.playC2S()
				*///? }
				//? if >1.20.4
				//.register(ChangePerksPayload.ID, ChangePerksPayload.CODEC);

		//? if >=1.21.1 {
		/*ServerPlayNetworking.registerGlobalReceiver(ChangePerksPayload.ID, ((payload, context) -> {
			context.player().setAttached(ModAttachments.PERKY_ATTACHMENT, payload.chosenPerks());
		}));
		*///? } else {
		ServerPlayNetworking.registerGlobalReceiver(ChangePerksPayload.PACKET_TYPE, ((payload, localPlayer, packetSender) -> {
			localPlayer.setAttached(ModAttachments.PERKY_ATTACHMENT, payload.chosenPerks());
		}));
		//? }


		//? if >=1.21.1 {
		/*ServerLivingEntityEvents.AFTER_DAMAGE.register(((livingEntity, damageSource, v, v1, b) -> {
		*///? } else {
		ServerLivingEntityEvents.ALLOW_DAMAGE.register(((livingEntity, damageSource, v) -> {
		//? }
			for (PerkyItem attachment : getAttachments(livingEntity)) {
				attachment.onDamage(livingEntity,damageSource,v);
			}
			//? if <=1.20.4
				return true;
		}));
	}

	public static boolean removeAttachment(LivingEntity livingEntity, PerkyItem item) {
		PerkyPlayerAttachment playerAttachment = livingEntity.getAttachedOrElse(ModAttachments.PERKY_ATTACHMENT, new PerkyPlayerAttachment());
		if (!playerAttachment.appliedItems.contains(item)) {
			return false;
		} else {
			playerAttachment.appliedItems.remove(item);

			if (item.attributes != null) {
				for (PerkyItem.AttributePair attribute : item.attributes) {
					//? if >=1.21.1 {
					/*Holder<Attribute> attr = attribute.holder;
					 *///? } else {
					Attribute attr = attribute.holder.value();
					//? }

					//? if >=1.21.1 {
					/*if (!livingEntity.getAttribute(attr).hasModifier(attribute.modifier.id())) {
					 *///? } else {
					if (!livingEntity.getAttribute(attr).hasModifier(attribute.modifier)) {
						//? }
						livingEntity.getAttribute(attr).addPermanentModifier(attribute.modifier);
					}
				}
			}

			livingEntity.setAttached(ModAttachments.PERKY_ATTACHMENT,playerAttachment);

			AttachmentTargetImpl attachmentTarget = ((AttachmentTargetImpl) livingEntity);
			//? if >=26.2 {
			/*attachmentTarget.fabric_syncChange(ModAttachments.PERKY_ATTACHMENT, new AttachmentChange(attachmentTarget.fabric_getSyncTargetInfo(), ModAttachments.PERKY_ATTACHMENT, playerAttachment));
			*///? } else if =26.1 {
			/*attachmentTarget.fabric_syncChange(ModAttachments.PERKY_ATTACHMENT, AttachmentChange.create(attachmentTarget.fabric_getSyncTargetInfo(), ModAttachments.PERKY_ATTACHMENT, playerAttachment, attachmentTarget.fabric_getRegistryAccess()));
			 *///? } else if =1.21.11 {
			/*attachmentTarget.fabric_syncChange(ModAttachments.PERKY_ATTACHMENT, AttachmentChange.create(attachmentTarget.fabric_getSyncTargetInfo(), ModAttachments.PERKY_ATTACHMENT, playerAttachment, attachmentTarget.fabric_getDynamicRegistryManager()));
			 *///? } else if >1.21.1 {
			/*attachmentTarget.fabric_syncChange(ModAttachments.PERKY_ATTACHMENT, new AttachmentSyncPayloadS2C(List.of(AttachmentChange.create(attachmentTarget.fabric_getSyncTargetInfo(), ModAttachments.PERKY_ATTACHMENT, playerAttachment, attachmentTarget.fabric_getDynamicRegistryManager()))));
			 *///? }
			playerAttachment.networkedTo.clear();
			return true;
		}
	}


	public static boolean addAttachment(LivingEntity livingEntity, PerkyItem item) {
		PerkyPlayerAttachment playerAttachment = livingEntity.getAttachedOrElse(ModAttachments.PERKY_ATTACHMENT, new PerkyPlayerAttachment());
		if (playerAttachment.appliedItems.contains(item)) {
			return false;
		} else {
			playerAttachment.appliedItems.add(item);
			livingEntity.setAttached(ModAttachments.PERKY_ATTACHMENT,playerAttachment);

			AttachmentTargetImpl attachmentTarget = ((AttachmentTargetImpl) livingEntity);

			//? if >=26.2 {
			/*attachmentTarget.fabric_syncChange(ModAttachments.PERKY_ATTACHMENT, new AttachmentChange(attachmentTarget.fabric_getSyncTargetInfo(), ModAttachments.PERKY_ATTACHMENT, playerAttachment));
			*///? } else if =26.1 {
			/*attachmentTarget.fabric_syncChange(ModAttachments.PERKY_ATTACHMENT, AttachmentChange.create(attachmentTarget.fabric_getSyncTargetInfo(), ModAttachments.PERKY_ATTACHMENT, playerAttachment, attachmentTarget.fabric_getRegistryAccess()));
			*///? } else if =1.21.11 {
			/*attachmentTarget.fabric_syncChange(ModAttachments.PERKY_ATTACHMENT, AttachmentChange.create(attachmentTarget.fabric_getSyncTargetInfo(), ModAttachments.PERKY_ATTACHMENT, playerAttachment, attachmentTarget.fabric_getDynamicRegistryManager()));
			 *///? } else if >1.21.1 {
			/*attachmentTarget.fabric_syncChange(ModAttachments.PERKY_ATTACHMENT, new AttachmentSyncPayloadS2C(List.of(AttachmentChange.create(attachmentTarget.fabric_getSyncTargetInfo(), ModAttachments.PERKY_ATTACHMENT, playerAttachment, attachmentTarget.fabric_getDynamicRegistryManager()))));
			 *///? }
			playerAttachment.networkedTo.clear();
			return true;
		}
	}

	public static List<PerkyItem> getAttachments(LivingEntity livingEntity) {
		if (livingEntity.hasAttached(ModAttachments.PERKY_ATTACHMENT)) {
			return livingEntity.getAttached(ModAttachments.PERKY_ATTACHMENT).appliedItems;
		}
		return List.of();
	}
}
