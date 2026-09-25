package org.agmas.perks;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import org.agmas.attachment.PerkyItem;

public class ShorterVanillaNegativeEffectsPerk extends PerkyItem {


    public ShorterVanillaNegativeEffectsPerk(int worth) {
        super(2);
    }

    @Override
    public float getPotionTimeMultiplier(LivingEntity livingEntity, MobEffectInstance effect) {
        if (
                //? if >1.20.4
                !effect.getEffect().unwrapKey().isEmpty() &&
                        !effect.getEffect()
                                //? if >1.20.4
                                .value()
                                .isBeneficial()) {

            if (
                    //? if >1.20.4 {
                    effect.getEffect().unwrapKey().get()
                    //? if >=1.21.11
                    .identifier()
                    //? if <1.21.11
                    //.location()
                    .getNamespace().equalsIgnoreCase("minecraft")
                    //? } else {
                    /*BuiltInRegistries.MOB_EFFECT.getKey(effect.getEffect()).getNamespace().equalsIgnoreCase("minecraft")
                    *///? }
            ) {
                return 0.1f;
            }
        }
        return 1;
    }

}
