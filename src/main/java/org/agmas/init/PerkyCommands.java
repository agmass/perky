package org.agmas.init;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
//? if >=1.21.11 {
/*import net.minecraft.server.permissions.Permissions;
*///?}
import net.minecraft.world.entity.player.Player;
import org.agmas.Perky;
import org.agmas.attachment.PerkyItem;
import org.agmas.command.PerkyItemArgument;

public class PerkyCommands {

    public static String ADD_SUCCESS = "perky.commands.add.success";
    public static String ADD_FAIL_ALREADY_HAS_ITEM = "perky.commands.add.already_has_item";
    public static String REMOVE_SUCCESS = "perky.commands.remove.success";
    public static String REMOVE_ALREADY_HAS_ITEM = "perky.commands.remove.does_not_have_item";
    public static String LIST = "perky.commands.list";

    public static String PERK = "perky.perk";
    public static String BINDING = "perky.binding";

    public static Component PERK_COMP = Component.translatable("perky.perk");
    public static Component BINDING_COMP = Component.translatable("perky.binding");

    public static void init() {
        CommandRegistrationCallback.EVENT.register((l, commandBuildContext, b)->{
            l.register(
                    Commands.literal("perky")
                            //? if >=1.21.11 {
                            /*.requires(p->p.permissions().hasPermission(Permissions.COMMANDS_ADMIN))
                            *///? } else {
                            .requires(p->p.hasPermission(2))
                             //? }
                            .then(Commands.literal("add")
                                    .then(Commands.argument("player", EntityArgument.player())
                                            .then(Commands.argument("perkOrBinding", PerkyItemArgument.perkyItem())
                                                    .executes((c)->{
                                                        Player p = EntityArgument.getPlayer(c, "player");
                                                        PerkyItem perkyItem = c.getArgument("perkOrBinding", PerkyItem.class);
                                                        if (perkyItem != null) {
                                                            if (Perky.addAttachment(p, perkyItem)) {
                                                                c.getSource().sendSystemMessage(commandTranslatable(ADD_SUCCESS, perkyItem, p));
                                                            } else {
                                                                c.getSource().sendFailure(commandTranslatable(ADD_FAIL_ALREADY_HAS_ITEM, perkyItem, p));
                                                                return 0;
                                                            }
                                                        }
                                                        return 0;
                                                    }))))
                            .then(Commands.literal("remove")
                                    .then(Commands.argument("player", EntityArgument.player())
                                            .then(Commands.argument("perkOrBinding", PerkyItemArgument.perkyItem())
                                                    .executes((c)->{
                                                        Player p = EntityArgument.getPlayer(c, "player");
                                                        PerkyItem perkyItem = c.getArgument("perkOrBinding", PerkyItem.class);
                                                        if (perkyItem != null) {
                                                            if (Perky.removeAttachment(p, perkyItem)) {
                                                                c.getSource().sendSystemMessage(commandTranslatable(REMOVE_SUCCESS, perkyItem, p));
                                                                return 1;
                                                            } else {
                                                                c.getSource().sendFailure(commandTranslatable(REMOVE_ALREADY_HAS_ITEM, perkyItem, p));
                                                                return 0;
                                                            }
                                                        }
                                                        return 0;
                                                    }))))
                            .then(Commands.literal("list")
                                    .then(Commands.argument("player", EntityArgument.player())
                                                .executes((c)->{
                                                    Player p = EntityArgument.getPlayer(c, "player");
                                                    String listString = "";

                                                    for (PerkyItem attachment : Perky.getAttachments(p)) {
                                                        listString += ModRegistries.PERKY_ITEM.getKey(attachment) + ", ";
                                                    }
                                                    listString = listString.substring(0,listString.length()-2);
                                                    c.getSource().sendSystemMessage(Component.translatable(LIST, p.getName(), listString));
                                                    return 1;
                                                })))
                            );
        });
    }

    public static Component commandTranslatable(String translatable, PerkyItem perkyItem, Player player) {
        return Component.translatable(translatable,
                perkyItem.worth > 0 ? PERK_COMP : BINDING_COMP,
                player.getName(),
                ModRegistries.PERKY_ITEM.getKey(perkyItem).toString());
    }
}
