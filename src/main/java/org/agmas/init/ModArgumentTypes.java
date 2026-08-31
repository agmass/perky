package org.agmas.init;

import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import org.agmas.command.PerkyItemArgument;

public class ModArgumentTypes {
    public static void init() {
        ArgumentTypeRegistry.registerArgumentType(PerkyItemArgument.identifier.id, PerkyItemArgument.class, SingletonArgumentInfo.contextFree(PerkyItemArgument::new));
    }
}
