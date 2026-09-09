package org.agmas.mixin;

import net.minecraft.network.protocol.game.ServerGamePacketListener;
import net.minecraft.network.protocol.game.ServerboundPlayerActionPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.agmas.Perky;
import org.agmas.accessors.PlayerAccessor;
import org.agmas.attachment.PerkyItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerGamePacketListenerImpl.class)
public class CannotOffhandMixin {

	@Shadow
	public ServerPlayer player;

	@Inject(method = "handlePlayerAction", at = @At("HEAD"), cancellable = true)
	public void noOffhandItem(ServerboundPlayerActionPacket serverboundPlayerActionPacket, CallbackInfo ci) {
		if (serverboundPlayerActionPacket.getAction().equals(ServerboundPlayerActionPacket.Action.SWAP_ITEM_WITH_OFFHAND)) {
			for (PerkyItem attachment : Perky.getAttachments(player)) {
				if (!attachment.shouldRenderPart("offArm")) {
					ci.cancel();
					return;
				}
			}
		}
	}
}