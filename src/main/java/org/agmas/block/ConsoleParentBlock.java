package org.agmas.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.agmas.block.entity.ConsoleBlockEntity;
import org.jetbrains.annotations.Nullable;

public class ConsoleParentBlock extends ConsoleBlock implements EntityBlock {
    public ConsoleParentBlock(Properties properties) {
        super(properties);
    }


    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new ConsoleBlockEntity(blockPos, blockState);
    }

}
