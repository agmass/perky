package org.agmas;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
//? if >=1.21.11 {
import net.minecraft.server.permissions.Permissions;
//? }
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.agmas.attachment.PerkyItem;
import org.agmas.command.PerkyItemArgument;
import org.agmas.init.ModAttachments;
import org.agmas.init.ModBlocks;
import org.agmas.init.ModRegistries;
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

		CommandRegistrationCallback.EVENT.register((l,commandBuildContext,b)->{
			l.register(
					Commands.literal("addPerkOrBinding")
							//? if >=1.21.11 {
							.requires(p->p.permissions().hasPermission(Permissions.COMMANDS_ADMIN))
							//? } else {
							/*.requires(p->p.hasPermission(2))
							 *///? }
							.then(Commands.argument("player", EntityArgument.player()))
							.then(Commands.argument("perkOrBinding", PerkyItemArgument.perkyItem()))
							.executes((c)->{
								Player p = EntityArgument.getPlayer(c, "player");
								PerkyItem perkyItem = c.getArgument("perkOrBinding", PerkyItem.class);
								if (perkyItem != null) {

								}
								return 0;
							})
			);
		});
	}

	public static List<PerkyItem> getAttachments(LivingEntity livingEntity) {
		if (livingEntity.hasAttached(ModAttachments.PERKY_ATTACHMENT)) {
			return livingEntity.getAttached(ModAttachments.PERKY_ATTACHMENT).appliedItems;
		}
		return List.of();
	}
}
