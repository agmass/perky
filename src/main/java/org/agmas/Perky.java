package org.agmas;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.impl.attachment.AttachmentTargetImpl;
import net.fabricmc.fabric.impl.attachment.sync.AttachmentChange;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
//? if >=1.21.11 {
import net.minecraft.server.permissions.Permissions;
//? }
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.agmas.attachment.PerkyItem;
import org.agmas.attachment.PerkyPlayerAttachment;
import org.agmas.command.PerkyItemArgument;
import org.agmas.init.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class Perky implements ModInitializer {
	public static final String MOD_ID = "perky";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlocks.init();
		ModAttachments.init();
		ModRegistries.init();
		PerkyItems.init();
		ModArgumentTypes.init();

		CommandRegistrationCallback.EVENT.register((l,commandBuildContext,b)->{
			l.register(
					Commands.literal("perky")
							//? if >=1.21.11 {
							.requires(p->p.permissions().hasPermission(Permissions.COMMANDS_ADMIN))
							//? } else {
							/*.requires(p->p.hasPermission(2))
							 *///? }
							.then(Commands.literal("add")
								.then(Commands.argument("player", EntityArgument.player())
								.then(Commands.argument("perkOrBinding", PerkyItemArgument.perkyItem())
								.executes((c)->{
									Player p = EntityArgument.getPlayer(c, "player");
									PerkyItem perkyItem = c.getArgument("perkOrBinding", PerkyItem.class);
									if (perkyItem != null) {
										if (addAttachment(p, perkyItem)) {
											c.getSource().sendSystemMessage(Component.translatable("perky.commands.add.success", perkyItem.toString()));
										} else {
											c.getSource().sendFailure(Component.translatable("perky.commands.add.already_has_item", perkyItem.toString()));
											return 0;
										}
									}
									return 0;
								})))
							)
			);
		});
	}



	public static boolean addAttachment(LivingEntity livingEntity, PerkyItem item) {
		PerkyPlayerAttachment playerAttachment = livingEntity.getAttachedOrElse(ModAttachments.PERKY_ATTACHMENT, new PerkyPlayerAttachment());
		if (playerAttachment.appliedItems.contains(item)) {
			return false;
		} else {
			playerAttachment.appliedItems.add(item);
			livingEntity.setAttached(ModAttachments.PERKY_ATTACHMENT,playerAttachment);

			AttachmentTargetImpl attachmentTarget = ((AttachmentTargetImpl) livingEntity);
			attachmentTarget.fabric_syncChange(ModAttachments.PERKY_ATTACHMENT, AttachmentChange.create(attachmentTarget.fabric_getSyncTargetInfo(), ModAttachments.PERKY_ATTACHMENT, playerAttachment, attachmentTarget.fabric_getDynamicRegistryManager()));
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
