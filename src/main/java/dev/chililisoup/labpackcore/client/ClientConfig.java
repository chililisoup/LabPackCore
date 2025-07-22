package dev.chililisoup.labpackcore.client;

import dev.chililisoup.labpackcore.LabPackCore;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = LabPackCore.MOD_ID)
public class ClientConfig {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue SHOW_ACCESSORIFY_HUD = BUILDER
            .comment("Whether to render the accessorify info hud")
            .define("showAccessorifyHud", true);

    public static final ModConfigSpec SPEC = BUILDER.build();

    public static boolean showAccessorifyHud;

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        showAccessorifyHud = SHOW_ACCESSORIFY_HUD.get();
    }
}
