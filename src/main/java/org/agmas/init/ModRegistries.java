package org.agmas.init;

import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import org.agmas.attachment.PerkyItem;
import org.agmas.porting.PPRegistrar;

public class ModRegistries {
    public static final ResourceKey<Registry<PerkyItem>> PERKY_ITEM_KEY = PPRegistrar.createRegistryKey("perky_item");
    public static final Registry<PerkyItem> PERKY_ITEM =
            //? if <26.1 {
            FabricRegistryBuilder.createSimple(PERKY_ITEM_KEY)
            //? } else {
            /*
            FabricRegistryBuilder.create(PERKY_ITEM_KEY)*/
            //? }
            .buildAndRegister();

    public static void init() {

    }

}
