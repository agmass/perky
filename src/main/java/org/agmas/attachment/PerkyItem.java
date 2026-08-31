package org.agmas.attachment;

import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.ai.attributes.*;
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

    public boolean shouldRenderPart(String partName) {
        return true;
    }

    public boolean modifiesVisibleParts() {
        return false;
    }

    public static PPIdentifier getKey(PerkyItem item) {
        return item == null ? null : new PPIdentifier(ModRegistries.PERKY_ITEM.getKey(item));
    }

    public static class AttributePair {
        public Holder<Attribute> holder;
        public AttributeModifier modifier;

        public AttributePair(Holder<Attribute> holder, AttributeModifier modifier) {
            this.holder = holder;
            this.modifier = modifier;
        }
    }
}
