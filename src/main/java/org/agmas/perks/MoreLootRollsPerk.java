package org.agmas.perks;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.loot.LootContext;
import org.agmas.attachment.PerkyItem;
import org.agmas.porting.PPIdentifier;

public class MoreLootRollsPerk extends PerkyItem {

    public MoreLootRollsPerk() {
        super(2);
    }

    @Override
    public int bonusRolls(LootContext params, int originalRolls) {
        return originalRolls*2;
    }
}
