package org.agmas.porting;

//? if >=26.1 {
/*import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
 *///? } else {
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
//? }
//? if >1.20.4 {
//? }
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Function;

public class PPRegistrar {

    public static Block registerBlock(String name, Function<BlockBehaviour.Properties, Block> blockFactory, BlockBehaviour.Properties properties, boolean shouldRegisterItem, ResourceKey<CreativeModeTab> tab) {

        Block block = blockFactory.apply(properties
                //? if >=1.21.4 {
                .setId(keyOfBlock(name))
                 //? }
        );

        if (shouldRegisterItem) {
            registerItem(name, (b)->{return new BlockItem(block, b);}, new Item.Properties()
                    //? if >=1.21.4 {
                    .setId(keyOfItem(name)).useBlockDescriptionPrefix()
                     //? }
            , tab);
        }

        return Registry.register(BuiltInRegistries.BLOCK, PPIdentifier.of(name).id, block);
    }


    public static <T> ResourceKey<Registry<T>> createRegistryKey(String string) {
        return ResourceKey.createRegistryKey(PPIdentifier.of(string).id);
    }

    public static <T extends Item> T registerItem(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings, ResourceKey<CreativeModeTab> tab) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, PPIdentifier.of(name).id);

        T item = itemFactory.apply(settings
                //? if >=1.21.4 {
                .setId(itemKey)
                 //? }
        );

        Registry.register(BuiltInRegistries.ITEM, itemKey, item);

        //? if >=26.1 {
        /*CreativeModeTabEvents.modifyOutputEvent(tab)
        *///? } else {
        ItemGroupEvents.modifyEntriesEvent(tab)
         //? }
        .register((creativeTab) -> {
            creativeTab.accept(item);
        });

        return item;
    }


    //? if >=1.21.4 {
    private static ResourceKey<Block> keyOfBlock(String name) {
        return ResourceKey.create(Registries.BLOCK, PPIdentifier.of(name).id);
    }

    private static ResourceKey<Item> keyOfItem(String name) {
        return ResourceKey.create(Registries.ITEM, PPIdentifier.of(name).id);
    }
    //? }

}
