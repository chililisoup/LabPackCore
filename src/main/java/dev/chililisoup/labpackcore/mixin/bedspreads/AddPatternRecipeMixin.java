package dev.chililisoup.labpackcore.mixin.bedspreads;

import com.illusivesoulworks.bedspreads.common.DecoratedBedItem;
import com.illusivesoulworks.bedspreads.common.recipe.AddPatternRecipe;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AddPatternRecipe.class)
public abstract class AddPatternRecipeMixin {
    @Unique private static Item labpackcore$transformItem(ItemStack stack) {
        Item item = stack.getItem();
        if (item instanceof DecoratedBedItem) return Items.AIR;
        return stack.is(ItemTags.BEDS) ? Items.WHITE_BED : item;
    }

    @Redirect(
            method = "matches(Lnet/minecraft/world/item/crafting/CraftingInput;Lnet/minecraft/world/level/Level;)Z", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;",
            ordinal = 1
    ))
    private Item allowBedTagMatch(ItemStack stack) {
        return labpackcore$transformItem(stack);
    }

    @Redirect(
            method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;getItem()Lnet/minecraft/world/item/Item;",
            ordinal = 1
    ))
    private Item allowBedTagAssemble(ItemStack stack) {
        return labpackcore$transformItem(stack);
    }

    @WrapOperation(
            method = "assemble(Lnet/minecraft/world/item/crafting/CraftingInput;Lnet/minecraft/core/HolderLookup$Provider;)Lnet/minecraft/world/item/ItemStack;", at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;copy()Lnet/minecraft/world/item/ItemStack;"
    ))
    private static ItemStack preventDupe(ItemStack stack, Operation<ItemStack> original) {
        return stack.copyWithCount(1);
    }
}
