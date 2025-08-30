package dev.chililisoup.labpackcore.mixin.amendments.client;

import net.mehvahdjukaar.amendments.AmendmentsClient;
import net.mehvahdjukaar.amendments.client.ClientResourceGenerator;
import net.mehvahdjukaar.moonlight.api.resources.pack.ResourceSink;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientResourceGenerator.class)
public abstract class ClientResourceGeneratorMixin {
    @Inject(method = "generateSignBlockModels", at = @At("TAIL"))
    private void addNaturesSpiritPaperSign(ResourceManager manager, ResourceSink sink, CallbackInfo ci) {
        Block sign = BuiltInRegistries.BLOCK.getOptional(
                ResourceLocation.fromNamespaceAndPath("natures_spirit", "paper_sign")
        ).orElse(null);
        Block wallSign = BuiltInRegistries.BLOCK.getOptional(
                ResourceLocation.fromNamespaceAndPath("natures_spirit", "paper_wall_sign")
        ).orElse(null);

        if (sign != null && wallSign != null) {
            AmendmentsClient.SIGN_THAT_WE_RENDER_AS_BLOCKS.add(sign);
            AmendmentsClient.SIGN_THAT_WE_RENDER_AS_BLOCKS.add(wallSign);
        }
    }
}
