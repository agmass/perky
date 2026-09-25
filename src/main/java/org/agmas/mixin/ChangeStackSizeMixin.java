package org.agmas.mixin;

//? if >=1.21.1
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.agmas.Perky;
import org.agmas.attachment.PerkyItem;
import org.agmas.init.ModComponents;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ChangeStackSizeMixin {

	@Shadow
	public abstract Item getItem();

	@Shadow
	private int count;

	@Shadow
	public abstract void shrink(int i);

	@Shadow
	public abstract ItemStack copyWithCount(int i);

	@Inject(method = "inventoryTick", at = @At("RETURN"), cancellable = true)
	//? if >1.21.1
	public void changeStackSize(Level level, Entity owner, EquipmentSlot equipmentSlot, CallbackInfo ci) {
	//? if <=1.21.1
	//public void changeStackSize(Level level, Entity owner, int i, boolean bl, CallbackInfo ci) {
			int originalSize =
				//? if >=1.21.1
				((ItemStack)(Object)this).getOrDefault(DataComponents.MAX_STACK_SIZE, 1);
				//? if <1.21.1
				//getItem().getMaxStackSize();
		int size = originalSize;
		if (owner instanceof LivingEntity livingEntity) {
			for (PerkyItem attachment : Perky.getAttachments(livingEntity)) {
				if (attachment == null) continue;
				int stackModify = attachment.modifyStackSize(((ItemStack) (Object)this), livingEntity, size);
				if (stackModify != -1) {
					size = stackModify;
				}
				if (stackModify == -1) {
					ModComponents.STACK_SIZE_CHANGED_BY_PERKS.remove(((ItemStack) (Object)this));
				}
			}
			if (size != originalSize) {
				if (count > size && owner instanceof Player player && size > 1) {
					int leftover = count-size;
					player.getInventory().add(copyWithCount(leftover));
					shrink(size);
				}
				ModComponents.STACK_SIZE_CHANGED_BY_PERKS.set(((ItemStack) (Object)this), size);
			}
		}
	}


	//? if <26.1 {
	@Inject(method = "getMaxStackSize", at = @At("RETURN"), cancellable = true)
	public void stack(CallbackInfoReturnable<Integer> cir) {
		if (ModComponents.STACK_SIZE_CHANGED_BY_PERKS.has(((ItemStack) (Object)this))) {
			int perkyStack = ModComponents.STACK_SIZE_CHANGED_BY_PERKS.get(((ItemStack) (Object) this));
			if (perkyStack != -1) {
				cir.setReturnValue(perkyStack);
				cir.cancel();
			}
		}
	}
	//? }
}