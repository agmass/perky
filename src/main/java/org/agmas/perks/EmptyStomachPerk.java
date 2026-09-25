package org.agmas.perks;

//? if >=1.21.1
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
    public InteractionResult overrideItemUse(ItemStack stack, Player player, InteractionHand hand) {
        if (
            //? if <1.21.1
            //stack.getItem().getFoodProperties() != null
            //? if >=1.21.1
            stack.getComponents().has(DataComponents.FOOD)
        ) {
            player.startUsingItem(hand);
            return InteractionResult.SUCCESS;
        }
        return super.overrideItemUse(stack, player, hand);
    }
}
