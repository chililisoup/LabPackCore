package dev.chililisoup.labpackcore.mixin.client;

import com.llamalad7.mixinextras.sugar.Local;
import dev.chililisoup.labpackcore.client.ClientConfig;
import it.unimi.dsi.fastutil.objects.ObjectIntImmutablePair;
import me.pajic.accessorify.Main;
import me.pajic.accessorify.gui.InfoOverlays;
import me.pajic.accessorify.util.ModUtil;
import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.client.event.RenderGuiEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(InfoOverlays.class)
public abstract class AccessorifyInfoOverlaysMixin {
    @Shadow private static final List<ObjectIntImmutablePair<Component>> renderList = new ArrayList<>();
    @Shadow private static final Minecraft MC = Minecraft.getInstance();

    @Shadow private static void prepareCompassOverlay(boolean shouldObfuscate) {}

    @Inject(method = "renderInfoOverlays", at = @At("HEAD"), cancellable = true)
    private static void hideInfoOverlays(RenderGuiEvent.Post event, CallbackInfo ci) {
        if (!ClientConfig.showAccessorifyHud)
            ci.cancel();
    }

    @Inject(
            method = "renderInfoOverlays", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/multiplayer/ClientLevel;dimension()Lnet/minecraft/resources/ResourceKey;",
            ordinal = 1,
            shift = At.Shift.AFTER
    ))
    private static void addCoordinatesOverlay(RenderGuiEvent.Post event, CallbackInfo ci, @Local(ordinal = 0) boolean shouldObfuscateCompass) {
        if (MC.player == null) return;

        boolean altimeter = ModUtil.accessoryEquipped(MC.player, ModRegistry.ALTIMETER_ITEM.get());
        boolean compass = ModUtil.accessoryEquipped(MC.player, Items.COMPASS);
        if (!altimeter && !compass) return;

        if ((altimeter && compass) || (compass && shouldObfuscateCompass)) {
            prepareCompassOverlay(shouldObfuscateCompass);
            return;
        }

        BlockPos blockPos = MC.player.blockPosition();
        Component coordinates = compass ?
                Component.translatable("gui.accessorify.coordinates_xz", blockPos.getX(), blockPos.getZ()) :
                Component.translatable("gui.accessorify.coordinates_y", blockPos.getY());

        renderList.add(new ObjectIntImmutablePair<>(coordinates, 0xffffff));
        if (!compass) return;

        // copied from accessorify to avoid extra slot checks
        if (Main.CONFIG.infoOverlaySettings.overlayFields.direction.get()) {
            Component direction = Component.translatable("gui.accessorify.facing", MC.player.getDirection().getName());
            renderList.add(new ObjectIntImmutablePair<>(direction, 0xffffff));
        }

        if (Main.CONFIG.infoOverlaySettings.overlayFields.biome.get()) {
            ResourceLocation biome = MC.player.level().getBiome(blockPos).unwrap().map(
                    key -> key != null ? key.location() : null, unknown -> null
            );
            Component biomeName = biome == null ?
                    Component.literal("Unknown") :
                    Component.translatable("biome." + biome.getNamespace() + "." + biome.getPath());
            renderList.add(new ObjectIntImmutablePair<>(biomeName, 0xffffff));
        }
    }

    @Redirect(
            method = "renderInfoOverlays", at = @At(
            value = "INVOKE",
            target = "Lme/pajic/accessorify/util/ModUtil;accessoryEquipped(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/item/Item;)Z",
            ordinal = 0
    ))
    private static boolean preventDefaultCompassOverlay(LivingEntity player, Item compass) {
        return false;
    }
}
