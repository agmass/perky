package org.agmas.attachment;

import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.InventoryMenu;
import org.agmas.accessors.PlayerAccessor;
import org.agmas.init.ModRegistries;
import org.agmas.porting.PPIdentifier;

import java.util.ArrayList;
import java.util.List;

public class PerkyItem {

    public int worth;
    public List<AttributePair> attributes;

    public PerkyItem(int worth) {
        this.worth = worth;
    }

    public PerkyItem(int worth, List<AttributePair> permanentAttributes) {
        this(worth);
        attributes = permanentAttributes;
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
                /*Attribute attr = attribute.holder.value();
                *///? }

                //? if >=1.21.1 {
                if (!livingEntity.getAttribute(attr).hasModifier(attribute.modifier.id())) {
                //? } else {
                /*if (!livingEntity.getAttribute(attr).hasModifier(attribute.modifier)) {
                *///? }
                    livingEntity.getAttribute(attr).addPermanentModifier(attribute.modifier);
                }
            }
        }
    }

    public float modifyDamage(LivingEntity victim, DamageSource source, float v) {
        return -1;
    }

    public void onDamage(LivingEntity victim, DamageSource source, float v) {
        ((PlayerAccessor)victim).perky$setTimeSinceLastDamage(0);
    }

    public static class AttributePair {
        public Holder<Attribute> holder;
        public AttributeModifier modifier;

        public AttributePair(Holder<Attribute> holder, AttributeModifier modifier) {
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
