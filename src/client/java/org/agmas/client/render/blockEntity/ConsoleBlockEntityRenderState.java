package org.agmas.client.render.blockEntity;

//? if >=1.21.11
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;

public class ConsoleBlockEntityRenderState
    //? if >=1.21.11
    extends BlockEntityRenderState
{
    public boolean shouldShowFillant = false;
    public Direction direction;
    public long animationTimestamp = 0;
    public float tickDelta = 0;
}
