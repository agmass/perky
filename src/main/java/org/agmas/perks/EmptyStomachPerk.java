package org.agmas.perks;

//? if >=1.21.11
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.agmas.attachment.PerkyItem;

public class EmptyStomachPerk extends PerkyItem {
    public EmptyStomachPerk() {
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
        if (
            //? if <1.21.11
            //stack.getFoodComponent() != null
            //? if >=1.21.11
            stack.getComponents().has(DataComponents.FOOD)
        ) {
            player.startUsingItem(hand);
            return InteractionResult.SUCCESS;
        }
        return super.overrideItemUse(stack, player, hand);
    }
}
