package org.agmas.perks;

import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.agmas.attachment.PerkyItem;
import org.agmas.init.ModTags;
import org.agmas.porting.PPIdentifier;

public class SpongePerk extends PerkyItem {

    public static PPIdentifier SPONGE_ID = PPIdentifier.of("sponge");
    int timeInWater = 0;
    AttributePair attributePair = null;

    public SpongePerk() {
        super(1);
    }

    @Override
    public void tick(LivingEntity livingEntity) {
        //? if >=1.21.1 {
        if (livingEntity.isInWater()) {
            timeInWater++;
        } else {
            timeInWater--;
        }
        if (livingEntity.isInWater()) {
            if (livingEntity instanceof Player p) {
                if (p.tickCount % 8 == 0) {
                    p.getFoodData().setFoodLevel(p.getFoodData().getFoodLevel() + 1);
                    p.getFoodData().setSaturation(p.getFoodData().getSaturationLevel() + 1);
                }
            }
        }

        if (timeInWater <= 20 && timeInWater > 0) {
            attributePair = new AttributePair(Attributes.SCALE,
                    new AttributeModifier(SPONGE_ID.id,
                            Mth.lerp(timeInWater/20f, 0 ,0.5f),
                            AttributeModifier.Operation.ADD_VALUE
                        )
            );
            attributes.add(attributePair);
            livingEntity.getAttribute(Attributes.SCALE).removeModifier(attributePair.modifier);
            livingEntity.getAttribute(Attributes.SCALE).addPermanentModifier(attributePair.modifier);
        } else if (timeInWater <= 0) {
            timeInWater = 0;

            attributes.remove(attributePair);
            if (livingEntity.getAttribute(Attributes.SCALE).hasModifier(SPONGE_ID.id)) {
                livingEntity.getAttribute(Attributes.SCALE).removeModifier(SPONGE_ID.id);
            }
        }
        if (timeInWater > 60) timeInWater = 60;
        //? } else {
        /*if (livingEntity.isInWater()) {
            if (livingEntity instanceof Player p) {
                if (p.tickCount % 20 == 0)
                    p.getFoodData().setFoodLevel(p.getFoodData().getFoodLevel()+1);
            }
        }
        *///? }
        super.tick(livingEntity);
    }
}
