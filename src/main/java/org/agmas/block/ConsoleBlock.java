package org.agmas.block;

import com.mojang.serialization.MapCodec;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.RandomSource;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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
import org.agmas.attachment.PerkyItem;
import org.agmas.block.entity.ConsoleBlockEntity;
import org.agmas.init.ModAttachments;
import org.agmas.init.ModBlocks;
import org.agmas.init.ModComponents;
import org.agmas.init.ModItems;
import org.agmas.network.OpenPerkyMenuPayload;
import org.jetbrains.annotations.Nullable;

public class ConsoleBlock extends HorizontalDirectionalBlock {

    public static final EnumProperty<ConsoleBlockPart> PART = EnumProperty.create("console_part", ConsoleBlockPart.class);

    public ConsoleBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(PART, ConsoleBlockPart.LOWER).setValue(FACING, Direction.NORTH));
    }

    @Override
    public boolean triggerEvent(BlockState blockState, Level level, BlockPos blockPos, int i, int j) {
        super.triggerEvent(blockState, level, blockPos, i, j);
        BlockEntity blockEntity = level.getBlockEntity(findParent(blockPos,blockState,level));
        if (blockEntity == null) {
            return false;
        }
        return blockEntity.triggerEvent(i, j);
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
        return pos.below();
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext blockPlaceContext) {
        Direction direction = blockPlaceContext.getHorizontalDirection();
        BlockPos blockPos = blockPlaceContext.getClickedPos();
        BlockPos blockPos2 = blockPos.above();
        Level level = blockPlaceContext.getLevel();
        return (level.getBlockState(blockPos2).canBeReplaced(blockPlaceContext) && level.getWorldBorder().isWithinBounds(blockPos2))
                        ? (BlockState) this.defaultBlockState().setValue(FACING, direction) : null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @Nullable LivingEntity livingEntity, ItemStack itemStack) {
        super.setPlacedBy(level, blockPos, blockState, livingEntity, itemStack);
        if (!level.isClientSide()) {
            level.setBlock(blockPos.above(), (BlockState) ModBlocks.CONSOLE_CHILD.defaultBlockState().setValue(PART, ConsoleBlockPart.UPPER).setValue(FACING, blockState.getValue(FACING)), 3);
            BlockPos blockPos2 = blockPos.relative((Direction) blockState.getValue(FACING));
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

        if (!(blockState2.getBlock() instanceof ConsoleBlock) && ((blockState.getValue(PART) == ConsoleBlockPart.LOWER && direction == Direction.UP) || (blockState.getValue(PART) == ConsoleBlockPart.UPPER && direction == Direction.DOWN))) {
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
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
    *///? }
        if (!level.isClientSide()) {
            ConsoleBlockEntity consoleBlockEntity = ((ConsoleBlockEntity) level.getBlockEntity(findParent(pos,state,level)));

            if (player.isShiftKeyDown() && consoleBlockEntity.hasBottleAttached && player.getMainHandItem().isEmpty()) {
                if (consoleBlockEntity.attachedBlood.appliedItems.isEmpty()) {
                    player.addItem(Items.GLASS_BOTTLE.getDefaultInstance());
                } else {
                    ItemStack bottle = ModItems.BLOOD_BOTTLE.getDefaultInstance();
                    ModComponents.BLOOD_COMPONENT.set(bottle, consoleBlockEntity.attachedBlood);
                    consoleBlockEntity.hasBottleAttached = false;
                    consoleBlockEntity.attachedBlood = new BloodstreamAttachment();
                    player.addItem(bottle);
                }
                consoleBlockEntity.hasBottleAttached = false;
                consoleBlockEntity.setChanged();
                return InteractionResult.SUCCESS;
            }
            if (ModComponents.BLOOD_COMPONENT.has(player.getMainHandItem()) && (consoleBlockEntity.hasBottleAttached || player.getMainHandItem().is(ModItems.BLOOD_BOTTLE))) {
                consoleBlockEntity.hasBottleAttached = true;
                for (PerkyItem appliedItem : ModComponents.BLOOD_COMPONENT.get(player.getMainHandItem()).appliedItems) {
                    if (!consoleBlockEntity.attachedBlood.appliedItems.contains(appliedItem)) {
                        consoleBlockEntity.attachedBlood.appliedItems.add(appliedItem);
                    }
                }

                ModComponents.BLOOD_COMPONENT.remove(player.getMainHandItem());
                consoleBlockEntity.setChanged();
                return InteractionResult.CONSUME;
            }
            if (player.getMainHandItem().is(Items.GLASS_BOTTLE) && !consoleBlockEntity.hasBottleAttached) {
                consoleBlockEntity.hasBottleAttached = true;
                player.getMainHandItem().shrink(1);
                consoleBlockEntity.setChanged();
                return InteractionResult.CONSUME;
            }

            if (!ModAttachments.PERKY_ATTACHMENT.has(player)) {
                ModAttachments.PERKY_ATTACHMENT.set(player, new BloodstreamAttachment());
            }
            ModAttachments.PERKY_ATTACHMENT.get(player).usingConsole = pos;
            ServerPlayNetworking.send(((ServerPlayer) player), new OpenPerkyMenuPayload(consoleBlockEntity.attachedBlood));
            consoleBlockEntity.setChanged();
        }
        return InteractionResult.SUCCESS;
    }

    public enum ConsoleBlockPart implements StringRepresentable {
        UPPER("upper"),
        LOWER("lower");


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
