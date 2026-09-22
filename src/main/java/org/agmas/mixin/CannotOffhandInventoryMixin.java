package org.agmas.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.datafixers.util.Pair;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
//? if >=1.21.11
import net.minecraft.resources.Identifier;
//? if <1.21.11
//import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
//? if <=1.20.1
//import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import org.agmas.Perky;
import org.agmas.attachment.PerkyItem;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(InventoryMenu.class)
public class CannotOffhandInventoryMixin {
	@WrapOperation(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/InventoryMenu;addSlot(Lnet/minecraft/world/inventory/Slot;)Lnet/minecraft/world/inventory/Slot;",
			//? if <1.20.1
			//ordinal = 5
			//? if >=1.20.1
			ordinal = 1
	))
	public Slot noOffhandSlot(InventoryMenu instance, Slot slot, Operation<Slot> original, @Local(argsOnly = true) Player player, @Local(argsOnly = true)Inventory inventory) {
		return original.call(instance, new Slot(inventory, 40,slot.x, slot.y) {

			@Override
			//? if >1.20.1 {
			public void setByPlayer(ItemStack itemStack, ItemStack itemStack2) {
				player.onEquipItem(EquipmentSlot.OFFHAND, itemStack2, itemStack);
				super.setByPlayer(itemStack, itemStack2);
			}//? } else {
			/*public void setByPlayer(ItemStack itemStack) {
				Equipable equipable = Equipable.get(itemStack);
				if (equipable != null) {
					player.onEquipItem(EquipmentSlot.OFFHAND, getItem(), itemStack);
				}
				super.setByPlayer(itemStack);
			}
			*///? }


			@Override
			public boolean isActive() {
				for (PerkyItem attachment : Perky.getAttachments(player)) {
					if (!attachment.shouldRenderPart("offArm")) {
						return false;
					}
				}
				return super.isActive();
			}


			//? if >=1.21.11 {
			@Override
			public Identifier getNoItemIcon() {
				return InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD;
			}
			//? } else {

			/*public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
				return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
			}
			*///? }
		});
	}
}