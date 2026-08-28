package org.agmas.bindings;

import net.minecraft.world.entity.HumanoidArm;
import org.agmas.attachment.PerkyItem;

public class NoArmBinding extends PerkyItem {
    public NoArmBinding() {
        super(-2);
    }

    @Override
    public boolean shouldRenderPart(String partName) {
        return !partName.equalsIgnoreCase("offArm");
    }

    @Override
    public boolean modifiesVisibleParts() {
        return true;
    }
}
