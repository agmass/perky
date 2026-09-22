package org.agmas.bindings;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.agmas.attachment.PerkyItem;

public class HalfStackSizeBinding extends PerkyItem {
    public HalfStackSizeBinding() {
        super(-2);
    }

    @Override
    public int modifyStackSize(ItemStack stack, LivingEntity livingEntity, int size) {
        if (!(stack.getItem() instanceof BlockItem)) {
            return (int) (size/2f);
        }
        return -1;
    }
}
