package org.agmas.client.mixin;

import net.minecraft.client.Minecraft;

//? if <=1.19 {
//? } else if <26.1 {
import net.minecraft.client.gui.GuiGraphics;
 //? } else {
/*import net.minecraft.client.gui.GuiGraphicsExtractor;
*///? }
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import org.agmas.Perky;
import org.agmas.attachment.PerkyItem;
import org.agmas.client.screen.PerkyScreen;
import org.agmas.init.ModRegistries;
import org.agmas.porting.PPIdentifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

//? >=1.21.11 {
@Mixin(CreativeModeInventoryScreen.class)
public class CreativeScreenMixin {

    //? if <=1.19 {
    /*@Inject(method = "render", at = @At("TAIL"))
    public void renderPerks(PoseStack guiGraphics, int j, int f, float a, CallbackInfo ci) {
    *///? } else if <26.1 {
    @Inject(method = "render", at = @At("TAIL"))
    public void renderPerks(GuiGraphics guiGraphics, int j, int f, float a, CallbackInfo ci) {
    //? } else {
    /*@Inject(method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IIF)V", at = @At("TAIL"))
    public void renderPerks(GuiGraphicsExtractor guiGraphics, int j, int f, float a, CallbackInfo ci) {
    *///? }
    int ie = 0;
        int totalWidth = 0;
        for (PerkyItem attachment : Perky.getAttachments(Minecraft.getInstance().player)) {
            Optional<ResourceKey<PerkyItem>> optionalResourceKey = ModRegistries.PERKY_ITEM.getResourceKey(attachment);
            if (optionalResourceKey.isEmpty()) continue;
            totalWidth += 20;
        }
        int x = (((Screen)(Object)this).width)/2 - (totalWidth)/2;
        for (PerkyItem attachment : Perky.getAttachments(Minecraft.getInstance().player)) {
            Optional<ResourceKey<PerkyItem>> optionalResourceKey = ModRegistries.PERKY_ITEM.getResourceKey(attachment);
            if (optionalResourceKey.isEmpty()) continue;
            ResourceKey<PerkyItem> resourceKey = optionalResourceKey.get();
            PerkyScreen.simpleBlit(
                    PPIdentifier.parse(PPIdentifier.of(resourceKey).id.getNamespace() +":textures/" + (attachment.worth > 0 ? "perks" : "bindings") + "/" + PPIdentifier.of(resourceKey).id.getPath() + ".png"),
                    guiGraphics,
                    x + (20 * ie),
                    6,
                    x + (20 * ie) + 16,
                    6 +16,
                    16,16
            );

            if (j > x + (20 * ie) && j < x + (20 * ie) + 16) {
                if (f > 6 && f < 22) {
                    int finalIe = ie;
                    //? if <=1.19
                    //runnable = ()-> {
                    PerkyScreen.renderPerkyTooltip(
                            //? if <=1.19
                            //(EffectRenderingInventoryScreen) (Object) this,
                            guiGraphics, attachment, PPIdentifier.of(resourceKey),
                            x + (20 * finalIe),
                            6,
                            x + (20 * finalIe) + 16,
                            6 + 16,
                            j,
                            f);
                    //? if <=1.19
                    //};
                }
            }

            ie++;
        }
    }
}

//? } else {
/*@Mixin(Player.class)
public class CreativeScreenMixin {}

*///? }