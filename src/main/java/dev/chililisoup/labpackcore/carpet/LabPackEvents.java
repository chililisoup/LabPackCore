package dev.chililisoup.labpackcore.carpet;

import carpet.script.CarpetEventServer;
import carpet.script.value.BlockValue;
import carpet.script.value.EntityValue;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;

import java.util.Arrays;

public class LabPackEvents extends CarpetEventServer.Event {
    public static void noop() {} //to load events before scripts do

    public LabPackEvents(String name, int reqArgs, boolean isGlobalOnly) {
        super(name, reqArgs, isGlobalOnly);
    }

    public void onPlayerEditsSupplementariesSign(ServerPlayer player, BlockPos pos) {}
    public static final LabPackEvents PLAYER_EDITS_SUPPLEMENTARIES_SIGN = new LabPackEvents("player_edits_supplementaries_sign", 2, false) {
        public void onPlayerEditsSupplementariesSign(ServerPlayer player, BlockPos pos) {
            handler.call(() ->
                    Arrays.asList(
                            new EntityValue(player),
                            new BlockValue(player.serverLevel(), pos)
                    ), player::createCommandSourceStack
            );
        }
    };
}
