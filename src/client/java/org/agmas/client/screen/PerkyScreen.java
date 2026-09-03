package org.agmas.client.screen;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
//? if <=1.21.11
import net.minecraft.client.gui.GuiGraphics;
//? if >=26.1
//import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipPositioner;
import net.minecraft.client.gui.screens.inventory.tooltip.DefaultTooltipPositioner;
//? if >1.21.1
//import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.agmas.attachment.PerkyItem;
import org.agmas.attachment.PerkyPlayerAttachment;
import org.agmas.init.ModRegistries;
import org.agmas.network.ChangePerksPayload;
import org.agmas.porting.PPIdentifier;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PerkyScreen extends Screen {

    private static final PPIdentifier CONTAINER_TEXTURE = PPIdentifier.of("textures/gui/container/console.png");

    public PerkyPlayerAttachment selectedItems = new PerkyPlayerAttachment();
    public PerkyItem hovered = null;
    public PerkyScreen(Component component) {
        super(component);
    }

    //? if >=26.1 {
    /*
    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float f) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, f);
    *///? } else {
    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float f) {
        super.render(guiGraphics, mouseX, mouseY, f);
    //? }


        int width = Math.min(guiGraphics.guiWidth()/2, guiGraphics.guiHeight());
        int height = (int)(width*0.736363636f);

        int x = (guiGraphics.guiWidth()/2) - (width/2);
        int y = (guiGraphics.guiHeight()/2) - (height/2);

        int yPad = (int)((40f/162f)*height);
        int xPad = (int)((11f/220f)*width);

        int xPadBinding = (int)((143f/220f)*width);


        simpleBlit(CONTAINER_TEXTURE, guiGraphics,
                x,y,
                x+width,
                y+height,
                width,height);
        int bindings = 0;
        int perks = 0;

        for (Map.Entry<ResourceKey<PerkyItem>, PerkyItem> resourceKeyPerkyItemEntry : ModRegistries.PERKY_ITEM.entrySet()) {

            PerkyItem perkyItem = resourceKeyPerkyItemEntry.getValue();
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
                    guiGraphics.fill(perkX,perkY,perkX+16,perkY+16, new Color(0, 234, 255,75).getRGB());
                }
                if (mouseX > perkX && mouseX < perkX + 16) {
                    if (mouseY > perkY && mouseY < perkY + 16) {
                        hovered  = perkyItem;
                        renderPerkyTooltip(guiGraphics, perkyItem, PPIdentifier.of(resourceKey),
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
                    guiGraphics.fill(perkX,perkY,perkX+16,perkY+16, new Color(0, 234, 255,75).getRGB());
                }

                if (mouseX > (x+xPadBinding) + xSep && mouseX < (x+xPadBinding) + xSep + 16) {
                    if (mouseY > (y + yPad) + ySep && mouseY < (y + yPad) + ySep + 16) {
                        hovered  = perkyItem;
                        renderPerkyTooltip(guiGraphics, perkyItem, PPIdentifier.of(resourceKey),
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

    //? if <26.1
    public static void simpleBlit(PPIdentifier identifier, GuiGraphics graphics, int x, int y, int endX, int endY, int textureWidth, int textureHeight) {
    //? if >=26.1
    //public static void simpleBlit(PPIdentifier identifier, GuiGraphicsExtractor graphics, int x, int y, int endX, int endY, int textureWidth, int textureHeight) {

        //? if >1.21.1 {
        /*graphics.blit(identifier.id,
                x,y,
                endX,endY,
                0,1,0,1);
        *///? } else {

        graphics.blit(identifier.id,
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
        if (!selectedItems.appliedItems.contains(hovered))
            selectedItems.appliedItems.add(hovered);
    }

    //? if >=26.1 {
    /*
    public static void renderPerkyTooltip(GuiGraphicsExtractor graphics, PerkyItem item, PPIdentifier id, int perkX, int perkY, int perkEndX, int perkEndY, int mouseX, int mouseY) {
     */
    //? } else {
    public static void renderPerkyTooltip(GuiGraphics graphics, PerkyItem item, PPIdentifier id, int perkX, int perkY, int perkEndX, int perkEndY, int mouseX, int mouseY) {
    //? }
        graphics.fill(perkX,perkY,perkEndX,perkEndY, new Color(255,255,255,75).getRGB());
        //? if >1.21.1
        //graphics.setTooltipForNextFrame(
        //? if <=1.21.1
        graphics.renderTooltip(
                Minecraft.getInstance().font, List.of(
                Component.translatable("perky." + (item.worth > 0 ? "perk" : "binding") + "." + id.id.getNamespace() + "." + id.id.getPath()).withStyle(ChatFormatting.GREEN).getVisualOrderText(),
                Component.translatable("perky." + (item.worth > 0 ? "perk" : "binding") + "." + id.id.getNamespace() + "." + id.id.getPath() + ".desc").getVisualOrderText()
        ), mouseX, mouseY);
    }
}
