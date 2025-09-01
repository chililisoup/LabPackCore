package dev.chililisoup.labpackcore.mixin.supplementaries;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProvider;
import com.misterpemodder.shulkerboxtooltip.api.provider.PreviewProviderRegistry;
import net.mehvahdjukaar.supplementaries.integration.ShulkerBoxTooltipCompat;
import net.mehvahdjukaar.suppsquared.SuppSquared;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import org.apache.commons.lang3.ArrayUtils;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Supplier;

@Mixin(ShulkerBoxTooltipCompat.class)
public abstract class ShulkerBoxTooltipCompatMixin {
    @WrapOperation(
            method = "registerProviders", at = @At(
            value = "INVOKE",
            target = "Lcom/misterpemodder/shulkerboxtooltip/api/provider/PreviewProviderRegistry;register(Lnet/minecraft/resources/ResourceLocation;Lcom/misterpemodder/shulkerboxtooltip/api/provider/PreviewProvider;[Lnet/minecraft/world/item/Item;)V",
            ordinal = 1
    ))
    private void addColoredSacks(PreviewProviderRegistry registry, ResourceLocation res, PreviewProvider previewProvider, Item[] items, Operation<Void> original) {
        original.call(registry, res, previewProvider, ArrayUtils.addAll(
                items,
                SuppSquared.SACK_ITEMS.values().stream().map(Supplier::get).toArray(Item[]::new)
        ));
    }
}
