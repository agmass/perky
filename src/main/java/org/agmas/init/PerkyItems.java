package org.agmas.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import org.agmas.attachment.PerkyItem;
import org.agmas.bindings.NoArmBinding;
import org.agmas.porting.PPIdentifier;

public class PerkyItems {
    public static void init() {
        Registry.register(ModRegistries.PERKY_ITEM, PPIdentifier.of("test_perk").id, new NoArmBinding());
    }
}
