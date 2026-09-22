package org.agmas.client.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
//? if <=1.19 {
//? } else if <26.1 {
/*import net.minecraft.client.gui.GuiGraphics;
*///? } else {
import net.minecraft.client.gui.GuiGraphicsExtractor;
 //? }
import net.minecraft.client.gui.screens.Screen;
//? if >=1.21.11
import net.minecraft.client.gui.screens.inventory.AbstractRecipeBookScreen;
//? if <1.21.11
//import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.resources.ResourceKey;
import org.agmas.Perky;
import org.agmas.attachment.PerkyItem;
import org.agmas.client.screen.PerkyScreen;
import org.agmas.init.ModRegistries;
import org.agmas.porting.PPIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

//? if <1.21.11
//@Mixin(EffectRenderingInventoryScreen.class)
//? >=1.21.11
@Mixin(AbstractRecipeBookScreen.class)
public class AddToInventoryScreenMixin {
    //? if <=1.19 {
    /*@Inject(method = "render", at = @At("TAIL"))
    public void renderPerks(PoseStack guiGraphics, int j, int f, float a, CallbackInfo ci) {
    *///? } else if <26.1 {
    /*@Inject(method = "render", at = @At("TAIL"))
    public void renderPerks(GuiGraphics guiGraphics, int j, int f, float a, CallbackInfo ci) {
    *///? } else {
    @Inject(method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V", at = @At("TAIL"))
    public void renderPerks(GuiGraphicsExtractor guiGraphics, int j, int f, float a, CallbackInfo ci) {
    //? }
        int ie = 0;
        int totalWidth = 0;
        for (PerkyItem attachment : Perky.getAttachments(Minecraft.getInstance().player)) {
            Optional<ResourceKey<PerkyItem>> optionalResourceKey = ModRegistries.PERKY_ITEM.getResourceKey(attachment);
            if (optionalResourceKey.isEmpty()) continue;
            totalWidth += 20;
        }
        //? if <=1.19
        //Runnable runnable = null;
        int x = (((Screen)(Object)this).width)/2 - (totalWidth)/2;
        for (PerkyItem attachment : Perky.getAttachments(Minecraft.getInstance().player)) {
            Optional<ResourceKey<PerkyItem>> optionalResourceKey = ModRegistries.PERKY_ITEM.getResourceKey(attachment);
            if (optionalResourceKey.isEmpty()) continue;
            ResourceKey<PerkyItem> resourceKey = optionalResourceKey.get();
            PerkyScreen.simpleBlit(
                    PPIdentifier.parse(PPIdentifier.of(resourceKey).id.getNamespace() +":textures/" + (attachment.worth > 0 ? "perks" : "bindings") + "/" + PPIdentifier.of(resourceKey).id.getPath() + ".png"),
                    guiGraphics,
                    x + (20 * ie),
                    16,
                    x + (20 * ie) + 16,
                    16 +16,
                    16,16
            );

            if (j > x + (20 * ie) && j < x + (20 * ie) + 16) {
                if (f > 16 && f < 32) {
                    int finalIe = ie;
                    //? if <=1.19
                    //runnable = ()-> {
                    PerkyScreen.renderPerkyTooltip(
                            //? if <=1.19
                            //(EffectRenderingInventoryScreen) (Object) this,
                            guiGraphics, attachment, PPIdentifier.of(resourceKey),
                            x + (20 * finalIe),
                            16,
                            x + (20 * finalIe) + 16,
                            16 + 16,
                            j,
                            f);
                    //? if <=1.19
                    //};
                }
            }

            ie++;
        }
        //? if <=1.19 {
        /*if (runnable != null)
            runnable.run();
        *///? }
    }
}
