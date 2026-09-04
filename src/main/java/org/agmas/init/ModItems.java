package org.agmas.init;

//? if >=26.1 {
/*import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
 *///? } else if >1.18 {

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
 //? }
import net.minecraft.core.Registry;
//? if >1.18 {
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
//? }
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.agmas.porting.PPIdentifier;

import java.util.function.Function;

public class ModItems {

    public static final Item BLOOD_BOTTLE = register("blood_bottle",
            Item::new
            , new Item.Properties()
                    .food(
                             //? if >=1.21.1 {
                            new FoodProperties.Builder().alwaysEdible().nutrition(0).saturationModifier(0f).build())
                             //? } else {
                            /*new FoodProperties.Builder().alwaysEat().nutrition(0).saturationMod(0f).build())
            *///? }
    );

    public static final Item SYRINGE = register("syringe", Item::new, new Item.Properties());


    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {

        //? if >1.18 {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, PPIdentifier.of(name).id);
         //? }

        T item = itemFactory.apply(settings
                //? if >=1.21.4 {
                .setId(itemKey)
                 //? }
        );


        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;

    }

    public static void init() {

        //? if >=26.1 {
        /*CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
        *///? } else {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.TOOLS_AND_UTILITIES)
         //? }
        .register((creativeTab) -> {
            creativeTab.accept(BLOOD_BOTTLE);
            creativeTab.accept(SYRINGE);
        });

        //? if >=26.1 {
        /*CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.FOOD_AND_DRINKS)
         *///? } else {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.FOOD_AND_DRINKS)
                //? }
                .register((creativeTab) -> {
                    creativeTab.accept(BLOOD_BOTTLE);
                });
    }
}