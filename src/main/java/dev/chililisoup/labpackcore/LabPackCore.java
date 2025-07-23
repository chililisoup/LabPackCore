package dev.chililisoup.labpackcore;

import com.mojang.logging.LogUtils;
import dev.chililisoup.labpackcore.carpet.LabPackCarpetServer;
import dev.chililisoup.labpackcore.client.LabPackCoreClient;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import org.slf4j.Logger;

@Mod(LabPackCore.MOD_ID)
public class LabPackCore {
    public static final String MOD_ID = "labpackcore";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final int MAX_BANNER_PATTERNS = 8;

    public static ResourceLocation loc(String id) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
    }

    public LabPackCore(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::onInitialize);

        if (FMLEnvironment.dist == Dist.CLIENT)
            LabPackCoreClient.init(modEventBus, modContainer);
    }

    public void onInitialize(FMLCommonSetupEvent event) {
        LabPackCarpetServer.loadExtension();
    }
}
