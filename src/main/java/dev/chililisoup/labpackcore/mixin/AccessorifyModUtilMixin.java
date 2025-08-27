package dev.chililisoup.labpackcore.mixin;

import me.pajic.accessorify.util.ModUtil;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModUtil.class)
public abstract class AccessorifyModUtilMixin {
    @Inject(method = "isHoldingProjectileWeapon", at = @At("HEAD"), cancellable = true)
    private static void disableProjectileAccessoryWidgets(Player player, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(false);
    }
}
