package org.agmas.porting;

//? if >=1.21.11 {
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.resources.Identifier;
//? } else {
/*import net.minecraft.resources.ResourceLocation;
 *///? }
import org.agmas.Perky;

public class PPIdentifier {
    //? if >=1.21.11 {
    public Identifier id;
    //? } else {
    /*public ResourceLocation id;
     *///? }


    //? if >=1.21.11 {
    public PPIdentifier(Identifier id) {
        //? } else {
        /*public PPIdentifier(ResourceLocation id) {
         *///? }
        this.id = id;
    }

    //? if >=1.21.11 {
    public static PPIdentifier of(String path) {
        return new PPIdentifier(Identifier.fromNamespaceAndPath(Perky.MOD_ID, path));
    }
    public static PPIdentifier parse(String path) {
        return new PPIdentifier(Identifier.parse(path));
    }
    public static PPIdentifier ofVanilla(String path) {
        return new PPIdentifier(Identifier.fromNamespaceAndPath("minecraft", path));
    }
    public static PPIdentifier read(StringReader stringReader) throws CommandSyntaxException {
        return new PPIdentifier(Identifier.read(stringReader));
    }
    //? } else if >1.20.4 {
    /*public static PPIdentifier of(String path) {
        return new PPIdentifier(ResourceLocation.fromNamespaceAndPath(Perky.MOD_ID, path));
    }
    public static PPIdentifier parse(String path) {
        return new PPIdentifier(ResourceLocation.parse(path));
    }
    public static PPIdentifier ofVanilla(String path) {
        return new PPIdentifier(ResourceLocation.fromNamespaceAndPath("minecraft", path));
    }
    public static PPIdentifier read(StringReader stringReader) throws CommandSyntaxException {
        return new PPIdentifier(ResourceLocation.read(stringReader));
    }
    *///? } else {
    
   /*public static PPIdentifier of(String path) {
        return new PPIdentifier(ResourceLocation.tryParse(Perky.MOD_ID + ":" + path));
    }
    public static PPIdentifier parse(String path) {
        return new PPIdentifier(ResourceLocation.tryParse(path));
    }
    public static PPIdentifier ofVanilla(String path) {
        return new PPIdentifier(ResourceLocation.tryParse("minecraft:" + path));
    }
    public static PPIdentifier read(StringReader stringReader) throws CommandSyntaxException {
        return new PPIdentifier(ResourceLocation.read(stringReader));
    }
    *///? }
}
