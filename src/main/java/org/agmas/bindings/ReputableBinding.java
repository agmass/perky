package org.agmas.bindings;

//? if >=1.21.1

import net.fabricmc.fabric.api.tag.convention.v2.ConventionalItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
//? if <=1.21.1
//import net.minecraft.world.entity.animal.IronGolem;
//? if >1.21.1
import net.minecraft.world.entity.animal.golem.IronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.agmas.attachment.PerkyItem;
import org.agmas.init.ModTags;

public class ReputableBinding extends PerkyItem {
    public ReputableBinding() {
        super(-1);
    }

    @Override
    public float multiplyVillagerPrices() {
        return 2;
    }

    @Override
    public boolean isAngryAt(LivingEntity holder, Mob mob) {
        return mob instanceof IronGolem;
    }
}
