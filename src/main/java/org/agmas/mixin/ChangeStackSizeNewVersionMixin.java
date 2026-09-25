package org.agmas.mixin;

import net.minecraft.world.item.Item;
import org.agmas.init.ModComponents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? if >=26.1 {
/*import net.minecraft.world.item.ItemInstance;
@Mixin(ItemInstance.class)
public interface ChangeStackSizeNewVersionMixin {
	@Inject(method = "getMaxStackSize", at = @At("RETURN"), cancellable = true)
	public default void changeStackSize(CallbackInfoReturnable<Integer> cir) {
		int perkyStack = ((ItemInstance)(Object)this).getOrDefault(ModComponents.STACK_SIZE_CHANGED_BY_PERKS.COMPONENT_FORM, -1);
		if (perkyStack != -1) {
			cir.setReturnValue(perkyStack);
			cir.cancel();
		}
	}
}
*///? } else {
@Mixin(Item.class)
public interface ChangeStackSizeNewVersionMixin {
}
//? }