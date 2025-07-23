package dev.chililisoup.labpackcore.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.chililisoup.labpackcore.LabPackCore;
import net.minecraft.world.inventory.LoomMenu;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(LoomMenu.class)
public abstract class LoomMenuMixin {
    @ModifyVariable(method = "slotsChanged", at = @At("STORE"), ordinal = 1)
    private boolean modifyMaxBannerPatterns(boolean base, @Local BannerPatternLayers patternLayers) {
        return patternLayers.layers().size() >= LabPackCore.MAX_BANNER_PATTERNS;
    }
}
