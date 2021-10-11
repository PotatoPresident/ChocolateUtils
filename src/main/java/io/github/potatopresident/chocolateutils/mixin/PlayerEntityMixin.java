package io.github.potatopresident.chocolateutils.mixin;

import io.github.potatopresident.chocolateutils.ChocolateUtils;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {

    @Inject(method = "checkFallFlying", at = @At("HEAD"), cancellable = true)
    private void disableFallFlyingInBlacklist(CallbackInfoReturnable<Boolean> cir) {
        if (ChocolateUtils.config.elytraBlacklist.contains(((PlayerEntity) (Object) this).world.getRegistryKey().getValue().toString())) {
            cir.setReturnValue(false);
        }
    }
}
