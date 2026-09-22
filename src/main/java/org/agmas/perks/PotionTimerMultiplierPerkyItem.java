package org.agmas.perks;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.agmas.attachment.PerkyItem;

import java.util.List;

public class PotionTimerMultiplierPerkyItem extends PerkyItem {

    public float potionTimeMultiplier = 1;

    public PotionTimerMultiplierPerkyItem(int worth, float potionTimeMultiplier) {
        super(worth);
        this.potionTimeMultiplier = potionTimeMultiplier;
    }

    @Override
    public float getPotionTimeMultiplier(LivingEntity livingEntity, MobEffectInstance effect) {
        return potionTimeMultiplier;
    }
}
