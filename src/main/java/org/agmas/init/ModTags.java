package org.agmas.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.Item;
import org.agmas.attachment.PerkyItem;
import org.agmas.porting.PPIdentifier;

public class ModTags {

    public static final TagKey<PerkyItem> INCOMPATIBLE_LIGHTER = TagKey.create(ModRegistries.PERKY_ITEM_KEY, PPIdentifier.of("incompatible_lighter").id);
    public static final TagKey<Item> MEATS = TagKey.create(Registries.ITEM, PPIdentifier.of("meats").id);
    public static final TagKey<Item> RAW_MEATS = TagKey.create(Registries.ITEM, PPIdentifier.of("raw_meats").id);

    public static void init() {}
}
