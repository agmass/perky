package org.agmas.init;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.*;
import org.agmas.attachment.PerkyItem;
import org.agmas.bindings.NoArmBinding;
import org.agmas.porting.PPIdentifier;

import java.util.List;

public class PerkyItems {

    public static PPIdentifier ATTACK_DAMAGE_TEST_PERK_ID = PPIdentifier.of("test_perk_attack_damage");

    public static void init() {
        Registry.register(ModRegistries.PERKY_ITEM, PPIdentifier.of("singlehanded").id, new NoArmBinding());
        Registry.register(ModRegistries.PERKY_ITEM,
                PPIdentifier.of("strong").id,
                new PerkyItem(1, List.of(
                        new PerkyItem.AttributePair(Attributes.ATTACK_DAMAGE, new AttributeModifier(ATTACK_DAMAGE_TEST_PERK_ID.id, 2, AttributeModifier.Operation.ADD_VALUE))
                )));
    }
}
