package org.agmas.client.datagen;

//? if >=26.1 {
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
 //? } else {
/*import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
*///? }
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.ResourceKey;
import org.agmas.attachment.PerkyItem;
import org.agmas.init.*;

import java.util.concurrent.CompletableFuture;

public class PerkyLangDatagen extends FabricLanguageProvider {



    //? if >=26.1 {
    public PerkyLangDatagen(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }
     //? } else if >=1.21.1 {
    /*public PerkyLangDatagen(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }
    *///? } else {
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

        translationBuilder.add(ModBlocks.CONSOLE, "Console");
        translationBuilder.add(ModBlocks.GRIDDIAN_TILES, "Griddian Tiles");
        translationBuilder.add(ModBlocks.KNUCKLIUM_BLOCK, "Knucklium Block");

        translationBuilder.add(ModItems.BLOOD_BOTTLE, "Blood Bottle");
        translationBuilder.add(ModItems.SYRINGE, "Syringe");

        translationBuilder.add("perky.perk.perky.fishermans_charm", "Fisherman's Charm");
        translationBuilder.add("perky.perk.perky.fishermans_charm.desc", "Increases Luck");

        translationBuilder.add("perky.perk.perky.passive_absorption", "Passive Absorption");
        translationBuilder.add("perky.perk.perky.passive_absorption.desc", "When out of combat for 30+ seconds,\ngain 4 hearts of Absorption");

        translationBuilder.add("perky.perk.perky.smooth_landing", "Smooth Landing");
        translationBuilder.add("perky.perk.perky.smooth_landing.desc", "Increase safe fall distance by 5 blocks");

        translationBuilder.add("perky.perk.perky.builders_pockets", "Builder's Pockets");
        translationBuilder.add("perky.perk.perky.builders_pockets.desc", "Blocks can now stack up to 99.");

        translationBuilder.add("perky.binding.perky.stackless", "Stackless");
        translationBuilder.add("perky.binding.perky.stackless.desc", "Non-block items can only stack up to half of their usual stack size.");

        translationBuilder.add("perky.perk.perky.mob_boss", "Mob Boss");
        translationBuilder.add("perky.perk.perky.mob_boss.desc", "Hostile mobs no longer target you when unprovoked.");

        translationBuilder.add("perky.perk.perky.lighter", "Lighter");
        translationBuilder.add("perky.perk.perky.lighter.desc", "Makes fire deal no damage and allows the use of Riptide tridents on fire.");

        translationBuilder.add("perky.perk.perky.extent", "Extent");
        translationBuilder.add("perky.perk.perky.extent.desc", "Makes potions last longer.");

        translationBuilder.add("perky.binding.perky.flammable", "Flammable");
        translationBuilder.add("perky.binding.perky.flammable.desc", "Increase time set on fire by 100%");

        translationBuilder.add("perky.perk.perky.sponge", "Sponge");
        //? if >=1.21.1
        translationBuilder.add("perky.perk.perky.sponge.desc", "Hunger and Saturation are refilled when in water; but size increases.");
        //? if <1.21.1
        //translationBuilder.add("perky.perk.perky.sponge.desc", "Hunger is slowly refilled when in water.");


        translationBuilder.add("perky.perk.perky.cure", "Cure");
        translationBuilder.add("perky.perk.perky.cure.desc", "Vanilla Debuff effects are significantly shortened.");

        translationBuilder.add("perky.binding.perky.singlehanded", "Singlehanded");
        translationBuilder.add("perky.binding.perky.singlehanded.desc", "Removes your off-hand, rendering it unusable.");

        translationBuilder.add("perky.binding.perky.witchs_curse", "Witch's Curse");
        translationBuilder.add("perky.binding.perky.witchs_curse.desc", "Potions last a shorter amount of time.");

        translationBuilder.add("perky.binding.perky.cannibal", "Cannibal");
        translationBuilder.add("perky.binding.perky.cannibal.desc", "You can only eat meat.");

        translationBuilder.add("perky.binding.perky.picky_eater", "Picky Eater");
        translationBuilder.add("perky.binding.perky.picky_eater.desc", "Eating raw foods give debuffs.");

        translationBuilder.add("perky.binding.perky.reputable", "Reputable");
        translationBuilder.add("perky.binding.perky.reputable.desc", "Villagers have increased prices and iron golems attack you.");

        translationBuilder.add("perky.binding.perky.unfortunate", "Unfortunate");
        translationBuilder.add("perky.binding.perky.unfortunate.desc", "Chests and mobs provide less loot.");

        translationBuilder.add("perky.perk.perky.empty_stomach", "Empty Stomach");
        translationBuilder.add("perky.perk.perky.empty_stomach.desc", "Food is always edible.");

        translationBuilder.add("perky.perk.perky.slimey", "Slimey");
        //? if >=1.21.1
        translationBuilder.add("perky.perk.perky.slimey.desc", "Become smaller and faster when less items are in your inventory.");
        //? if <1.21.1
        //translationBuilder.add("perky.perk.perky.slimey.desc", "Become faster when less items are in your inventory.");

        translationBuilder.add("perky.perk.perky.dexterity", "Dexterity");
        translationBuilder.add("perky.perk.perky.dexterity.desc", "You can reach blocks from further.");

        translationBuilder.add("perky.perk.perky.grime_filled", "Grime-filled");
        translationBuilder.add("perky.perk.perky.grime_filled.desc", "Mobs and Loot Chests have more loot.");

        translationBuilder.add("perky.perk.perky.midas_touch", "Midas Touch");
        translationBuilder.add("perky.perk.perky.midas_touch.desc", "Apples and Carrots act like their Golden counterpart.");

        translationBuilder.add("perky.binding.perky.midas_touch", "Nyctophobia");
        translationBuilder.add("perky.binding.perky.midas_touch.desc", "Take damage when in the dark.");

        translationBuilder.add("death.attack.nyctophobia", "%s stared into the shadows");
        translationBuilder.add("death.attack.nyctophobia.player", "%s stared into the shadows of %s");

        translationBuilder.add("perky.bloodstream", "Bloodstream");
        translationBuilder.add("perky.no_bottle", "Requires Glass Bottle");
        translationBuilder.add("perky.worth", "Worth %s %ss.");
        translationBuilder.add("perky.not_enough_worth", "%s binding points required.");
    }

}
