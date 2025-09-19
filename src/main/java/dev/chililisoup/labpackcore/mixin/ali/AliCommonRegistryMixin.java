package dev.chililisoup.labpackcore.mixin.ali;

import com.yanny.ali.manager.AliCommonRegistry;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Set;

@Mixin(AliCommonRegistry.class)
public abstract class AliCommonRegistryMixin {
    @Unique private static final Set<String> UNSAFE_ENTITIES = Set.of(
            "entity.supplementaries.hat_stand",
            "entity.create.carriage_contraption"
    );

    @Inject(method = "createEntities", at = @At("HEAD"), cancellable = true)
    private void ignoreUnsafeEntities(EntityType<?> type, Level level, CallbackInfoReturnable<List<Entity>> cir) {
        if (UNSAFE_ENTITIES.contains(type.getDescriptionId()))
            cir.setReturnValue(List.of());
    }
}
