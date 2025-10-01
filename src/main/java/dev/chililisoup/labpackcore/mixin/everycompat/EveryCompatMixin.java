package dev.chililisoup.labpackcore.mixin.everycompat;

import net.mehvahdjukaar.every_compat.EveryCompat;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EveryCompat.class)
public abstract class EveryCompatMixin {
    @Inject(method = "registerItemsToTabs", at = @At("HEAD"), cancellable = true)
    private static void preventAddingItemsToTabs(RegHelper.ItemToTabEvent event, CallbackInfo ci) {
        ci.cancel();
    }
}
