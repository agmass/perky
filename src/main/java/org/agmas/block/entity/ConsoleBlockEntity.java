package org.agmas.block.entity;

import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.agmas.attachment.BloodstreamAttachment;
import org.agmas.block.ConsoleBlock;
import org.agmas.init.ModEntities;

public class ConsoleBlockEntity extends BlockEntity {

    public boolean shouldRenderModel = false;
    public Direction direction;
    public BloodstreamAttachment attachedBlood = new BloodstreamAttachment();

    public ConsoleBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModEntities.CONSOLE_BLOCK_ENTITY, blockPos, blockState);
        direction = blockState.getValue(HorizontalDirectionalBlock.FACING);
        shouldRenderModel = blockState.getValueOrElse(ConsoleBlock.PART, ConsoleBlock.ConsoleBlockPart.LOWER_RIGHT) == ConsoleBlock.ConsoleBlockPart.LOWER_LEFT;
    }


}
