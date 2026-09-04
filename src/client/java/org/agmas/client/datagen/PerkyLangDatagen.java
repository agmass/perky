package org.agmas.client.datagen;

//? if >=26.1 {
/*import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
 *///? } else {
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
//? }
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import org.agmas.attachment.PerkyItem;
import org.agmas.init.*;

import java.util.concurrent.CompletableFuture;

public class PerkyLangDatagen extends FabricLanguageProvider {



    //? if >=26.1 {
    /*public PerkyLangDatagen(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }
     *///? } else if >=1.21.1 {
    public PerkyLangDatagen(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }
    //? } else {
    /*public PerkyLangDatagen(FabricDataOutput dataOutput) {
        super(dataOutput);
    }
    *///? }

    //? if >=1.21.1 {
    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
    //? } else {
    /*public void generateTranslations(TranslationBuilder translationBuilder) {
    *///? }

        translationBuilder.add(PerkyCommands.PERK, "perk");
        translationBuilder.add(PerkyCommands.BINDING, "binding");

        translationBuilder.add(PerkyCommands.ADD_SUCCESS, "Gave %1$s %3$s to %2$s");
        translationBuilder.add(PerkyCommands.ADD_FAIL_ALREADY_HAS_ITEM, "%2$s already has %1$s %3$s");
        translationBuilder.add(PerkyCommands.REMOVE_SUCCESS, "Removed %1$s %3$s from %2$s");
        translationBuilder.add(PerkyCommands.REMOVE_ALREADY_HAS_ITEM, "%2$s does not have %1$s %3$s");
        translationBuilder.add(PerkyCommands.LIST, "%s's perk(s) and binding(s): %s");

        translationBuilder.add("perky.perk.perky.fishermans_charm", "Fisherman's Charm");
        translationBuilder.add("perky.perk.perky.fishermans_charm.desc", "Increases Luck");

        translationBuilder.add("perky.perk.perky.passive_absorption", "Passive Absorption");
        translationBuilder.add("perky.perk.perky.passive_absorption.desc", "When out of combat for 30+ seconds, gain 4 hearts of Absorption");

        translationBuilder.add("perky.perk.perky.smooth_landing", "Smooth Landing");
        translationBuilder.add("perky.perk.perky.smooth_landing.desc", "Increase safe fall distance by 2 blocks");

        translationBuilder.add("perky.perk.perky.flammable", "Flammable");
        translationBuilder.add("perky.perk.perky.flammable.desc", "Increase time set on fire by 100%");

        translationBuilder.add("perky.perk.perky.singlehanded", "Singlehanded");
        translationBuilder.add("perky.perk.perky.singlehanded.desc", "Removes your off-hand, rendering it unusable.");
    }

}
