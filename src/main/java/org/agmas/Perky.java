package org.agmas;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
//? if >1.19
import net.fabricmc.fabric.impl.attachment.AttachmentTargetImpl;
//? if >1.21.1
import net.fabricmc.fabric.impl.attachment.sync.AttachmentChange;

//? if >=26.1 {
import net.fabricmc.fabric.api.item.v1.ItemComponentTooltipProviderRegistry;
//? } else if >1.21.1 {
/*import net.fabricmc.fabric.api.item.v1.ComponentTooltipAppenderRegistry;
*///? }
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
//? if >=1.21.11 {
//? } else if >1.21.1 {
/*import net.fabricmc.fabric.impl.attachment.sync.s2c.AttachmentSyncPayloadS2C;
*///? }
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.agmas.attachment.BloodstreamAttachment;
import org.agmas.attachment.PerkyItem;
import org.agmas.init.*;
import org.agmas.network.OpenPerkyMenuPayload;
import org.agmas.network.ChangePerksPayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//? if >=1.21.1
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;

import java.util.ArrayList;
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
		ModItems.init();
		ModComponents.init();
		ModTags.init();
		ModArgumentTypes.init();

		PerkyCommands.init();


		//? if >=26.1 {
		PayloadTypeRegistry.clientboundPlay()
		 //? } else if >=1.21.1 {
		/*PayloadTypeRegistry.playS2C()
				*///? }
				//? if >1.20.4
				.register(OpenPerkyMenuPayload.ID, OpenPerkyMenuPayload.CODEC);


		//? if >=26.1 {
		PayloadTypeRegistry.serverboundPlay()
		 //? } else if >=1.21.1 {
		/*PayloadTypeRegistry.playC2S()
				*///? }
				//? if >1.20.4
				.register(ChangePerksPayload.ID, ChangePerksPayload.CODEC);

		//? if >=1.21.1 {
		ServerPlayNetworking.registerGlobalReceiver(ChangePerksPayload.ID, ((payload, context) -> {
			BlockPos consolePos = ModAttachments.PERKY_ATTACHMENT.getOrElse(context.player(), new BloodstreamAttachment()).usingConsole;
			if (consolePos == null) return;
			BloodstreamAttachment bloodstreamAttachment = ModAttachments.PERKY_ATTACHMENT.getOrElse(context.player(), new BloodstreamAttachment());
			ArrayList<PerkyItem> toRemove = new ArrayList<>();
			for (PerkyItem appliedItem : bloodstreamAttachment.appliedItems) {
				if (!payload.chosenPerks().appliedItems.contains(appliedItem)) {
					toRemove.add(appliedItem);
				}
			}
			for (PerkyItem perkyItem : toRemove) {
				removeAttachment(context.player(), perkyItem);
			}
			for (PerkyItem appliedItem : payload.chosenPerks().appliedItems) {
				if (!bloodstreamAttachment.appliedItems.contains(appliedItem)) {
					addAttachment(context.player(), appliedItem);
				}
			}

			ServerLevel level = (ServerLevel) context.player().level();
			level.blockEvent(consolePos, level.getBlockState(consolePos).getBlock(), 0, 0);

		}));
		//? } else {
		/*ServerPlayNetworking.registerGlobalReceiver(ChangePerksPayload.PACKET_TYPE, ((payload, localPlayer, packetSender) -> {
			BloodstreamAttachment bloodstreamAttachment = ModAttachments.PERKY_ATTACHMENT.getOrElse(localPlayer, new BloodstreamAttachment());

			ArrayList<PerkyItem> toRemove = new ArrayList<>();
			for (PerkyItem appliedItem : bloodstreamAttachment.appliedItems) {
				if (!payload.chosenPerks().appliedItems.contains(appliedItem)) {
					toRemove.add(appliedItem);
				}
			}
			for (PerkyItem perkyItem : toRemove) {
				removeAttachment(localPlayer, perkyItem);
			}
			for (PerkyItem appliedItem : payload.chosenPerks().appliedItems) {
				if (!bloodstreamAttachment.appliedItems.contains(appliedItem)) {
					addAttachment(localPlayer, appliedItem);
				}
			}
		}));
		*///? }


		//? if >=1.21.1 {
		ServerLivingEntityEvents.AFTER_DAMAGE.register(((livingEntity, damageSource, v, v1, b) -> {
		//? } else {
		/*ServerLivingEntityEvents.ALLOW_DAMAGE.register(((livingEntity, damageSource, v) -> {
		*///? }
			for (PerkyItem attachment : getAttachments(livingEntity)) {
				attachment.onDamage(livingEntity,damageSource,v);
			}
			//? if <=1.20.4
				//return true;
		}));

		ServerLivingEntityEvents.AFTER_DEATH.register(((livingEntity, damageSource) -> {
			if (damageSource.getDirectEntity() instanceof Player attacker) {
				if (attacker.getMainHandItem().is(ModItems.SYRINGE) && !ModComponents.BLOOD_COMPONENT.has(attacker.getMainHandItem())) {
					ItemStack newStack = attacker.getMainHandItem().copyWithCount(1);
					ModComponents.BLOOD_COMPONENT.set(newStack, getBloodstreamFromMob(livingEntity));
					attacker.addItem(newStack);
					attacker.getMainHandItem().shrink(1);
					return;
				}

				if (attacker.getOffhandItem().is(ModItems.SYRINGE) && !ModComponents.BLOOD_COMPONENT.has(attacker.getOffhandItem())) {
					ItemStack newStack = attacker.getOffhandItem().copyWithCount(1);
					ModComponents.BLOOD_COMPONENT.set(newStack, getBloodstreamFromMob(livingEntity));
					attacker.addItem(newStack);
					attacker.getOffhandItem().shrink(1);
					return;
				}
			}
		}));


		//? if >=26.1 {
		ItemComponentTooltipProviderRegistry.addLast(ModComponents.BLOOD_COMPONENT.COMPONENT_FORM);
		 //? } else if >1.21.1 {
		/*ComponentTooltipAppenderRegistry.addLast(ModComponents.BLOOD_COMPONENT.COMPONENT_FORM);
		*///? }
	}

	public static BloodstreamAttachment getBloodstreamFromMob(LivingEntity livingEntity) {
		BloodstreamAttachment bloodstream = new BloodstreamAttachment();
		List<PerkyItem> items = Perky.getAttachments(livingEntity);
		if (!items.isEmpty()) {
			bloodstream.appliedItems.addAll(items);
		}
		bloodstream.appliedItems.add(ModRegistries.PERKY_ITEM.getRandom(livingEntity.getRandom()).get().value());
		return bloodstream;
	}

	public static boolean removeAttachment(LivingEntity livingEntity, PerkyItem item) {
		BloodstreamAttachment playerAttachment = ModAttachments.PERKY_ATTACHMENT.getOrElse(livingEntity, new BloodstreamAttachment());
		if (!playerAttachment.appliedItems.contains(item)) {
			return false;
		} else {
			playerAttachment.appliedItems.remove(item);
			item.onRemoved(livingEntity);

			if (item.attributes != null) {
				for (PerkyItem.AttributePair attribute : item.attributes) {
					//? if >=1.21.1 {
					Holder<Attribute> attr = attribute.holder;
					 //? } else {
					/*Attribute attr = attribute.holder;
					*///? }

					//? if >=1.21.1 {
					if (livingEntity.getAttribute(attr).hasModifier(attribute.modifier.id())) {
					 //? } else {
					/*if (livingEntity.getAttribute(attr).hasModifier(attribute.modifier)) {
						*///? }
						//? if >1.20.4
						livingEntity.getAttribute(attr).removeModifier(attribute.modifier);
						//? if <=1.20.4
						//livingEntity.getAttribute(attr).removePermanentModifier(attribute.modifier.getId());
					}
				}
			}

			ModAttachments.PERKY_ATTACHMENT.set(livingEntity,playerAttachment);

			sortAttachments(livingEntity);
			//? if >1.19
			AttachmentTargetImpl attachmentTarget = ((AttachmentTargetImpl) livingEntity);

			//? if >=26.2 {
			attachmentTarget.fabric_syncChange(ModAttachments.PERKY_ATTACHMENT.PERKY_ATTACHMENT, new AttachmentChange(attachmentTarget.fabric_getSyncTargetInfo(), ModAttachments.PERKY_ATTACHMENT.PERKY_ATTACHMENT, playerAttachment));
			//? } else if =26.1 {
			/*attachmentTarget.fabric_syncChange(ModAttachments.PERKY_ATTACHMENT.PERKY_ATTACHMENT, AttachmentChange.create(attachmentTarget.fabric_getSyncTargetInfo(), ModAttachments.PERKY_ATTACHMENT.PERKY_ATTACHMENT, playerAttachment, attachmentTarget.fabric_getRegistryAccess()));
			 *///? } else if =1.21.11 {
			/*attachmentTarget.fabric_syncChange(ModAttachments.PERKY_ATTACHMENT.PERKY_ATTACHMENT, AttachmentChange.create(attachmentTarget.fabric_getSyncTargetInfo(), ModAttachments.PERKY_ATTACHMENT.PERKY_ATTACHMENT, playerAttachment, attachmentTarget.fabric_getDynamicRegistryManager()));
			 *///? } else if >1.21.1 {
			/*attachmentTarget.fabric_syncChange(ModAttachments.PERKY_ATTACHMENT.PERKY_ATTACHMENT, new AttachmentSyncPayloadS2C(List.of(AttachmentChange.create(attachmentTarget.fabric_getSyncTargetInfo(), ModAttachments.PERKY_ATTACHMENT.PERKY_ATTACHMENT, playerAttachment, attachmentTarget.fabric_getDynamicRegistryManager()))));
			 *///? }
			playerAttachment.networkedTo.clear();
			return true;
		}
	}

	public static void sortAttachments(LivingEntity livingEntity) {
		List<PerkyItem> unsorted = Perky.getAttachments(livingEntity);
		ArrayList<PerkyItem> sorted = new ArrayList<>();

		BloodstreamAttachment playerAttachment = ModAttachments.PERKY_ATTACHMENT.getOrElse(livingEntity, new BloodstreamAttachment());

		for (PerkyItem perkyItem : unsorted) {
			if (perkyItem.worth <= 0) sorted.add(perkyItem);
		}

		for (PerkyItem perkyItem : unsorted) {
			if (perkyItem.worth > 0) sorted.add(perkyItem);
		}

		playerAttachment.appliedItems = sorted;
		ModAttachments.PERKY_ATTACHMENT.set(livingEntity,playerAttachment);
	}

	public static boolean addAttachment(LivingEntity livingEntity, PerkyItem item) {
		BloodstreamAttachment playerAttachment = ModAttachments.PERKY_ATTACHMENT.getOrElse(livingEntity, new BloodstreamAttachment());
		if (playerAttachment.appliedItems.contains(item)) {
			return false;
		} else {
			playerAttachment.appliedItems.add(item);
			ModAttachments.PERKY_ATTACHMENT.set(livingEntity,playerAttachment);
			sortAttachments(livingEntity);

			//? if >1.19
			AttachmentTargetImpl attachmentTarget = ((AttachmentTargetImpl) livingEntity);

			//? if >=26.2 {
			attachmentTarget.fabric_syncChange(ModAttachments.PERKY_ATTACHMENT.PERKY_ATTACHMENT, new AttachmentChange(attachmentTarget.fabric_getSyncTargetInfo(), ModAttachments.PERKY_ATTACHMENT.PERKY_ATTACHMENT, playerAttachment));
			//? } else if =26.1 {
			/*attachmentTarget.fabric_syncChange(ModAttachments.PERKY_ATTACHMENT.PERKY_ATTACHMENT, AttachmentChange.create(attachmentTarget.fabric_getSyncTargetInfo(), ModAttachments.PERKY_ATTACHMENT.PERKY_ATTACHMENT, playerAttachment, attachmentTarget.fabric_getRegistryAccess()));
			*///? } else if =1.21.11 {
			/*attachmentTarget.fabric_syncChange(ModAttachments.PERKY_ATTACHMENT.PERKY_ATTACHMENT, AttachmentChange.create(attachmentTarget.fabric_getSyncTargetInfo(), ModAttachments.PERKY_ATTACHMENT.PERKY_ATTACHMENT, playerAttachment, attachmentTarget.fabric_getDynamicRegistryManager()));
			 *///? } else if >1.21.1 {
			/*attachmentTarget.fabric_syncChange(ModAttachments.PERKY_ATTACHMENT.PERKY_ATTACHMENT, new AttachmentSyncPayloadS2C(List.of(AttachmentChange.create(attachmentTarget.fabric_getSyncTargetInfo(), ModAttachments.PERKY_ATTACHMENT.PERKY_ATTACHMENT, playerAttachment, attachmentTarget.fabric_getDynamicRegistryManager()))));
			 *///? }
			playerAttachment.networkedTo.clear();
			return true;
		}
	}

	public static List<PerkyItem> getAttachments(LivingEntity livingEntity) {
		if (livingEntity == null) return List.of();
		if (ModAttachments.PERKY_ATTACHMENT.has(livingEntity)) {
			return ModAttachments.PERKY_ATTACHMENT.get(livingEntity).appliedItems;
		}
		return List.of();
	}
}
