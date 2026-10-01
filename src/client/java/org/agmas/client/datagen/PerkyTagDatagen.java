package org.agmas.client.datagen;

//? if >=26.1 {
/*import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
*///? } else {
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
//? }

import net.minecraft.core.registries.BuiltInRegistries;

//? if >=1.21.11
import net.minecraft.data.tags.TagAppender;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.agmas.attachment.PerkyItem;
import org.agmas.init.ModDamageTypes;
import org.agmas.init.ModRegistries;
import org.agmas.init.ModTags;
import org.agmas.init.PerkyItems;
import net.minecraft.core.HolderLookup;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PerkyTagDatagen {

    //? if >=26.1 {
    /*public static final class ItemTags extends FabricTagsProvider.ItemTagsProvider {
     *///?} else {
    public static final class ItemTags extends FabricTagProvider.ItemTagProvider {
        //? }


        //? if >=26.1 {
        /*public ItemTags(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
         *///?} else {
        public ItemTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            //? }
            super(output, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            add(Items.CHICKEN, ModTags.MEATS);
            add(Items.COOKED_CHICKEN, ModTags.MEATS);
            add(Items.PORKCHOP, ModTags.MEATS);
            add(Items.COOKED_PORKCHOP, ModTags.MEATS);
            add(Items.BEEF, ModTags.MEATS);
            add(Items.COOKED_BEEF, ModTags.MEATS);
            add(Items.MUTTON, ModTags.MEATS);
            add(Items.COOKED_MUTTON, ModTags.MEATS);
            add(Items.COD, ModTags.MEATS);
            add(Items.COOKED_COD, ModTags.MEATS);
            add(Items.SALMON, ModTags.MEATS);
            add(Items.COOKED_SALMON, ModTags.MEATS);
            add(Items.RABBIT, ModTags.MEATS);
            add(Items.COOKED_RABBIT, ModTags.MEATS);
            add(Items.ROTTEN_FLESH, ModTags.MEATS);
            add(Items.TROPICAL_FISH, ModTags.MEATS);
            add(Items.PUFFERFISH, ModTags.MEATS);


            add(Items.CHICKEN, ModTags.RAW_MEATS);
            add(Items.PORKCHOP, ModTags.RAW_MEATS);
            add(Items.BEEF, ModTags.RAW_MEATS);
            add(Items.MUTTON, ModTags.RAW_MEATS);
            add(Items.COD, ModTags.RAW_MEATS);
            add(Items.SALMON, ModTags.RAW_MEATS);
            add(Items.RABBIT, ModTags.RAW_MEATS);
        }

        public void add(Item item, TagKey<Item> tag) {
            //? if >=26.2 {
            /*tag(tag)
                    .add(ResourceKey.create(Registries.ITEM, BuiltInRegistries.ITEM.getKey(item)));
            *///? } else if >1.21.4 {
            valueLookupBuilder(tag)
                    .add(item);
            //? } else{
            /*tag(tag)
                    .add(BuiltInRegistries.ITEM.getResourceKey(item).get());
            *///? }
        }

        public void addList(List<Item> list, TagKey<Item> tag) {
            //? if >=26.2 {
            /*TagAppender<Item> itemTagAppender = tag(tag);
             *///? } else  if >1.21.4 {
            TagAppender<Item, Item> itemTagAppender = valueLookupBuilder(tag);
             //? }
            for (Item item : list) {
                //? if >=26.2 {
                /*itemTagAppender.add(ResourceKey.create(Registries.ITEM, BuiltInRegistries.ITEM.getKey(item)));
                 *///? } else  if >1.21.4 {
                itemTagAppender.add(item);
                 //? } else {
                /*tag(tag)
                        .add(BuiltInRegistries.ITEM.getResourceKey(item).get());
                *///? }
            }
        }
    }

    //? if >=26.1 {
    /*public static final class PerkyItemTags extends FabricTagsProvider<PerkyItem> {
     *///?} else {
    public static final class PerkyItemTags extends FabricTagProvider<PerkyItem> {
        //?}

        //? if >=26.1 {
        /*public PerkyItemTags(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
         *///? } else {
        public PerkyItemTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            //? }
            super(output, ModRegistries.PERKY_ITEM_KEY, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            //? if >1.21.1
            add(PerkyItems.FLAMMABLE, ModTags.INCOMPATIBLE_LIGHTER);

            add(PerkyItems.UNFORTUNATE, ModTags.INCOMPATIBLE_FORTUNE);
            add(PerkyItems.GRIME_FILLED, ModTags.INCOMPATIBLE_FORTUNE);

            add(PerkyItems.WITCHS_CURSE, ModTags.INCOMPATIBLE_POTION_TIME);
            add(PerkyItems.EXTENT, ModTags.INCOMPATIBLE_POTION_TIME);
        }

        public void add(PerkyItem item, TagKey<PerkyItem> tag) {
            //? if >=1.21.11 {
            builder(tag)
                    .add(ResourceKey.create(ModRegistries.PERKY_ITEM_KEY, ModRegistries.PERKY_ITEM.getKey(item)));
            //? } else {
            /*getOrCreateRawBuilder(tag)
                    .addElement(ModRegistries.PERKY_ITEM.getKey(item));
            *///? }
        }
    }

    //? if >=26.1 {
    /*public static final class PerkyDamageTypeTags extends FabricTagsProvider<DamageType> {
     *///?} else {
    public static final class PerkyDamageTypeTags extends FabricTagProvider<DamageType> {
        //?}

        //? if >=26.1 {
        /*public PerkyDamageTypeTags(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
         *///? } else {
        public PerkyDamageTypeTags(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
            //? }
            super(output, Registries.DAMAGE_TYPE, registriesFuture);
        }

        @Override
        protected void addTags(HolderLookup.Provider provider) {
            //? if >=1.21.1
            add(ModDamageTypes.NYCTOPHOBIA, DamageTypeTags.NO_KNOCKBACK);
        }

        public void add(ResourceKey<DamageType> item, TagKey<DamageType> tag) {
            //? if >=1.21.11 {
            builder(tag)
                    .add(item);
            //? } else {
            /*getOrCreateRawBuilder(tag)
                    .addElement(item.location());
            *///? }
        }
    }
}