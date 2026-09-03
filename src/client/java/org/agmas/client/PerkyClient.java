package org.agmas.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import org.agmas.client.screen.PerkyScreen;
import org.agmas.init.ModAttachments;
//? if <1.21.1
import org.agmas.network.OldVersionPerkyNetworkingPayload;
import org.agmas.network.OpenPerkyMenuPayload;

public class PerkyClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		//? if >=1.21.1 {
		/*ClientPlayNetworking.registerGlobalReceiver(OpenPerkyMenuPayload.ID, ((payload, context) -> {
			//? if >1.21.1
			//context.client().setScreenAndShow(new PerkyScreen(Component.literal("fractions of inskibidi")));
			//? if <=1.21.1
			context.client().setScreen(new PerkyScreen(Component.literal("fractions of inskibidi")));

		}));
		*///? } else {
		ClientPlayNetworking.registerGlobalReceiver(OpenPerkyMenuPayload.PACKET_TYPE, ((openPerkyMenuPayload, localPlayer, packetSender) -> {
			Minecraft.getInstance().setScreen(new PerkyScreen(Component.literal("ohio")));
		}));
		ClientPlayNetworking.registerGlobalReceiver(OldVersionPerkyNetworkingPayload.PACKET_TYPE, ((payload, localPlayer, packetSender) -> {
			localPlayer.level().getEntity(payload.id()).setAttached(ModAttachments.PERKY_ATTACHMENT, payload.playerAttachment());
		}));
		//? }
	}
}