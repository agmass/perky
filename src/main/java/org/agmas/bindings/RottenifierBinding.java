package org.agmas.bindings;

//? if >=1.21.1
import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.agmas.attachment.PerkyItem;
import org.agmas.init.ModTags;

public class RottenifierBinding extends PerkyItem {
    public RottenifierBinding() {
        super(-1);
    }

    @Override
    public Item overrideFoodData(ItemStack stack, LivingEntity player) {
        if (
            //? if >=1.21.1 {
            stack.is(ConventionalItemTags.RAW_MEAT_FOODS) ||
            stack.is(ConventionalItemTags.RAW_FISH_FOODS)
            //? } else {
            /*false
             *///? }
        ) {
            return player.getRandom().nextBoolean() ? Items.ROTTEN_FLESH : Items.SPIDER_EYE;
        }
        return super.overrideFoodData(stack, player);
    }

}
