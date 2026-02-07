package com.terifarti.voidfiller.item;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.block.Block;

public class VoidToolTier implements Tier {

    @Override
    public int getUses() {
        return 5000;
    }

    @Override
    public float getSpeed() {
        return 15.0f;
    }

    @Override
    public float getAttackDamageBonus() {
        return 5.0f;
    }

    @Override
    public TagKey<Block> getIncorrectBlocksForDrops() {
        return BlockTags.INCORRECT_FOR_DIAMOND_TOOL;
    }

    @Override
    public int getEnchantmentValue() {
        return 25;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.of(ModItems.VOID_INGOT.get());
    }
}