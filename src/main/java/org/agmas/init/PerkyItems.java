package org.agmas.init;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.*;
import org.agmas.attachment.PerkyItem;
import org.agmas.bindings.*;
import org.agmas.perks.*;
import org.agmas.porting.PPIdentifier;
import org.w3c.dom.Attr;

import java.util.List;

public class PerkyItems {

    public static PPIdentifier DEXTERITY_ID = PPIdentifier.of("dexterity");
    public static PPIdentifier FLAMMABLE_ID = PPIdentifier.of("flammable");
    public static PPIdentifier SMOOTH_LANDING_ID = PPIdentifier.of("smooth_landing");

    public static PerkyItem SINGLEHANDED;
    public static PerkyItem FLAMMABLE;


    public static void init() {

        //? if >=1.21.1
        AttributeModifier.Operation add = AttributeModifier.Operation.ADD_VALUE;

        //? if <1.21.1
        //AttributeModifier.Operation add = AttributeModifier.Operation.ADDITION;

        SINGLEHANDED = Registry.register(ModRegistries.PERKY_ITEM, PPIdentifier.of("singlehanded").id, new NoArmBinding());
        Registry.register(ModRegistries.PERKY_ITEM, PPIdentifier.of("passive_absorption").id, new PassiveAbsorptionPerk());
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
                new PotionTimerMultiplierPerkyItem(-2, 0.5f));

        Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("stackless").id,
                new HalfStackSizeBinding());

        Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("cure").id,
                new ShorterVanillaNegativeEffectsPerk(1));

        Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("sponge").id,
                new SpongePerk());

        Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("cannibal").id,
                new CannibalBinding());

        Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("midas_touch").id,
                new MidasTouchPerk());

        Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("picky_eater").id,
                new RottenifierBinding());

        Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("empty_stomach").id,
                new EmptyStomachPerk());

        Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("grime_filled").id,
                new MoreLootRollsPerk());

        Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("slimey").id,
                new SlimeyInventoryPerk());

        Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("nyctophobia").id,
                new NyctophobiaBinding());

        //? if >1.21.1 {
        
            

            Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("builders_pockets").id,
                new MoreBuildingStackPerk());

            Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("dexterity").id,
                new PerkyItem(1, List.of(
                        new PerkyItem.AttributePair(Attributes.BLOCK_INTERACTION_RANGE, new AttributeModifier(DEXTERITY_ID.id, 2, add))
                )));
            FLAMMABLE = Registry.register(ModRegistries.PERKY_ITEM,
                    PPIdentifier.of("flammable").id,
                    new PerkyItem(-1, List.of(
                            new PerkyItem.AttributePair(Attributes.BURNING_TIME, new AttributeModifier(FLAMMABLE_ID.id, 1, add))
                    )));
            Registry.register(ModRegistries.PERKY_ITEM,
                    PPIdentifier.of("smooth_landing").id,
                    new PerkyItem(1, List.of(
                            new PerkyItem.AttributePair(Attributes.SAFE_FALL_DISTANCE, new AttributeModifier(SMOOTH_LANDING_ID.id, 5, add))
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
