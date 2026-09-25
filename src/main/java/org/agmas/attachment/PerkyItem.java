package org.agmas.attachment;

import net.minecraft.core.Holder;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import org.agmas.accessors.PlayerAccessor;
import org.agmas.init.ModRegistries;
import org.agmas.init.PerkyItems;
import org.agmas.porting.PPIdentifier;

import java.util.ArrayList;
import java.util.List;

public class PerkyItem {

    public int worth;
    public List<AttributePair> attributes;
    public boolean staticModifiers = true;

    public PerkyItem(int worth) {
        this.worth = worth;
        attributes = new ArrayList<>();
    }

    public PerkyItem(int worth, List<AttributePair> permanentAttributes) {
        this(worth);
        attributes = permanentAttributes;
    }

    public int bonusRolls(LootContext params, int originalRolls) {
        return 0;
    }

    public CancellableResult canRiptide(Player player) { return CancellableResult.PASS;}

    public boolean canAngerMob(LivingEntity holder, Mob mob) { return true;}

    public boolean shouldRenderPart(String partName) {
        return true;
    }
    public float getPotionTimeMultiplier(LivingEntity livingEntity, MobEffectInstance effect) {
        return 1f;
    }

    public boolean modifiesVisibleParts() {
        return false;
    }

    public static PPIdentifier getKey(PerkyItem item) {
        return item == null ? null : new PPIdentifier(ModRegistries.PERKY_ITEM.getKey(item));
    }
    
    public void onRemoved(LivingEntity livingEntity) {}

    public void tick(LivingEntity livingEntity) {
        if (attributes != null) {
            for (PerkyItem.AttributePair attribute : attributes) {
                //? if >=1.21.1 {
                Holder<Attribute> attr = attribute.holder;
                //? } else {
                /*Attribute attr = attribute.holder;
                *///? }

                //? if >=1.21.1 {
                if (!livingEntity.getAttribute(attr).hasModifier(attribute.modifier.id())) {
                //? } else {
                /*if (!livingEntity.getAttribute(attr).hasModifier(attribute.modifier)) {
                *///? }
                    livingEntity.getAttribute(attr).addPermanentModifier(attribute.modifier);
                } else if (!staticModifiers) {
                    //? if >=1.21.1 {
                    livingEntity.getAttribute(attr).addOrReplacePermanentModifier(attribute.modifier);
                    //? } else {
                    /*if (livingEntity.getAttribute(attr).hasModifier(attribute.modifier)) {
                        livingEntity.getAttribute(attr).removePermanentModifier(attribute.modifier.getId());
                        livingEntity.getAttribute(attr).addPermanentModifier(attribute.modifier);
                    }
                    *///? }
                }
            }
        }
    }

    public Item overrideFoodData(ItemStack stack, LivingEntity player) {
        return null;
    }
    public InteractionResult overrideItemUse(ItemStack stack, Player player, InteractionHand hand) {
        return InteractionResult.PASS;
    }

    public TagKey<PerkyItem> exclusive() {
        return null;
    }

    public int modifyStackSize(ItemStack stack, LivingEntity livingEntity, int size) {
        return -1;
    }

    public float modifyDamage(LivingEntity victim, DamageSource source, float v) {
        return -1;
    }

    public void onDamage(LivingEntity victim, DamageSource source, float v) {
        ((PlayerAccessor)victim).perky$setTimeSinceLastDamage(0);
    }

    public static class AttributePair {
        //? if >1.20.4
        public Holder<Attribute> holder;
        //? if <=1.20.4
        //public Attribute holder;
        public AttributeModifier modifier;

        //? if >1.20.4
        public AttributePair(Holder<Attribute> holder, AttributeModifier modifier) {
        //? if <=1.20.4
        //public AttributePair(Attribute holder, AttributeModifier modifier) {
            this.holder = holder;
            this.modifier = modifier;
        }
    }


    public enum CancellableResult {
        CANCEL,
        PASS,
        SUCCESS
    }
}
