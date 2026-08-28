package org.agmas.command;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
//? if >=1.21.11 {
import net.minecraft.resources.Identifier;
//? } else {
/*
import net.minecraft.resources.ResourceLocation;
 *///? }
import org.agmas.attachment.PerkyItem;
import org.agmas.init.ModRegistries;
import org.agmas.porting.PPIdentifier;

import java.util.concurrent.CompletableFuture;

public class PerkyItemArgument implements ArgumentType<PerkyItem> {

    public PerkyItemArgument() {
    }

    public static PerkyItemArgument perkyItem() {
        return new PerkyItemArgument();
    }


    @Override
    public PerkyItem parse(StringReader reader) throws CommandSyntaxException {
        return getItem(reader.readString());
    }

    @Override
    public <S> PerkyItem parse(StringReader reader, S source) throws CommandSyntaxException {
        return getItem(reader.readString());
    }

    PerkyItem getItem(String string) {
        //? if >=1.21.11 {
        return ModRegistries.PERKY_ITEM.getValue(PPIdentifier.parse(string).id);
         //? } else {
        /*//? if >=1.21.4 {
        return ModRegistries.PERKY_ITEM.get(PPIdentifier.parse(string).id).get().value();
        //? } else {
        /^return ModRegistries.PERKY_ITEM.get(PPIdentifier.parse(string).id);
        ^///? }
        *///? }
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        //? if >=1.21.11 {
        for (Identifier identifier : ModRegistries.PERKY_ITEM.keySet()) {
        //? } else {
        /*for (ResourceLocation identifier : ModRegistries.PERKY_ITEM.keySet()) {
         *///? }
            builder.suggest(identifier.toString());
        }
        return builder.buildFuture();
    }
}
