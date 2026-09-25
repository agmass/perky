package org.agmas.perks;

import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.loot.LootContext;
import org.agmas.attachment.PerkyItem;
import org.agmas.porting.PPIdentifier;

import java.util.UUID;

public class SlimeyInventoryPerk extends PerkyItem {

    public SlimeyInventoryPerk() {
        super(1);
        staticModifiers = false;
    }

    public static PPIdentifier SCALE_ID = PPIdentifier.of("slimey_scale");
    public static PPIdentifier SPEED_ID = PPIdentifier.of("slimey_speed");
    //? if >1.20.4 {
    AttributePair scaleAttributePair = new AttributePair(Attributes.MOVEMENT_SPEED,
            new AttributeModifier(
                    SCALE_ID.id,
                    0,
                    AttributeModifier.Operation.ADD_VALUE
            )
    );
    //? }
    AttributePair speedAttributePair = new AttributePair(Attributes.MOVEMENT_SPEED,
            new AttributeModifier(
                    //? if >1.20.4
                    SPEED_ID.id
                    //? if <=1.20.4
                    //SPEED_UUID, "slimeyspeed"
                    ,
                    0,
                    //? if >1.20.4
                    AttributeModifier.Operation.ADD_VALUE
                    //? if <=1.20.4
                    //AttributeModifier.Operation.ADDITION
            )
    );
    //? if <=1.20.4
    //public static UUID SPEED_UUID = UUID.fromString("222fe70b-3bef-4526-bfdc-c39462f180bb");

    @Override
    public void tick(LivingEntity livingEntity) {

        if (!(livingEntity instanceof Player))
            return;

        Player p = (Player) livingEntity;

        AttributeModifier.Operation operation =
                //? if >1.20.4
                AttributeModifier.Operation.ADD_VALUE;
                //? if <=1.20.4
                //AttributeModifier.Operation.ADDITION;

        attributes.clear();
        long i =
                //? if >1.21.1
                p.getInventory().getNonEquipmentItems()
                //? if <=1.21.1
                //p.getInventory().items
                        .stream().filter(item -> !item.isEmpty()).count();
        //? if >=1.21.1 {
        if (i < 15) {
            scaleAttributePair = new AttributePair(Attributes.SCALE,
                    new AttributeModifier(SCALE_ID.id
                            //? if <=1.20.4
                            //.toString()
                            ,
                            Mth.lerp(i/15f, -0.3f, 0f),
                            operation
                    )
            );
            if (!attributes.contains(scaleAttributePair))
                attributes.add(scaleAttributePair);
        } else {
            //? if >1.20.4
            livingEntity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(scaleAttributePair.modifier);
            //? if <=1.20.4
            //livingEntity.getAttribute(Attributes.MOVEMENT_SPEED).removePermanentModifier(scaleAttributePair.modifier.getId());
        }
        //? }

        if (i < 15) {

            speedAttributePair = new AttributePair(Attributes.MOVEMENT_SPEED,
                    new AttributeModifier(
                            //? if >1.20.4
                            SPEED_ID.id
                            //? if <=1.20.4
                            //SPEED_UUID, "slimeyspeed"
                            ,
                            Mth.lerp(i/15f, 0.04f, 0f),
                            operation
                    )
            );
            if (!attributes.contains(speedAttributePair))
                attributes.add(speedAttributePair);

        } else {

            //? if <=1.20.4
            //livingEntity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(SPEED_UUID);
            //? if >1.20.4
            livingEntity.getAttribute(Attributes.MOVEMENT_SPEED).removeModifier(speedAttributePair.modifier);
        }

        super.tick(livingEntity);
    }
}
