package dev.chililisoup.labpackcore.mixin;

import dev.chililisoup.labpackcore.LabPackCore;
import net.mehvahdjukaar.supplementaries.common.items.crafting.FlagFromBannerRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FlagFromBannerRecipe.class)
public abstract class SupplementariesFlagFromBannerRecipeMixin {
    @Inject(method = "getMaxBannerPatterns", at = @At("HEAD"), cancellable = true)
    private static void modifyMaxBannerPatterns(CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(LabPackCore.MAX_BANNER_PATTERNS);
    }
}
