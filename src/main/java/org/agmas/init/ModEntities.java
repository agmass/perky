package org.agmas.init;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.agmas.block.entity.ConsoleBlockEntity;
import org.agmas.porting.PPIdentifier;

public class ModEntities {
    public static final BlockEntityType<ConsoleBlockEntity> CONSOLE_BLOCK_ENTITY =
            register("console", ConsoleBlockEntity::new, ModBlocks.CONSOLE);

    private static <T extends BlockEntity> BlockEntityType<T> register(
            String name,
            FabricBlockEntityTypeBuilder.Factory<? extends T> entityFactory,
            Block... blocks
    ) {
        PPIdentifier id = PPIdentifier.of(name);
        return Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, id.id, FabricBlockEntityTypeBuilder.<T>create(entityFactory, blocks).build());
    }

    public static void init() {}
}
