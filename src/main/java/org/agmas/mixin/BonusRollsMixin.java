package org.agmas.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
//? if <26.3
//import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
//? if >=26.3
import net.minecraft.world.level.storage.loot.providers.number.ints.ContextIntProvider;
import org.agmas.Perky;
import org.agmas.attachment.PerkyItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LootPool.class)
public class BonusRollsMixin {
    //? if <26.3 {
    /*@WrapOperation(method = "addRandomItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/providers/number/NumberProvider;getInt(Lnet/minecraft/world/level/storage/loot/LootContext;)I"))
    public int makeDropsEven(NumberProvider instance, LootContext lootContext, Operation<Integer> original) {
    *///? } else if >=26.3 {
    @WrapOperation(method = "addRandomItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/storage/loot/providers/number/ints/ContextIntProvider;getInt(Lnet/minecraft/world/level/storage/loot/LootContext;)I"))
    public int makeDropsEven(ContextIntProvider instance, LootContext lootContext, Operation<Integer> original) {
    //? }
        int rolls = original.call(instance,lootContext);
        //? if >1.21.1 {
        if (lootContext.hasParameter(LootContextParams.THIS_ENTITY) && !lootContext.hasParameter(LootContextParams.BLOCK_STATE)) {

            Entity en =
                    //? if >26.3
                    lootContext.getOptional(LootContextParams.THIS_ENTITY);
                    //? if <26.3
                    //lootContext.getParameter(LootContextParams.THIS_ENTITY);
        //? } else {
        /*if (lootContext.hasParam(LootContextParams.THIS_ENTITY) && !lootContext.hasParam(LootContextParams.BLOCK_STATE)) {
            Entity en = lootContext.getParam(LootContextParams.THIS_ENTITY);
        *///? }
            if (en instanceof LivingEntity livingEntity) {
                for (PerkyItem attachment : Perky.getAttachments(livingEntity)) {
                    if (attachment == null) continue;
                    rolls += attachment.bonusRolls(lootContext,rolls);
                }
            }
        }
        //? if >1.21.1 {
        if (lootContext.hasParameter(LootContextParams.ATTACKING_ENTITY)) {
            Entity en =
                    //? if >26.3
                    lootContext.getOptional(LootContextParams.ATTACKING_ENTITY);
                    //? if <26.3
                    //lootContext.getParameter(LootContextParams.ATTACKING_ENTITY);
        //? } else if =1.21.1 {
        /*if (lootContext.hasParam(LootContextParams.ATTACKING_ENTITY)) {
            Entity en = lootContext.getParam(LootContextParams.ATTACKING_ENTITY);
        *///? } else {
        /*if (lootContext.hasParam(LootContextParams.KILLER_ENTITY)) {
            Entity en = lootContext.getParam(LootContextParams.KILLER_ENTITY);
        *///? }
            if (en instanceof LivingEntity livingEntity) {
                for (PerkyItem attachment : Perky.getAttachments(livingEntity)) {
                    if (attachment == null) continue;
                    rolls += attachment.bonusRolls(lootContext,rolls);
                }
            }
        }
        return rolls;
    }
}
