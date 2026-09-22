package org.agmas.bindings;

//? if >=1.21.1
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
//? if <1.21.1
//import net.fabricmc.fabric.api.tag.convention.v1.ConventionalItemTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import org.agmas.attachment.PerkyItem;
import org.agmas.init.ModTags;

public class CannibalBinding extends PerkyItem {
    public CannibalBinding() {
        super(-1);
    }


    @Override
    public InteractionResult overrideItemUse(ItemStack stack, Player player, InteractionHand hand) {
        if (
                stack.is(ConventionalItemTags.FOODS) &&
                //? if >=1.21.1 {
                !stack.is(ConventionalItemTags.RAW_MEAT_FOODS) &&
                !stack.is(ConventionalItemTags.COOKED_MEAT_FOODS) &&
                !stack.is(ConventionalItemTags.RAW_FISH_FOODS) &&
                !stack.is(ConventionalItemTags.COOKED_FISH_FOODS) &&
                //? }
                !stack.is(ModTags.MEATS)
        ) {
            return InteractionResult.FAIL;
        }
        return super.overrideItemUse(stack, player, hand);
    }
}
