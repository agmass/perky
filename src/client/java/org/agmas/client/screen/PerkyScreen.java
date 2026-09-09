package org.agmas.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
//? if <=1.21.11
//import net.minecraft.client.gui.GuiGraphics;
//? if >=26.1
//import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
//? if >1.21.1
//import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import org.agmas.attachment.BloodstreamAttachment;
import org.agmas.attachment.PerkyItem;
import org.agmas.init.ModAttachments;
import org.agmas.init.ModRegistries;
import org.agmas.network.ChangePerksPayload;
import org.agmas.porting.PPIdentifier;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class PerkyScreen extends Screen {

    private static final PPIdentifier CONTAINER_TEXTURE = PPIdentifier.of("textures/gui/container/console.png");

    public BloodstreamAttachment selectedItems = new BloodstreamAttachment();
    public PerkyItem hovered = null;
    public BloodstreamAttachment availablePerks;
    public PerkyScreen(BloodstreamAttachment availablePerks, Component component) {
        super(component);
        this.availablePerks = availablePerks;
        if (Minecraft.getInstance().player != null) {
            selectedItems =ModAttachments.PERKY_ATTACHMENT.getOrElse(Minecraft.getInstance().player, new BloodstreamAttachment());
        }
    }

    //? if >=26.1 {
    /*
    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float f) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, f);
        int guiGraphicsWidth = guiGraphics.guiWidth();
        int guiGraphicsHeight = guiGraphics.guiHeight();
    *///? } else if >1.19 {
    /*@Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float f) {
        super.render(guiGraphics, mouseX, mouseY, f);
        int guiGraphicsWidth = guiGraphics.guiWidth();
        int guiGraphicsHeight = guiGraphics.guiHeight();
    *///? } else {

    @Override
    public void render(PoseStack guiGraphics, int mouseX, int mouseY, float f) {
        super.render(guiGraphics, mouseX, mouseY, f);
        int guiGraphicsWidth = width;
        int guiGraphicsHeight = height;
    //? }


        int width_ = Math.min(guiGraphicsWidth/2, guiGraphicsHeight);
        int height_ = (int)(width_ *0.736363636f);

        int x = (guiGraphicsWidth/2) - (width_ /2);
        int y = (guiGraphicsHeight/2) - (height_ /2);

        int yPad = (int)((40f/162f)* height_);
        int xPad = (int)((11f/220f)* width_);

        int xPadBinding = (int)((143f/220f)* width_);


        simpleBlit(CONTAINER_TEXTURE, guiGraphics,
                x,y,
                x+ width_,
                y+ height_,
                width_, height_);
        int bindings = 0;
        int perks = 0;

        hovered = null;
        for (Map.Entry<ResourceKey<PerkyItem>, PerkyItem> resourceKeyPerkyItemEntry : ModRegistries.PERKY_ITEM.entrySet()) {

            PerkyItem perkyItem = resourceKeyPerkyItemEntry.getValue();
            if (!availablePerks.appliedItems.contains(perkyItem))
                continue;
            ResourceKey<PerkyItem> resourceKey = resourceKeyPerkyItemEntry.getKey();

            if (perkyItem.worth > 0) {
                int xSep = 18*(perks%5);
                int ySep = 18*((int)Math.floor(perks/5f));

                int perkX = (x+xPad) + xSep;
                int perkY = (y+yPad) + ySep;
                simpleBlit(
                        PPIdentifier.parse(PPIdentifier.of(resourceKey).id.getNamespace() +":textures/perks/" + PPIdentifier.of(resourceKey).id.getPath() + ".png"),
                        guiGraphics,
                        perkX,
                        perkY,
                        perkX + 16,
                        perkY +16,
                        16,16
                );

                if (selectedItems.appliedItems.contains(perkyItem)) {
                    //? if >1.19
                    //guiGraphics.
                            fill(
                                    //? if <=1.19
                                    guiGraphics,
                                    perkX,perkY,perkX+16,perkY+16, new Color(0, 234, 255,75).getRGB());
                }
                if (mouseX > perkX && mouseX < perkX + 16) {
                    if (mouseY > perkY && mouseY < perkY + 16) {
                        hovered  = perkyItem;
                        renderPerkyTooltip(
                                //? if <=1.19
                                this,
                                guiGraphics, perkyItem, PPIdentifier.of(resourceKey),
                                perkX,
                                perkY,
                                perkX + 16,
                                perkY +16,
                                mouseX,
                                mouseY);
                    }
                }

                perks++;
            } else {

                int xSep = 18*(bindings%3);
                int ySep = 18*((int)Math.floor(bindings/3f));

                int perkX = (x+xPadBinding) + xSep;
                int perkY = (y+yPad) + ySep;
                simpleBlit(
                        PPIdentifier.parse(PPIdentifier.of(resourceKey).id.getNamespace() +":textures/bindings/" + PPIdentifier.of(resourceKey).id.getPath() + ".png"),
                        guiGraphics,
                        perkX,
                        perkY,
                        perkX + 16,
                        perkY +16,
                        16,16
                );

                if (selectedItems.appliedItems.contains(perkyItem)) {
                    //? if >1.19
                    //guiGraphics.
                    fill(
                            //? if <=1.19
                            guiGraphics,
                            perkX,perkY,perkX+16,perkY+16, new Color(0, 234, 255,75).getRGB());

                }

                if (mouseX > (x+xPadBinding) + xSep && mouseX < (x+xPadBinding) + xSep + 16) {
                    if (mouseY > (y + yPad) + ySep && mouseY < (y + yPad) + ySep + 16) {
                        hovered  = perkyItem;
                        renderPerkyTooltip(
                                //? if <=1.19
                                this,
                                guiGraphics, perkyItem, PPIdentifier.of(resourceKey),
                                perkX,
                                perkY,
                                perkX + 16,
                                perkY +16,
                                mouseX,
                                mouseY);
                    }
                }
                bindings++;
            }
        }
    }

    @Override
    public void onClose() {
        ClientPlayNetworking.send(new ChangePerksPayload(selectedItems));
        super.onClose();
    }

    //? if >=26.1 {
    /*public static void simpleBlit(PPIdentifier identifier, GuiGraphicsExtractor graphics, int x, int y, int endX, int endY, int textureWidth, int textureHeight) {
    *///? else if >1.19 {
    /*public static void simpleBlit(PPIdentifier identifier, GuiGraphics graphics, int x, int y, int endX, int endY, int textureWidth, int textureHeight) {
    *///? else {
    public static void simpleBlit(PPIdentifier identifier, PoseStack stack, int x, int y, int endX, int endY, int textureWidth, int textureHeight) {
    //? }

    //? if >1.21.1 {
        /*graphics.blit(identifier.id,
                x,y,
                endX,endY,
                0,1,0,1);
        *///? } else if >1.19 {

        /*graphics.blit(identifier.id,
                x,y,0,
                0,0,
                endX-x, endY-y,
                textureWidth,textureHeight
        );*/
        //? } else {
        RenderSystem.setShaderTexture(0, BACKGROUND_LOCATION);
        blit(stack,
                x,y,0,
                0,0,
                endX-x, endY-y,
                textureWidth,textureHeight
        );
        //? }
    }

    //? if >1.21.1 {
    /*@Override
    public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean bl) {
        onClick();
        return super.mouseClicked(mouseButtonEvent, bl);
    }
    *///? } else {
    @Override
    public boolean mouseClicked(double d, double e, int i) {
        onClick();
        return super.mouseClicked(d, e, i);
    }
    //? }

    public void onClick() {
        if (!selectedItems.appliedItems.contains(hovered)) {
            selectedItems.appliedItems.add(hovered);
        } else {
            selectedItems.appliedItems.remove(hovered);
        }
    }

    //? if >=26.1 {
    /*
    public static void renderPerkyTooltip(GuiGraphicsExtractor graphics, PerkyItem item, PPIdentifier id, int perkX, int perkY, int perkEndX, int perkEndY, int mouseX, int mouseY) {
     */
    //? } else {
    public static void renderPerkyTooltip(
            //? if >1.19
            //GuiGraphics graphics,
            //? if <=1.19 {
            Screen graphics,
            PoseStack stack,
            //? }
            PerkyItem item, PPIdentifier id, int perkX, int perkY, int perkEndX, int perkEndY, int mouseX, int mouseY) {
    //? }
        graphics.fill(
                //? if <=1.19
                stack,
                perkX,perkY,perkEndX,perkEndY, new Color(255,255,255,75).getRGB());
        //? if >1.21.1
        //graphics.setTooltipForNextFrame(
        //? if <=1.21.1
        graphics.renderTooltip(
                //? if <=1.19
                stack,
                //? if >1.19
                //Minecraft.getInstance().font,
                List.of(
                Component.translatable("perky." + (item.worth > 0 ? "perk" : "binding") + "." + id.id.getNamespace() + "." + id.id.getPath()).withStyle(ChatFormatting.GREEN).getVisualOrderText(),
                Component.translatable("perky." + (item.worth > 0 ? "perk" : "binding") + "." + id.id.getNamespace() + "." + id.id.getPath() + ".desc").getVisualOrderText()
        ), mouseX, mouseY);
    }

    //? if >1.19 {
    /*@Override
    public boolean isInGameUi() {
        return true;
    }
    *///? }
}
