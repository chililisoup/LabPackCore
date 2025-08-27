package dev.chililisoup.labpackcore.mixin.client;

import dev.chililisoup.labpackcore.LabPackCore;
import net.minecraft.client.gui.screens.inventory.LoomScreen;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(LoomScreen.class)
public abstract class LoomScreenMixin {
    @Redirect(
            method = "containerChanged", at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;size()I"
    ))
    private int modifyMaxBannerPatterns(List<BannerPatternLayers.Layer> layers) {
        return layers.size() >= LabPackCore.MAX_BANNER_PATTERNS ? layers.size() : 0;
    }
}
