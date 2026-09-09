package org.agmas.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
//? if <=1.21.1
//import net.fabricmc.fabric.api.object.builder.v1.client.model.FabricModelPredicateProviderRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.blockentity.ShulkerBoxRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.agmas.client.render.blockEntity.ConsoleBlockEntityRenderer;
import org.agmas.client.screen.PerkyScreen;
import org.agmas.init.ModAttachments;
//? if <1.21.1
//import org.agmas.network.OldVersionPerkyNetworkingPayload;
import org.agmas.init.ModComponents;
import org.agmas.init.ModEntities;
import org.agmas.network.OpenPerkyMenuPayload;
import org.agmas.porting.PPIdentifier;

public class PerkyClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {

		BlockEntityRenderers.register(ModEntities.CONSOLE_BLOCK_ENTITY, ConsoleBlockEntityRenderer::new);
		//? if >=1.21.1 {
		ClientPlayNetworking.registerGlobalReceiver(OpenPerkyMenuPayload.ID, ((payload, context) -> {
			//? if >1.21.1
			context.client().setScreenAndShow(new PerkyScreen(payload.options(), Component.literal("fractions of inskibidi")));
			//? if <=1.21.1
			//context.client().setScreen(new PerkyScreen(payload.options(), Component.literal("fractions of inskibidi")));

		}));
		//? } else {
		/*ClientPlayNetworking.registerGlobalReceiver(OpenPerkyMenuPayload.PACKET_TYPE, ((openPerkyMenuPayload, localPlayer, packetSender) -> {
			Minecraft.getInstance().setScreen(new PerkyScreen(openPerkyMenuPayload.options(), Component.literal("ohio")));
		}));
		ClientPlayNetworking.registerGlobalReceiver(OldVersionPerkyNetworkingPayload.PACKET_TYPE, ((payload, localPlayer, packetSender) -> {
			ModAttachments.PERKY_ATTACHMENT.set(localPlayer.level.getEntity(payload.id()), payload.playerAttachment());
		}));
		*///? }

		//? if <=1.21.1 {
		/*FabricModelPredicateProviderRegistry.register(PPIdentifier.of("filled_with_blood").id, ((itemStack, clientLevel, livingEntity, i) -> {
			return ModComponents.BLOOD_COMPONENT.has(itemStack) ? 1 : 0;
		}));
		*///? }
	}
}