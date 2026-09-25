package org.agmas.bindings;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import org.agmas.attachment.PerkyItem;
import org.agmas.init.ModDamageTypes;

public class NyctophobiaBinding extends PerkyItem {
    public NyctophobiaBinding() {
        super(-1);
    }

    @Override
    public void tick(LivingEntity livingEntity) {
        if (livingEntity.level
                //? >1.19
                ()
                .getRawBrightness(livingEntity.blockPosition(),0) < 1) {
            livingEntity.
                    //?<1.21.11
                    //hurt(
                    //?>=1.21.11
                            hurtServer

                    //?>=1.21.11
                            ((ServerLevel) livingEntity.level(),
                                    livingEntity.damageSources().source(ModDamageTypes.NYCTOPHOBIA), 2
                            );
        }
        super.tick(livingEntity);
    }
}
