package dev.chililisoup.labpackcore.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.chililisoup.labpackcore.LabPackCore;
import net.minecraft.client.gui.screens.inventory.LoomScreen;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LoomScreen.class)
public abstract class LoomScreenMixin {
    @Shadow private boolean hasMaxPatterns;

    @Redirect(
            method = "containerChanged", at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/gui/screens/inventory/LoomScreen;hasMaxPatterns:Z",
            opcode = Opcodes.PUTFIELD
    ))
    private void modifyMaxBannerPatterns(LoomScreen instance, boolean value, @Local BannerPatternLayers patternLayers) {
        this.hasMaxPatterns = patternLayers.layers().size() >= LabPackCore.MAX_BANNER_PATTERNS;
    }
}
