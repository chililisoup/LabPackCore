package dev.chililisoup.labpackcore.mixin;

import com.github.eterdelta.crittersandcompanions.extension.IGrapplingState;
import com.github.eterdelta.crittersandcompanions.item.GrapplingHookItem;
import com.llamalad7.mixinextras.sugar.Local;
import dev.chililisoup.labpackcore.inject.ICACGrapplingHookEntity;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.event.EventHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GrapplingHookItem.class)
public abstract class CACGrapplingHookItemMixin {
    @ModifyArg(method = "<init>", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;<init>(Lnet/minecraft/world/item/Item$Properties;)V"))
    private static Item.Properties modifyProperties(Item.Properties properties) {
        return properties.durability(64);
    }

    @Inject(
            method = "use", at = @At(
            value = "INVOKE",
            target = "Lcom/github/eterdelta/crittersandcompanions/entity/GrapplingHookEntity;pull()V"
    ))
    private void damageItem(
            Level level,
            Player player,
            InteractionHand interactionHand,
            CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir,
            @Local IGrapplingState grapplingState,
            @Local ItemStack stack
    ) {
        if (level.isClientSide) return;
        if (!((ICACGrapplingHookEntity) grapplingState.getHook()).labpackcore$getStick()) return;

        ItemStack original = stack.copy();
        stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(interactionHand));
        if (stack.isEmpty()) {
            EventHooks.onPlayerDestroyItem(player, original, interactionHand);
        }
    }
}
