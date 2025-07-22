package dev.chililisoup.labpackcore.client;


import dev.chililisoup.labpackcore.LabPackCore;
import net.minecraft.client.KeyMapping;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.lwjgl.glfw.GLFW;

public class LabPackCoreClient {
    public static KeyMapping TOGGLE_ACCESSORIFY_HUD = null;

    public static void init(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(LabPackCoreClient::onInitializeClient);
        modEventBus.addListener(LabPackCoreClient::initKeybindings);

        modContainer.registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
    }

    public static void onInitializeClient(FMLClientSetupEvent event) {
        NeoForge.EVENT_BUS.addListener(LabPackCoreClient::clientTick);
    }

    public static void initKeybindings(RegisterKeyMappingsEvent event) {
        TOGGLE_ACCESSORIFY_HUD = new KeyMapping(
                LabPackCore.MOD_ID + ".key.toggle_accessorify_hud",
                GLFW.GLFW_KEY_H,
                "accessories.key.category.accessories"
        );

        event.register(TOGGLE_ACCESSORIFY_HUD);
    }

    public static void clientTick(ClientTickEvent.Pre event) {
        if (TOGGLE_ACCESSORIFY_HUD.consumeClick()) {
            ClientConfig.SHOW_ACCESSORIFY_HUD.set(!ClientConfig.showAccessorifyHud);
            ClientConfig.SPEC.save();
        }
    }
}
