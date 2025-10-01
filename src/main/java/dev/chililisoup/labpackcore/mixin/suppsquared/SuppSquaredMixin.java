package dev.chililisoup.labpackcore.mixin.suppsquared;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.mehvahdjukaar.suppsquared.SuppSquared;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemContainerContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SuppSquared.class)
public abstract class SuppSquaredMixin {
    @WrapOperation(method = "lambda$static$23", remap = false, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item$Properties;stacksTo(I)Lnet/minecraft/world/item/Item$Properties;"))
    private static Item.Properties fixSackComponents(Item.Properties properties, int maxStackSize, Operation<Item.Properties> original) {
        return original.call(properties, maxStackSize).component(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
    }
}
