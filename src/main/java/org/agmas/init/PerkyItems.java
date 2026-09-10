package org.agmas.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.*;
import org.agmas.attachment.PerkyItem;
import org.agmas.bindings.NoArmBinding;
import org.agmas.perks.FireWaterSwapPerk;
import org.agmas.perks.NoTargetPerk;
import org.agmas.perks.PassiveAbsorptionPerk;
import org.agmas.perks.PotionTimerMultiplierPerkyItem;
import org.agmas.porting.PPIdentifier;
import org.w3c.dom.Attr;

import java.util.List;

public class PerkyItems {

    public static PPIdentifier FLAMMABLE_ID = PPIdentifier.of("flammable");
    public static PPIdentifier MAX_HEALTH_NEGATIVE_ID = PPIdentifier.of("max_health_negative");
    public static PPIdentifier SMOOTH_LANDING_ID = PPIdentifier.of("smooth_landing");
    public static PPIdentifier MOB_BOSS_CHARM_ID = PPIdentifier.of("mob_boss");

    public static PerkyItem SINGLEHANDED;

    public static void init() {

        //? if >=1.21.1
        AttributeModifier.Operation add = AttributeModifier.Operation.ADD_VALUE;

        //? if <1.21.1
        //AttributeModifier.Operation add = AttributeModifier.Operation.ADDITION;

        SINGLEHANDED = Registry.register(ModRegistries.PERKY_ITEM, PPIdentifier.of("singlehanded").id, new NoArmBinding());
        Registry.register(ModRegistries.PERKY_ITEM, PPIdentifier.of("passive_absorption").id, new PassiveAbsorptionPerk());
        Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("max_health_negative").id,
                new PerkyItem(-2, List.of(
                        new PerkyItem.AttributePair(attribute(Attributes.MAX_HEALTH), modifier(MAX_HEALTH_NEGATIVE_ID, -2, add))
                )));
        Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("lighter").id,
                new FireWaterSwapPerk());

        Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("mob_boss").id,
                new NoTargetPerk());

        Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("extent").id,
                new PotionTimerMultiplierPerkyItem(2, 2));

        Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("witchs_curse").id,
                new PotionTimerMultiplierPerkyItem(2, 0.5f));


        //? if >1.21.1 {
        
            Registry.register(ModRegistries.PERKY_ITEM,
                    PPIdentifier.of("flammable").id,
                    new PerkyItem(-1, List.of(
                            new PerkyItem.AttributePair(Attributes.BURNING_TIME, new AttributeModifier(FLAMMABLE_ID.id, 1, add))
                    )));
            Registry.register(ModRegistries.PERKY_ITEM,
                    PPIdentifier.of("smooth_landing").id,
                    new PerkyItem(1, List.of(
                            new PerkyItem.AttributePair(Attributes.SAFE_FALL_DISTANCE, new AttributeModifier(SMOOTH_LANDING_ID.id, 1, add))
                    )));
         //? }
    }



    //? if <1.21.1 {
    /*static Holder<Attribute> attribute(Attribute attribute) {
        return Holder.direct(attribute);
    }
    *///? } else {
    static Holder<Attribute> attribute(Holder<Attribute> attribute) {
        return attribute;
    }
    //? }


    static AttributeModifier modifier(PPIdentifier id, float amount, AttributeModifier.Operation operation) {
        //? if >=1.21.1
        return new AttributeModifier(id.id,amount,operation);
        //? if <1.21.1
        //return new AttributeModifier(id.id.toString(),amount,operation);
    }
}
