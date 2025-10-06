package dev.chililisoup.labpackcore.reg;

import de.larsensmods.stl_backport.item.STLItems;
import net.mehvahdjukaar.moonlight.api.platform.RegHelper;
import net.minecraft.core.dispenser.ProjectileDispenseBehavior;

public class ModDispenserBehaviors {
    public static void init() {
        RegHelper.addDynamicDispenserBehaviorRegistration(event -> {
            event.register(STLItems.BLUE_EGG.get(), new ProjectileDispenseBehavior(STLItems.BLUE_EGG.get()));
            event.register(STLItems.BROWN_EGG.get(), new ProjectileDispenseBehavior(STLItems.BROWN_EGG.get()));
        });
    }
}
