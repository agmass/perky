package org.agmas.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
//? if >1.19 {
//? if <=1.21.11
//import net.minecraft.client.gui.GuiGraphics;
//? }
//? if >=26.1
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
//? if >1.21.1
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import org.agmas.Perky;
import org.agmas.attachment.BloodstreamAttachment;
import org.agmas.attachment.PerkyItem;
import org.agmas.init.ModAttachments;
import org.agmas.init.ModRegistries;
import org.agmas.init.ModTags;
import org.agmas.init.PerkyCommands;
import org.agmas.network.ChangePerksPayload;
import org.agmas.porting.PPIdentifier;
import org.joml.Matrix3x2fStack;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PerkyScreen extends Screen {

    private static final PPIdentifier CONTAINER_TEXTURE = PPIdentifier.of("textures/gui/container/console.png");


    public ArrayList<PerkyItem> incompatibleCache = new ArrayList<>();
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
    
    @Override
    public void extractRenderState(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY, float f) {
        super.extractRenderState(guiGraphics, mouseX, mouseY, f);
        int guiGraphicsWidth = guiGraphics.guiWidth();
        int guiGraphicsHeight = guiGraphics.guiHeight();
    //? } else if >1.19 {
    /*@Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float f) {
        super.render(guiGraphics, mouseX, mouseY, f);
        int guiGraphicsWidth = guiGraphics.guiWidth();
        int guiGraphicsHeight = guiGraphics.guiHeight();
    *///? } else {

    /*@Override
    public void render(PoseStack guiGraphics, int mouseX, int mouseY, float f) {
        super.render(guiGraphics, mouseX, mouseY, f);
        int guiGraphicsWidth = width;
        int guiGraphicsHeight = height;
    *///? }
        int width_ = 220;
        int height_ = 162;

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
        int worth = 0;

        //? if <=1.19
        //Runnable tooltipRun = null;
        hovered = null;
        for (Map.Entry<ResourceKey<PerkyItem>, PerkyItem> resourceKeyPerkyItemEntry : ModRegistries.PERKY_ITEM.entrySet()) {

            PerkyItem perkyItem = resourceKeyPerkyItemEntry.getValue();
            if (!availablePerks.appliedItems.contains(perkyItem) && !Perky.getAttachments(Minecraft.getInstance().player).contains(perkyItem) && !Minecraft.getInstance().player.isCreative())
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
                boolean canBeSelected = !incompatibleCache.contains(perkyItem);

                if (canBeSelected) {
                    if (selectedItems.appliedItems.contains(perkyItem)) {
                        //? if >1.19
                        guiGraphics.
                                fill(
                                        //? if <=1.19
                                        //guiGraphics,
                                        perkX, perkY, perkX + 16, perkY + 16, new Color(0, 234, 255, 75).getRGB());
                    }
                    if (mouseX > perkX && mouseX < perkX + 16) {
                        if (mouseY > perkY && mouseY < perkY + 16) {
                            hovered = perkyItem;

                            //? if <=1.19
                            //tooltipRun = ()-> {
                            renderPerkyTooltip(
                                    //? if <=1.19
                                    //this,
                                    guiGraphics, perkyItem, PPIdentifier.of(resourceKey),
                                    perkX,
                                    perkY,
                                    perkX + 16,
                                    perkY + 16,
                                    mouseX,
                                    mouseY);

                            //? if <=1.19
                            //};
                        }
                    }
                } else {
                    //? if >1.19
                    guiGraphics.
                            fill(
                                    //? if <=1.19
                                    //guiGraphics,
                                    perkX, perkY, perkX + 16, perkY + 16, new Color(157, 157, 157, 75).getRGB());
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
                boolean canBeSelected = !incompatibleCache.contains(perkyItem);

                if (canBeSelected) {

                    if (selectedItems.appliedItems.contains(perkyItem)) {
                        //? if >1.19
                        guiGraphics.
                                fill(
                                        //? if <=1.19
                                        //guiGraphics,
                                        perkX, perkY, perkX + 16, perkY + 16, new Color(0, 234, 255, 75).getRGB());

                    }

                    if (mouseX > (x + xPadBinding) + xSep && mouseX < (x + xPadBinding) + xSep + 16) {
                        if (mouseY > (y + yPad) + ySep && mouseY < (y + yPad) + ySep + 16) {
                            hovered = perkyItem;

                            //? if <=1.19
                            //tooltipRun = ()-> {
                            renderPerkyTooltip(
                                    //? if <=1.19
                                    //this,
                                    guiGraphics, perkyItem, PPIdentifier.of(resourceKey),
                                    perkX,
                                    perkY,
                                    perkX + 16,
                                    perkY + 16,
                                    mouseX,
                                    mouseY);
                            //? if <=1.19
                            //};
                        }
                    }
                } else {
                    //? if >1.19
                    guiGraphics.
                            fill(
                                    //? if <=1.19
                                    //guiGraphics,
                                    perkX, perkY, perkX + 16, perkY + 16, new Color(157, 157, 157, 75).getRGB());
                }
                bindings++;
            }
        }

        if (!Minecraft.getInstance().player.isCreative()) {
            if (selectedItems.getWorth() > 0) {
                //? if <=1.19 {
                /*drawCenteredString(
                    guiGraphics,
                *///? } else if <26.1 {
                /*guiGraphics.drawCenteredString(
                *///? } else {
                guiGraphics.centeredText(
                //? }
                        Minecraft.getInstance().font,
                        Component.translatable("perky.not_enough_worth", selectedItems.getWorth()).withStyle(ChatFormatting.RED),
                        guiGraphicsWidth/2,
                        20  ,
                        -1
                        );
            }
        }

        //? if <=1.19 {
        /*if (tooltipRun != null) {
            tooltipRun.run();
        }
        *///? }
    }

    public void checkIfIncompatible() {
        for (Map.Entry<ResourceKey<PerkyItem>, PerkyItem> resourceKeyPerkyItemEntry : ModRegistries.PERKY_ITEM.entrySet()) {
            PerkyItem perkyItem = resourceKeyPerkyItemEntry.getValue();
            for (PerkyItem appliedItem : selectedItems.appliedItems) {
                if (appliedItem == null) continue;
                if (appliedItem.exclusive() != null) {
                    if (ModRegistries.PERKY_ITEM.wrapAsHolder(perkyItem).is(appliedItem.exclusive())) {
                        incompatibleCache.add(perkyItem);
                    }
                }
                if (perkyItem.exclusive() != null) {
                    if (ModRegistries.PERKY_ITEM.wrapAsHolder(appliedItem).is(perkyItem.exclusive())) {
                        incompatibleCache.add(perkyItem);
                    }
                }
            }
        }
    }

    @Override
    public void onClose() {
        ClientPlayNetworking.send(new ChangePerksPayload(selectedItems));
        super.onClose();
    }

    //? if >=26.1 {
    public static void simpleBlit(PPIdentifier identifier, GuiGraphicsExtractor graphics, int x, int y, int endX, int endY, int textureWidth, int textureHeight) {
    //? } else if >1.19 {
    /*public static void simpleBlit(PPIdentifier identifier, GuiGraphics graphics, int x, int y, int endX, int endY, int textureWidth, int textureHeight) {
    *///? } else {
    /*public static void simpleBlit(PPIdentifier identifier, PoseStack stack, int x, int y, int endX, int endY, int textureWidth, int textureHeight) {
    *///? }

    //? if >1.21.1 {
        graphics.blit(identifier.id,
                x,y,
                endX,endY,
                0,1,0,1);
        //? } else if >1.19 {

        /*graphics.blit(identifier.id,
                x,y,0,
                0,0,
                endX-x, endY-y,
                textureWidth,textureHeight
        );
        *///? } else {
        /*RenderSystem.setShaderTexture(0, identifier.id);
        blit(stack,
                x,y,0,
                0,0,
                endX-x, endY-y,
                textureWidth,textureHeight
        );
        *///? }
    }

    //? if >1.21.1 {
    @Override
    public boolean mouseClicked(MouseButtonEvent mouseButtonEvent, boolean bl) {
        onClick();
        return super.mouseClicked(mouseButtonEvent, bl);
    }
    //? } else {
    /*@Override
    public boolean mouseClicked(double d, double e, int i) {
        onClick();
        return super.mouseClicked(d, e, i);
    }
    *///? }

    public void onClick() {
        if (!selectedItems.appliedItems.contains(hovered)) {
            selectedItems.appliedItems.add(hovered);
            incompatibleCache.clear();
            checkIfIncompatible();
        } else {
            selectedItems.appliedItems.remove(hovered);
            incompatibleCache.clear();
            checkIfIncompatible();
        }
    }

    //? if >=26.1 {
    
    public static void renderPerkyTooltip(GuiGraphicsExtractor graphics, PerkyItem item, PPIdentifier id, int perkX, int perkY, int perkEndX, int perkEndY, int mouseX, int mouseY) {
     
    //? } else {
    /*public static void renderPerkyTooltip(
            //? if >1.19
            GuiGraphics graphics,
            //? if <=1.19 {
            /^Screen graphics,
            PoseStack stack,
            ^///? }
            PerkyItem item, PPIdentifier id, int perkX, int perkY, int perkEndX, int perkEndY, int mouseX, int mouseY) {
    *///? }
        graphics.fill(
                //? if <=1.19
                //stack,
                perkX,perkY,perkEndX,perkEndY, new Color(255,255,255,75).getRGB());
        //? if >1.21.1
        graphics.setTooltipForNextFrame(
        //? if <=1.21.1
        //graphics.renderTooltip(
                //? if <=1.19
                //stack,
                //? if >1.19
                Minecraft.getInstance().font,
                List.of(
                        Component.translatable("perky." + (item.worth > 0 ? "perk" : "binding") + "." + id.id.getNamespace() + "." + id.id.getPath()).withStyle(item.worth > 0 ? ChatFormatting.GREEN : ChatFormatting.RED).getVisualOrderText(),
                        Component.empty().getVisualOrderText(),
                        Component.translatable("perky." + (item.worth > 0 ? "perk" : "binding") + "." + id.id.getNamespace() + "." + id.id.getPath() + ".desc").getVisualOrderText(),
                        Component.empty().getVisualOrderText(),
                        Component.translatable("perky.worth", Math.abs(item.worth), item.worth > 0 ? PerkyCommands.BINDING_COMP  :  PerkyCommands.PERK_COMP).withStyle(item.worth > 0 ? ChatFormatting.RED : ChatFormatting.GREEN).getVisualOrderText()
                ), mouseX, mouseY);
    }

    //? if >1.21.1 {
    @Override
    public boolean isInGameUi() {
        return true;
    }

    //? }
    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
