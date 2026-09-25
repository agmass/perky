package org.agmas.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.agmas.Perky;
import org.agmas.attachment.PerkyItem;
import org.spongepowered.asm.mixin.Mixin;


@Mixin(LivingEntity.class)
public class ChangeFoodComponentOldMixin {

	//? if <1.21.11 {
	/*@WrapMethod(method = "eat(Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)Lnet/minecraft/world/item/ItemStack;")
	public ItemStack changeEat(Level level, ItemStack itemStack, Operation<ItemStack> original) {
		for (PerkyItem attachment : Perky.getAttachments((LivingEntity) (Object)this)) {
			if (attachment == null) continue;
			Item overrideItem = attachment.overrideFoodData(itemStack,(LivingEntity) (Object)this);
			if (overrideItem != null) {
				original.call(level,overrideItem.getDefaultInstance());
				itemStack.shrink(1);
				return itemStack;
			}
		}
		return original.call(level,itemStack);
	}
	*///? }

}