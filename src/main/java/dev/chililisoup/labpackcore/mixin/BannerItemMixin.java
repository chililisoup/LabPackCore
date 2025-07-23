package dev.chililisoup.labpackcore.mixin;

import dev.chililisoup.labpackcore.LabPackCore;
import net.minecraft.world.item.BannerItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(BannerItem.class)
public abstract class BannerItemMixin {
    @ModifyConstant(method = "appendHoverTextFromBannerBlockEntityTag", constant = @Constant(intValue = 6))
    private static int modifyMaxBannerPatterns(int base) {
        return LabPackCore.MAX_BANNER_PATTERNS;
    }
}
