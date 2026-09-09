package org.agmas.bindings;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

    @Override
    public void tick(LivingEntity livingEntity) {
        if (!livingEntity.getOffhandItem().isEmpty()) {
            ItemStack offhandStack = livingEntity.getOffhandItem().copy();
            if (livingEntity instanceof Player pl) {
                pl.getInventory().add(offhandStack);
            }
            offhandStack.shrink(99999);
        }
        super.tick(livingEntity);
    }
}
