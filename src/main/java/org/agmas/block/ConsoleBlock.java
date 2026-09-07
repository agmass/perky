package org.agmas.block;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
//? if >1.21.1
import net.minecraft.world.level.ScheduledTickAccess;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;
import org.agmas.attachment.BloodstreamAttachment;
import org.agmas.block.entity.ConsoleBlockEntity;
import org.agmas.init.ModAttachments;
import org.agmas.init.ModBlocks;
import org.agmas.network.OpenPerkyMenuPayload;
import org.jetbrains.annotations.Nullable;

public class ConsoleBlock extends HorizontalDirectionalBlock {

    public static final EnumProperty<ConsoleBlockPart> PART = EnumProperty.create("console_part", ConsoleBlockPart.class);

    public ConsoleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, ConsoleBlockPart.LOWER_LEFT).setValue(FACING, Direction.NORTH));
    }

    //? if >1.20.1 {
    public static final MapCodec<ConsoleBlock> CODEC = simpleCodec(ConsoleBlock::new);

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }
    //?}

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(new Property[]{FACING, PART});
    }

    public BlockPos findParent(BlockPos pos, BlockState state, LevelAccessor level) {
        if (state.getBlock() instanceof ConsoleParentBlock) {
            return pos;
        }
        switch (state.getValue(PART)) {
            case LOWER_LEFT -> {
                return pos;
            }
            case UPPER_LEFT -> {
                return pos.below(1);
            }
            case LOWER_RIGHT -> {
                return pos.relative(state.getValue(FACING).getCounterClockWise());
            }
            case UPPER_RIGHT -> {
                return pos.relative(state.getValue(FACING).getCounterClockWise()).below(1);
            }
        }
        return null;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        Direction direction = blockPlaceContext.getHorizontalDirection();
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        BlockPos blockPos2 = blockPos.relative(direction.getClockWise());
        BlockPos blockPos3 = blockPos.above();
        BlockPos blockPos4 = blockPos2.above();
        Level level = blockPlaceContext.getLevel();
        return
                ((level.getBlockState(blockPos2).canBeReplaced(blockPlaceContext) && level.getWorldBorder().isWithinBounds(blockPos2)) &&
                        (level.getBlockState(blockPos3).canBeReplaced(blockPlaceContext) && level.getWorldBorder().isWithinBounds(blockPos3)) &&
                        (level.getBlockState(blockPos4).canBeReplaced(blockPlaceContext) && level.getWorldBorder().isWithinBounds(blockPos4)))
                        ? (BlockState) this.defaultBlockState().setValue(FACING, direction) : null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
        if (!level.isClientSide()) {
            level.setBlock(blockPos.above(), (BlockState) ModBlocks.CONSOLE_CHILD.defaultBlockState().setValue(PART, ConsoleBlockPart.UPPER_LEFT), 3);
            BlockPos blockPos2 = blockPos.relative((Direction) blockState.getValue(FACING).getClockWise());
            level.setBlock(blockPos2, (BlockState) ModBlocks.CONSOLE_CHILD.defaultBlockState().setValue(PART, ConsoleBlockPart.LOWER_RIGHT), 3);
            BlockPos blockPos3 = blockPos2.above();
            level.setBlock(blockPos3, (BlockState) ModBlocks.CONSOLE_CHILD.defaultBlockState().setValue(PART, ConsoleBlockPart.UPPER_RIGHT), 3);
            level.updateNeighborsAt(blockPos, Blocks.AIR);
            blockState.updateNeighbourShapes(level, blockPos, 3);
        }
    }

    //? if <1.21.1 {

    /*@Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
    *///? } else if =1.21.1 {
    /*@Override
    protected BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
    *///? } else {
    @Override
    protected BlockState updateShape(BlockState blockState, LevelReader levelReader, ScheduledTickAccess scheduledTickAccess, BlockPos blockPos, Direction direction, BlockPos blockPos2, BlockState blockState2, RandomSource randomSource) {
    //? }

        Direction adjacent = blockState.getValue(FACING);
        Direction adjacentVertical;
        if (blockState.getValue(PART) == ConsoleBlockPart.LOWER_LEFT || blockState.getValue(PART) == ConsoleBlockPart.UPPER_LEFT) {
            adjacent = adjacent.getClockWise();
        } else {
            adjacent = adjacent.getCounterClockWise();
        }

        if (blockState.getValue(PART) == ConsoleBlockPart.UPPER_LEFT || blockState.getValue(PART) == ConsoleBlockPart.UPPER_RIGHT) {
            adjacentVertical = Direction.DOWN;
        } else {
            adjacentVertical = Direction.UP;
        }

        if (direction == adjacentVertical || direction == adjacent) {
            if (!blockState2.is(ModBlocks.CONSOLE) && !blockState2.is(ModBlocks.CONSOLE_CHILD))
                return Blocks.AIR.defaultBlockState();
        }

        //? if >1.21.1 {
        return super.updateShape(blockState, levelReader, scheduledTickAccess, blockPos, direction, blockPos2, blockState2, randomSource);
        //? } else {
        /*return super.updateShape(blockState, direction, blockState2, levelAccessor, blockPos, blockPos2);
         *///? }
    }

    //? if >=1.21.1 {
    protected InteractionResult useWithoutItem(final BlockState state, final Level level, final BlockPos pos, final Player player, final BlockHitResult hitResult) {
    //? } else {
    /*@Override
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
    *///? }
        if (!level.isClientSide()) {
            player.getAttachedOrElse(ModAttachments.PERKY_ATTACHMENT, new BloodstreamAttachment()).usingConsole = true;
            ServerPlayNetworking.send(((ServerPlayer) player), new OpenPerkyMenuPayload());
        }
        return InteractionResult.SUCCESS;
    }

    public enum ConsoleBlockPart implements StringRepresentable {
        UPPER_LEFT("upper_left"),
        UPPER_RIGHT("upper_right"),
        LOWER_LEFT("lower_left"),
        LOWER_RIGHT("lower_right");


        private final String name;

        ConsoleBlockPart(final String string2) {
            this.name = string2;
        }

        public String toString() {
            return this.name;
        }

        public String getSerializedName() {
            return this.name;
        }
    }
}
