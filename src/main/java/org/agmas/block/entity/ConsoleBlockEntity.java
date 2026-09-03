package org.agmas.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.agmas.init.ModEntities;

public class ConsoleBlockEntity extends BlockEntity {
    public ConsoleBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModEntities.CONSOLE_BLOCK_ENTITY, blockPos, blockState);
    }
}
