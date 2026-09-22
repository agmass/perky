package org.agmas.perks;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.agmas.attachment.PerkyItem;

public class MoreBuildingStackPerk extends PerkyItem {
    public MoreBuildingStackPerk() {
        super(1);
    }

    @Override
    public int modifyStackSize(ItemStack stack, LivingEntity livingEntity, int size) {
        if (stack.getItem() instanceof BlockItem) {
            return 99;
        }
        return -1;
    }
}
