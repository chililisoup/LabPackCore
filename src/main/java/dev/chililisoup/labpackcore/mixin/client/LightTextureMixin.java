package dev.chililisoup.labpackcore.mixin.client;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(LightTexture.class)
public abstract class LightTextureMixin {
    @WrapOperation(method = "updateLightTexture", at = @At(value = "INVOKE", target = "Ljava/lang/Double;floatValue()F", ordinal = 1))
    private float fixNetherAndEndBrightness(Double instance, Operation<Float> original, @Local ClientLevel clientLevel) {
        ResourceKey<Level> dimension = clientLevel.dimension();
        if (dimension.equals(ClientLevel.NETHER) || dimension.equals(ClientLevel.END))
            return 1F;
        return original.call(instance);
    }
}
