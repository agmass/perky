package org.agmas.perks;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.agmas.attachment.PerkyItem;

public class FireWaterSwapPerk extends PerkyItem {


    public FireWaterSwapPerk() {
        super(3);
    }

    @Override
    public CancellableResult canRiptide(Player player) {
        return player.isOnFire() ? CancellableResult.SUCCESS : CancellableResult.PASS;
    }

    @Override
    public float modifyDamage(LivingEntity victim, DamageSource source, float v) {
        return source.is(DamageTypeTags.IS_FIRE) ? 0f : -1;
    }
}
