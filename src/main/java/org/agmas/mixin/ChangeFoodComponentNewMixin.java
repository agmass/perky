package org.agmas.mixin;

import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
//? if >=1.21.11 {
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.component.ConsumableListener;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import org.agmas.Perky;
import org.agmas.attachment.PerkyItem;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.sugar.Local;
//? if >=26.2
import net.minecraft.advancements.triggers.CriteriaTriggers;
//? if <26.2
 //import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;

@Mixin(Consumable.class)
public class ChangeFoodComponentNewMixin {

	@WrapMethod(method = "onConsume")
	public ItemStack changeEat(Level level, LivingEntity livingEntity, ItemStack itemStack, Operation<ItemStack> original) {
		for (PerkyItem attachment : Perky.getAttachments(livingEntity)) {
			if (attachment == null) continue;
			Item overrideItem = attachment.overrideFoodData(itemStack,livingEntity);
			if (overrideItem != null && overrideItem.components().has(DataComponents.CONSUMABLE)) {
				RandomSource randomSource = livingEntity.getRandom();
				overrideItem.components().get(DataComponents.CONSUMABLE).emitParticlesAndSounds(randomSource, livingEntity, itemStack, 16);
				if (livingEntity instanceof ServerPlayer) {
					ServerPlayer serverPlayer = (ServerPlayer)livingEntity;
					serverPlayer.awardStat(Stats.ITEM_USED.get(itemStack.getItem()));
					CriteriaTriggers.CONSUME_ITEM.trigger(serverPlayer, itemStack);
				}
				overrideItem.getDefaultInstance().getAllOfType(ConsumableListener.class).forEach(consumableListener -> consumableListener.onConsume(level, livingEntity, overrideItem.getDefaultInstance(), overrideItem.components().get(DataComponents.CONSUMABLE)));
				if (!level.isClientSide()) {
					overrideItem.components().get(DataComponents.CONSUMABLE).onConsumeEffects().forEach(consumeEffect -> consumeEffect.apply(level, itemStack, livingEntity));
				}
				livingEntity.gameEvent(GameEvent.EAT);
				itemStack.consume(1, livingEntity);
				return itemStack;
			}
		}
		return original.call(level, livingEntity, itemStack);
	}

}
//? } else {
/*@Mixin(Player.class)
public class ChangeFoodComponentNewMixin {}
*///? }