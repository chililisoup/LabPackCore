package dev.chililisoup.labpackcore.mixin.crittersandcompanions;

import com.github.eterdelta.crittersandcompanions.entity.GrapplingHookEntity;
import dev.chililisoup.labpackcore.inject.ICACGrapplingHookEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(GrapplingHookEntity.class)
public abstract class GrapplingHookEntityMixin implements ICACGrapplingHookEntity {
    @Shadow protected boolean isStick;

    @Override
    public boolean labpackcore$getStick() {
        return this.isStick;
    }
}
