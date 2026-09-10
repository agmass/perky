package org.agmas.perks;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.agmas.attachment.PerkyItem;

public class NoTargetPerk extends PerkyItem {

    public NoTargetPerk() {
        super(2);
    }

    @Override
    public boolean canAngerMob(LivingEntity holder, Mob mob) {
        if (mob.getLastAttacker() != holder) return false;
        return super.canAngerMob(holder, mob);
    }
}
