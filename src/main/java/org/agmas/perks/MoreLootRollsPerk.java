package org.agmas.perks;

import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.loot.LootContext;
import org.agmas.attachment.PerkyItem;
import org.agmas.init.ModTags;
import org.agmas.porting.PPIdentifier;

public class MoreLootRollsPerk extends PerkyItem {

    float lootRollMultiplier = 0;

    public MoreLootRollsPerk(int worth, float lootRollMultiplier) {
        super(worth);
        this.lootRollMultiplier = lootRollMultiplier;
    }

    @Override
    public int bonusRolls(LootContext params, int originalRolls) {
        return (int) (originalRolls*lootRollMultiplier);
    }

    @Override
    public TagKey<PerkyItem> exclusive() {
        return ModTags.INCOMPATIBLE_FORTUNE;
    }
}
