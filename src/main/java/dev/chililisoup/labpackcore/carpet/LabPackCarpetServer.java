package dev.chililisoup.labpackcore.carpet;

import carpet.CarpetExtension;
import carpet.CarpetServer;

public class LabPackCarpetServer implements CarpetExtension {
    @Override
    public String version() {
        return "lab-pack-carpet-server";
    }

    public static void loadExtension() {
        CarpetServer.manageExtension(new LabPackCarpetServer());
    }

    @Override
    public void onGameStarted() {
        LabPackEvents.noop();
    }
}
