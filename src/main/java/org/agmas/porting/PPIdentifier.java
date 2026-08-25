package org.agmas.porting;

//? if >=1.21.11 {
/*import net.minecraft.resources.Identifier;
*///? } else {
import net.minecraft.resources.ResourceLocation;
 //? }
import org.agmas.Perky;

public class PPIdentifier {
    //? if >=1.21.11 {
    /*public Identifier id;
    *///? } else {
    public ResourceLocation id;
     //? }


    //? if >=1.21.11 {
    /*public PPIdentifier(Identifier id) {
        *///? } else {
        public PPIdentifier(ResourceLocation id) {
         //? }
        this.id = id;
    }

    //? if >=1.21.11 {
    /*public static PPIdentifier of(String path) {
        return new PPIdentifier(Identifier.fromNamespaceAndPath(Perky.MOD_ID, path));
    }
    public static PPIdentifier ofVanilla(String path) {
        return new PPIdentifier(Identifier.fromNamespaceAndPath("minecraft", path));
    }
    *///? } else if >1.20.4 {
    /*public static PPIdentifier of(String path) {
        return new PPIdentifier(ResourceLocation.fromNamespaceAndPath(Perky.MOD_ID, path));
    }
    public static PPIdentifier ofVanilla(String path) {
        return new PPIdentifier(ResourceLocation.fromNamespaceAndPath("minecraft", path));
    }*/
    //? } else {
    
   public static PPIdentifier of(String path) {
        return new PPIdentifier(ResourceLocation.tryParse(Perky.MOD_ID + ":" + path));
    }
    public static PPIdentifier ofVanilla(String path) {
        return new PPIdentifier(ResourceLocation.tryParse("minecraft:" + path));
    }
    //? }
}
