package org.agmas.block.entity;

import com.mojang.serialization.Codec;
import net.fabricmc.loader.impl.util.log.Log;
import net.fabricmc.loader.impl.util.log.LogCategory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
//? if >1.19 {
/*import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
*///? }
import org.agmas.attachment.BloodstreamAttachment;
import org.agmas.block.ConsoleBlock;
import org.agmas.init.ModEntities;
import org.jetbrains.annotations.Nullable;

public class ConsoleBlockEntity extends BlockEntity {

    public boolean shouldRenderModel = false;
    public Direction direction;
    public BloodstreamAttachment attachedBlood = new BloodstreamAttachment();
    public boolean hasBottleAttached = false;

    public ConsoleBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModEntities.CONSOLE_BLOCK_ENTITY, blockPos, blockState);
        direction = blockState.getValue(HorizontalDirectionalBlock.FACING);
        shouldRenderModel = blockState.getValue(ConsoleBlock.PART) == ConsoleBlock.ConsoleBlockPart.LOWER_LEFT;
    }

    //? if >1.19 {
    /*@Override
    protected void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.store("hasBottleAttached", Codec.BOOL, hasBottleAttached);
        valueOutput.store("bloodstream", BloodstreamAttachment.CODEC, attachedBlood);
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        return saveWithoutMetadata(provider);
    }

    @Override
    protected void loadAdditional(ValueInput valueInput) {
        super.loadAdditional(valueInput);
        attachedBlood = valueInput.read("bloodstream", BloodstreamAttachment.CODEC).orElseGet(BloodstreamAttachment::new);
        hasBottleAttached = valueInput.getBooleanOr("hasBottleAttached", false);
    }
    *///? } else {

    @Override
    protected void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        compoundTag.putBoolean("hasBottleAttached", hasBottleAttached);
        compoundTag.put("bloodstream", BloodstreamAttachment.CODEC.encodeStart(NbtOps.INSTANCE, attachedBlood).get().left().get());
    }

    @Override
    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        if (compoundTag.contains("bloodstream")) {
            attachedBlood = BloodstreamAttachment.CODEC.decode(NbtOps.INSTANCE, compoundTag.get("bloodstream")).get().left().get().getFirst();
            hasBottleAttached = compoundTag.getBoolean("hasBottleAttached");
        } else {
            attachedBlood = new BloodstreamAttachment();
            hasBottleAttached = false;
        }
    }

    @Override
    public CompoundTag getUpdateTag() {
        return saveWithoutMetadata();
    }

    @Override
    public @Nullable Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
    //? }
}
