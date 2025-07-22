package dev.chililisoup.labpackcore.carpet;

import carpet.script.CarpetEventServer;

public class LabPackEvents extends CarpetEventServer.Event {
    public static void noop() {} //to load events before scripts do

    public LabPackEvents(String name, int reqArgs, boolean isGlobalOnly) {
        super(name, reqArgs, isGlobalOnly);
    }
}
