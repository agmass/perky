package org.agmas.mixin;

//? if >=1.21.1
import net.minecraft.core.component.DataComponents;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.agmas.Perky;
import org.agmas.attachment.PerkyItem;
import org.agmas.init.ModComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Inventory.class)
public abstract class AutoChangeStackSizeMixin {

	@WrapOperation(method = "hasRemainingSpaceForItem", at = @At(value = "INVOKE", target =
		//? if <1.21.1
		//	"Lnet/minecraft/world/item/ItemStack;isSameItemSameTags(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"
		//? if >=1.21.1
			"Lnet/minecraft/world/item/ItemStack;isSameItemSameComponents(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/ItemStack;)Z"
	))
	public boolean changeStackSize(ItemStack stack, ItemStack stack2, Operation<Boolean> original) {
		if (ModComponents.STACK_SIZE_CHANGED_BY_PERKS.has(stack) && !ModComponents.STACK_SIZE_CHANGED_BY_PERKS.has(stack2) && stack.is(stack2.getItem())) {
			ItemStack copystack1 = stack.copy();
			ModComponents.STACK_SIZE_CHANGED_BY_PERKS.remove(copystack1);
			return original.call(copystack1, stack2);
		}
		return original.call(stack,stack2);
	}
}