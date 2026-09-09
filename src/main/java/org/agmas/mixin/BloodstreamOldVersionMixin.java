package org.agmas.mixin;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.agmas.Perky;
import org.agmas.accessors.PlayerAccessor;
import org.agmas.attachment.PerkyItem;
import org.agmas.init.ModComponents;
import org.agmas.init.ModRegistries;
import org.agmas.porting.PPIdentifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;
import java.util.List;

@Mixin(ItemStack.class)
public class BloodstreamOldVersionMixin {

	//? if <=1.21.1 {
	/*@Inject(method = "getTooltipLines", at = @At("RETURN"))
    //? if >=1.21.1
    public void addToTooltip(Item.TooltipContext tooltipContext, @Nullable Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir) {
    //? if <1.21.1
	//public void addToTooltip(Player player, TooltipFlag tooltipFlag, CallbackInfoReturnable<List<Component>> cir) {
		List<Component> components = cir.getReturnValue();
		if (ModComponents.BLOOD_COMPONENT.has((ItemStack)(Object) this)) {
			components.add(Component.empty());
			components.add(Component.translatable("perky.bloodstream").withStyle(Style.EMPTY.withColor(new Color(130,0,0).getRGB())));
			for (PerkyItem item : ModComponents.BLOOD_COMPONENT.get((ItemStack)(Object) this).appliedItems) {
				PPIdentifier id = new PPIdentifier(ModRegistries.PERKY_ITEM.getResourceKey(item).get().location());
				components.add(Component.translatable("perky." + (item.worth > 0 ? "perk" : "binding") + "." + id.id.getNamespace() + "." + id.id.getPath()));
			}
		}
	}
	*///? }

}