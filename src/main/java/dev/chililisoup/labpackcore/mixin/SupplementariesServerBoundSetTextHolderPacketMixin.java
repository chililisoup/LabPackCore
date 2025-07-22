package dev.chililisoup.labpackcore.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.mehvahdjukaar.supplementaries.common.block.ITextHolderProvider;
import net.mehvahdjukaar.supplementaries.common.network.ServerBoundSetTextHolderPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.FilteredText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

import static dev.chililisoup.labpackcore.carpet.LabPackEvents.PLAYER_EDITS_SUPPLEMENTARIES_SIGN;

@Mixin(ServerBoundSetTextHolderPacket.class)
public abstract class SupplementariesServerBoundSetTextHolderPacketMixin {
    @WrapOperation(method = "updateSignText", at = @At(
            value = "INVOKE",
            target = "Lnet/mehvahdjukaar/supplementaries/common/block/ITextHolderProvider;tryAcceptingClientText(Lnet/minecraft/core/BlockPos;Lnet/minecraft/server/level/ServerPlayer;Ljava/util/List;)Z"
    ))
    private boolean signUpdateEvent(ITextHolderProvider textHolder, BlockPos blockPos, ServerPlayer player, List<List<FilteredText>> signText, Operation<Boolean> original) {
        if (!original.call(textHolder, blockPos, player, signText))
            return false;

        PLAYER_EDITS_SUPPLEMENTARIES_SIGN.onPlayerEditsSupplementariesSign(player, blockPos);
        return true;
    }
}
