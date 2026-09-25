package org.agmas.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;
import org.agmas.porting.PPIdentifier;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> NYCTOPHOBIA = ResourceKey.create(Registries.DAMAGE_TYPE, PPIdentifier.of("nyctophobia").id);

}
