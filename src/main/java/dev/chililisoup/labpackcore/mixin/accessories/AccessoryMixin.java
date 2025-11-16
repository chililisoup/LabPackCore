package dev.chililisoup.labpackcore.mixin.accessories;

import com.imoonday.soulbound.Soulbound;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import io.wispforest.accessories.api.Accessory;
import io.wispforest.accessories.api.DropRule;
import io.wispforest.accessories.api.slot.SlotReference;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Accessory.class)
@Debug(export = true)
public interface AccessoryMixin {
    @WrapMethod(method = "getDropRule")
    private DropRule keepSoulbound(ItemStack stack, SlotReference reference, DamageSource source, Operation<DropRule> original) {
        return Soulbound.hasSoulbound(reference.entity().level(), stack) ?
                DropRule.KEEP :
                original.call(stack, reference, source);
    }
}
