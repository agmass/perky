package org.agmas.client;

//? if >=1.21.11 {
import net.fabricmc.fabric.api.client.rendering.v1.RenderStateDataKey;

public class PerkyRenderKeys {
    public static RenderStateDataKey<Boolean> shouldRenderHead = RenderStateDataKey.create();
    public static RenderStateDataKey<Boolean> shouldRenderBody = RenderStateDataKey.create();
    public static RenderStateDataKey<Boolean> shouldRenderMainArm = RenderStateDataKey.create();
    public static RenderStateDataKey<Boolean> shouldRenderOffArm = RenderStateDataKey.create();
    public static RenderStateDataKey<Boolean> shouldRenderLeftLeg = RenderStateDataKey.create();
    public static RenderStateDataKey<Boolean> shouldRenderRightLeg = RenderStateDataKey.create();
}

//? }