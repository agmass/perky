package org.agmas.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import org.agmas.Perky;
import org.agmas.attachment.PerkyItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(NeutralMob.class)
public interface NeutralMobMixin {
    @WrapMethod(method = "isAngryAt")
    default boolean makeDropsEven(LivingEntity livingEntity,
                                  //? if >1.21.1
                                  ServerLevel serverLevel,
                                  Operation<Boolean> original) {
        for (PerkyItem attachment : Perky.getAttachments(livingEntity)) {
            if (attachment == null) continue;
            if (attachment.isAngryAt(livingEntity, ((Mob) this))) {
                return true;
            }
        }
        return original.call(livingEntity
                //? if >1.21.1
                ,serverLevel
        );
    }
}
