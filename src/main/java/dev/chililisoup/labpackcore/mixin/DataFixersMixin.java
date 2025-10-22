package dev.chililisoup.labpackcore.mixin;

import com.mojang.datafixers.DataFixerBuilder;
import com.mojang.datafixers.schemas.Schema;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.util.datafix.fixes.BlockRenameFix;
import net.minecraft.util.datafix.fixes.ItemRenameFix;
import net.minecraft.util.datafix.schemas.NamespacedSchema;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Arrays;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@Mixin(DataFixers.class)
public abstract class DataFixersMixin {
    @Final @Shadow private static final BiFunction<Integer, Schema, Schema> SAME_NAMESPACED = NamespacedSchema::new;

    @Shadow private static UnaryOperator<String> createRenamer(Map<String, String> renameMap) {
        throw new AssertionError();
    }

    @Inject(method = "addFixers", at = @At("TAIL"))
    private static void addLabFixers(DataFixerBuilder builder, CallbackInfo ci) {
        Schema labSchema3956 = builder.addSchema(3956, SAME_NAMESPACED);

        UnaryOperator<String> woodGoodToNaturesDelight = createRenamer(Arrays.stream(new String[]{
                "redwood",
                "sugi",
                "wisteria",
                "fir",
                "willow",
                "aspen",
                "maple",
                "cypress",
                "olive",
                "joshua",
                "ghaf",
                "palo_verde",
                "coconut",
                "cedar",
                "larch",
                "mahogany",
                "saxaul"
        }).collect(Collectors.toMap(
                wood -> "everycomp:fd/natures_spirit/" + wood + "_cabinet",
                wood -> "natures_delight:" + wood + "_cabinet"
        )));
        String woodGoodToNaturesDelightName = "Migrate Nature's Spirit's cabinets from Wood Good to Nature's Delight";
        builder.addFixer(BlockRenameFix.create(labSchema3956, woodGoodToNaturesDelightName, woodGoodToNaturesDelight));
        builder.addFixer(ItemRenameFix.create(labSchema3956, woodGoodToNaturesDelightName, woodGoodToNaturesDelight));
    }
}
