package dev.chililisoup.labpackcore.accessories;

import io.wispforest.accessories.api.client.AccessoriesRendererRegistry;
import io.wispforest.accessories.api.slot.SlotReference;
import me.pajic.accessorify.accessories.SlotCopyingAccessory;
import me.pajic.accessorify.util.MultiVersionUtil;
import net.mehvahdjukaar.supplementaries.reg.ModRegistry;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.ModifyRegistriesEvent;
import net.neoforged.neoforge.registries.callback.AddCallback;

public class AltimeterAccessory implements SlotCopyingAccessory {
    @SubscribeEvent
    public static void init(ModifyRegistriesEvent event) {
        event.getRegistry(Registries.ITEM).addCallback((AddCallback<Item>) (registry, id, key, value) -> {
            if (key.location().equals(MultiVersionUtil.parse("supplementaries:altimeter"))) {
                MultiVersionUtil.registerAccessory(value, new AltimeterAccessory());
            }
        });
    }

    @SubscribeEvent
    public static void clientInit(ModifyRegistriesEvent event) {
        event.getRegistry(Registries.ITEM).addCallback((AddCallback<Item>) (registry, id, key, value) -> {
            if (key.location().equals(MultiVersionUtil.parse("supplementaries:altimeter"))) {
                AccessoriesRendererRegistry.registerNoRenderer(value);
            }
        });
    }

    @Override
    public String getPath() {
        return "add_wrist_4";
    }

    @Override
    public String getSlot() {
        return "wrist";
    }

    @Override
    public boolean canEquip(ItemStack stack, SlotReference reference) {
        return !MultiVersionUtil.isAnotherEquipped(stack, reference, ModRegistry.ALTIMETER_ITEM.get());
    }
}
