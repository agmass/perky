package org.agmas.mixin;

import net.minecraft.world.entity.LivingEntity;
import org.agmas.Perky;
import org.agmas.accessors.PlayerAccessor;
import org.agmas.attachment.PerkyItem;

//? if <1.21.1
//import org.agmas.network.OldVersionPerkyNetworkingPayload;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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
			if (attachment.modifiesVisibleParts()) {
				mainArmDisabled = !attachment.shouldRenderPart("mainArm");
				offArmDisabled = !attachment.shouldRenderPart("offArm");
				headDisabled = !attachment.shouldRenderPart("head");
				bodyDisabled = !attachment.shouldRenderPart("body");
				leftLegDisabled = !attachment.shouldRenderPart("leftLeg");
				rightLegDisabled = !attachment.shouldRenderPart("rightLeg");
			}

			attachment.tick(p);
		}

		//? if <=1.20.4 {
		/*if (p.hasAttached(ModAttachments.PERKY_ATTACHMENT) && !p.level().isClientSide()) {
			PerkyPlayerAttachment playerAttachment = p.getAttached(ModAttachments.PERKY_ATTACHMENT);
			for (Player player : p.level().players()) {
				if (!playerAttachment.networkedTo.contains(player.getUUID())) {
					if (player.distanceTo(p) < 250) {
						playerAttachment.networkedTo.add(player.getUUID());
						ServerPlayNetworking.send((ServerPlayer) player, new OldVersionPerkyNetworkingPayload(p.getId(),playerAttachment));
					}
				}
			}
		}
		*///? }
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