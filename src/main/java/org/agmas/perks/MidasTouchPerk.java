package org.agmas.perks;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.agmas.attachment.PerkyItem;

public class MidasTouchPerk extends PerkyItem {
    public MidasTouchPerk() {
        super(1);
    }

    @Override
    public Item overrideFoodData(ItemStack stack, LivingEntity player) {
        if (stack.is(Items.APPLE)) {
            return Items.GOLDEN_APPLE;
        } else if (stack.is(Items.CARROT)) {
            return Items.GOLDEN_CARROT;
        }
        return super.overrideFoodData(stack, player);
    }

    @Override
    public InteractionResult overrideItemUse(ItemStack stack, Player player, InteractionHand hand) {
        if (stack.is(Items.APPLE)) {
            player.startUsingItem(hand);
            return InteractionResult.SUCCESS;
        }
        return super.overrideItemUse(stack, player, hand);
    }
}
