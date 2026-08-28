package org.agmas.attachment;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import org.agmas.init.ModRegistries;
import org.agmas.porting.PPIdentifier;

public class PerkyItem {

    public int worth = 0;
    public AttributeMap attributes  = new AttributeMap(AttributeSupplier.builder().build());

    public PerkyItem(int worth) {
        this.worth = worth;
    }

    public PerkyItem(int worth, AttributeMap permanentAttributes) {
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
}
