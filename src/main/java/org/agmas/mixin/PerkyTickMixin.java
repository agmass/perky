package org.agmas.mixin;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.agmas.Perky;
import org.agmas.accessors.PlayerAccessor;
import org.agmas.attachment.BloodstreamAttachment;
import org.agmas.attachment.PerkyItem;

//? if <1.21.1
//import org.agmas.network.OldVersionPerkyNetworkingPayload;
import org.agmas.init.ModAttachments;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(LivingEntity.class)
public class PerkyTickMixin implements PlayerAccessor {

	@Unique
    public boolean mainArmDisabled = false;
	@Unique
	public boolean offArmDisabled = false;
	@Unique
	public boolean headDisabled = false;
	@Unique
	public boolean bodyDisabled = false;
	@Unique
	public boolean leftLegDisabled = false;
	@Unique
	public boolean rightLegDisabled = false;
	@Unique
	public int timeSinceLastDamage = 0;

	@Inject(method = "tick", at = @At("HEAD"))
	public void noOffhandItem(CallbackInfo ci) {
		LivingEntity p = ((LivingEntity) (Object)this);
		timeSinceLastDamage++;
		mainArmDisabled = false;
		offArmDisabled = false;
		headDisabled = false;
		bodyDisabled = false;
		leftLegDisabled = false;
		rightLegDisabled = false;
		for (PerkyItem attachment : Perky.getAttachments(p)) {
			if (attachment == null) continue;
			if (attachment.modifiesVisibleParts()) {
				mainArmDisabled = !attachment.shouldRenderPart("mainArm");
				offArmDisabled = !attachment.shouldRenderPart("offArm");
				headDisabled = !attachment.shouldRenderPart("head");
				bodyDisabled = !attachment.shouldRenderPart("body");
				leftLegDisabled = !attachment.shouldRenderPart("leftLeg");
				rightLegDisabled = !attachment.shouldRenderPart("rightLeg");
			}

			if (!p.level
					//? if >1.19
					()
					.isClientSide())
				attachment.tick(p);
		}
	}

	@Override
	public boolean perky$mainArmDisabled() {
		return mainArmDisabled;
	}

	@Override
	public boolean perky$offArmDisabled() {
		return offArmDisabled;
	}

	@Override
	public boolean perky$headDisabled() {
		return headDisabled;
	}

	@Override
	public boolean perky$bodyDisabled() {
		return bodyDisabled;
	}

	@Override
	public boolean perky$leftLegDisabled() {
		return leftLegDisabled;
	}

	@Override
	public boolean perky$rightLegDisabled() {
		return rightLegDisabled;
	}

	@Override
	public int perky$timeSinceLastDamage() {
		return timeSinceLastDamage;
	}

	@Override
	public void perky$setTimeSinceLastDamage(int i) {
		timeSinceLastDamage = i;
	}
}