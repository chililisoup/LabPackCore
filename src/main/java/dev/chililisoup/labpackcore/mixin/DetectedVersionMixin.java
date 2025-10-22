package dev.chililisoup.labpackcore.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.DetectedVersion;
import net.minecraft.world.level.storage.DataVersion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(DetectedVersion.class)
public abstract class DetectedVersionMixin {
    @Unique private static DataVersion labPackCore$overwriteWorldVersion(String series, Operation<DataVersion> original) {
        return original.call(3956, series); // Vanilla is 3955
    }

    @WrapOperation(method = "<init>()V", at = @At(value = "NEW", target = "Lnet/minecraft/world/level/storage/DataVersion;"))
    private static DataVersion modifyVersion(int version, String series, Operation<DataVersion> original) {
        return labPackCore$overwriteWorldVersion(series, original);
    }

    @WrapOperation(method = "<init>(Lcom/google/gson/JsonObject;)V", at = @At(value = "NEW", target = "Lnet/minecraft/world/level/storage/DataVersion;"))
    private static DataVersion modifyGatheredVersion(int version, String series, Operation<DataVersion> original) {
        return labPackCore$overwriteWorldVersion(series, original);
    }
}
