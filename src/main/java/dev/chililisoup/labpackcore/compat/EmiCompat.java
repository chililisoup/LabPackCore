package dev.chililisoup.labpackcore.compat;

import com.illusivesoulworks.bedspreads.common.BedspreadsData;
import com.illusivesoulworks.bedspreads.common.BedspreadsRegistry;
import dev.chililisoup.labpackcore.LabPackCore;
import dev.emi.emi.EmiPort;
import dev.emi.emi.EmiUtil;
import dev.emi.emi.api.EmiEntrypoint;
import dev.emi.emi.api.EmiPlugin;
import dev.emi.emi.api.EmiRegistry;
import dev.emi.emi.api.recipe.EmiCraftingRecipe;
import dev.emi.emi.api.recipe.EmiPatternCraftingRecipe;
import dev.emi.emi.api.stack.EmiStack;
import dev.emi.emi.api.stack.TagEmiIngredient;
import dev.emi.emi.api.widget.GeneratedSlotWidget;
import dev.emi.emi.api.widget.SlotWidget;
import dev.emi.emi.recipe.special.EmiBannerDuplicateRecipe;
import dev.emi.emi.recipe.special.EmiBannerShieldRecipe;
import dev.emi.emi.registry.EmiTags;
import io.github.mortuusars.horseman.Horseman;
import net.minecraft.core.component.DataComponents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.BannerItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

@EmiEntrypoint
public class EmiCompat implements EmiPlugin {
    @Override
    public void register(EmiRegistry registry) {
        registry.addRecipe(new DecoratedBedAddPatternRecipe(LabPackCore.loc("/decorated_bed_add_pattern")));

        for (Item item : EmiBannerDuplicateRecipe.BANNERS)
            registry.addRecipe(new DecoratedBedRemovePatternRecipe(
                    item, LabPackCore.loc("/decorated_bed_remove_pattern" + EmiUtil.subId(item))
            ));

        registry.addRecipe(new EmiCraftingRecipe(
                List.of(
                        EmiStack.of(Items.GOAT_HORN),
                        EmiStack.of(Items.COPPER_INGOT),
                        EmiStack.of(Items.COPPER_INGOT),
                        EmiStack.of(Items.COPPER_INGOT)
                ),
                EmiStack.of(Horseman.Items.COPPER_HORN.get()),
                LabPackCore.loc("/copper_horn")
        ));
    }

    private static EmiStack getPattern(Random random, @Nullable Item item, List<EmiStack> beds, Item banner) {
        int patterns = 1 + Math.max(random.nextInt(5), random.nextInt(3));
        BannerPatternLayers pattern = BannerPatternLayers.EMPTY;
        for (int i = 0; i < patterns; i++)
            pattern = EmiPort.addRandomBanner(pattern, random);

        ItemStack bannerStack = new ItemStack(banner);
        bannerStack.set(DataComponents.BANNER_PATTERNS, pattern);
        if (item == null || item instanceof BannerItem) return EmiStack.of(bannerStack);

        ItemStack stack = new ItemStack(item);
        stack.set(
                BedspreadsRegistry.BEDSPREADS_DATA.get(),
                new BedspreadsData(beds.get(random.nextInt(beds.size())).getItemStack(), bannerStack)
        );

        return EmiStack.of(stack);
    }

    private static EmiStack getPattern(Random random, @Nullable Item item, List<EmiStack> beds) {
        Item banner = item instanceof BannerItem ?
                item :
                EmiBannerShieldRecipe.BANNERS.get(
                        random.nextInt(EmiBannerShieldRecipe.BANNERS.size())
                );

        return getPattern(random, item, beds, banner);
    }

    private static class DecoratedBedAddPatternRecipe extends EmiPatternCraftingRecipe {
        private static final List<EmiStack> EMI_BANNERS = EmiBannerShieldRecipe.BANNERS.stream().map(EmiStack::of).toList();
        private static final TagEmiIngredient BEDS_TAG = new TagEmiIngredient(ItemTags.BEDS, 1);
        private static final List<EmiStack> BEDS = BEDS_TAG.getEmiStacks();

        public DecoratedBedAddPatternRecipe(ResourceLocation id) {
            super(
                    Stream.concat(Stream.of(BEDS_TAG), EMI_BANNERS.stream()).toList(),
                    EmiStack.of(BedspreadsRegistry.DECORATED_BED_ITEM.get()),
                    id
            );
        }

        @Override
        public SlotWidget getInputWidget(int slot, int x, int y) {
            if (slot == 0)
                return new SlotWidget(BEDS_TAG, x, y);
            else if (slot == 1)
                return new GeneratedSlotWidget(r -> getPattern(r, null, BEDS), unique, x, y);
            return new SlotWidget(EmiStack.EMPTY, x, y);
        }

        @Override
        public SlotWidget getOutputWidget(int x, int y) {
            return new GeneratedSlotWidget(r -> getPattern(r, BedspreadsRegistry.DECORATED_BED_ITEM.get(), BEDS), unique, x, y);
        }
    }

    private static class DecoratedBedRemovePatternRecipe extends EmiPatternCraftingRecipe {
        private static final List<EmiStack> BEDS = EmiTags.getValues(ItemTags.BEDS);
        private final Item banner;

        public DecoratedBedRemovePatternRecipe(Item banner, ResourceLocation id) {
            super(
                    List.of(EmiStack.of(BedspreadsRegistry.DECORATED_BED_ITEM.get())),
                    EmiStack.of(banner),
                    id
            );

            this.banner = banner;
        }

        @Override
        public SlotWidget getInputWidget(int slot, int x, int y) {
            if (slot == 0)
                return new GeneratedSlotWidget(r -> {
                    EmiStack bed = getPattern(
                            r, BedspreadsRegistry.DECORATED_BED_ITEM.get(), BEDS, this.banner
                    );
                    BedspreadsData bedData = bed.get(BedspreadsRegistry.BEDSPREADS_DATA.get());
                    return bed.setRemainder(EmiStack.of(
                            bedData == null ? Items.WHITE_BED.getDefaultInstance() : bedData.bed()
                    ));
                }, unique, x, y);
            return new SlotWidget(EmiStack.EMPTY, x, y);
        }

        @Override
        public SlotWidget getOutputWidget(int x, int y) {
            return new GeneratedSlotWidget(r -> getPattern(r, this.banner, BEDS), unique, x, y);
        }
    }
}
