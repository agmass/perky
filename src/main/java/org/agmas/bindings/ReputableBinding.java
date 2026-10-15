package org.agmas.bindings;

//? if >=1.21.11
import net.minecraft.world.entity.animal.golem.IronGolem;
//? if <1.21.11
//import net.minecraft.world.entity.animal.IronGolem;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
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
