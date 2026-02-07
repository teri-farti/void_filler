package com.terifarti.voidfiller.item;

import com.terifarti.voidfiller.voidfiller;
import com.terifarti.voidfiller.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Registries.ITEM, voidfiller.MODID);
    public static final DeferredHolder<Item, BlockItem> VOID_GENERATOR_ITEM =
            ITEMS.register("void_generator", () ->
                    new BlockItem(ModBlocks.VOID_GENERATOR.get(), new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> VOID_PIPE_ITEM =
            ITEMS.register("void_pipe", () ->
                    new BlockItem(ModBlocks.VOID_PIPE.get(), new Item.Properties()));
    public static final DeferredHolder<Item, BlockItem> VOID_EXTRACTOR_ITEM =
            ITEMS.register("void_extractor", () ->
                    new BlockItem(ModBlocks.VOID_EXTRACTOR.get(), new Item.Properties()));

    public static final DeferredHolder<Item, BlockItem> SOLID_VOID_ITEM =
            ITEMS.register("solid_void", () ->
                    new BlockItem(ModBlocks.SOLID_VOID.get(), new Item.Properties()));

    public static final DeferredHolder<Item, BlockItem> VOID_BLOCK_ITEM =
            ITEMS.register("void_block", () ->
                    new BlockItem(ModBlocks.VOID_BLOCK.get(), new Item.Properties()));

    public static final DeferredHolder<Item, Item> VOID_RESIN =
            ITEMS.register("void_resin", () ->
                    new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> BEDROCK_SHARD =
            ITEMS.register("bedrock_shard", () ->
                    new Item(new Item.Properties()));
    public static final DeferredHolder<Item, Item> VOID_ALLOY =
            ITEMS.register("void_alloy", () ->
                    new Item(new Item.Properties()));

    public static final DeferredHolder<Item, Item> VOID_INGOT = ITEMS.register("void_ingot",
            () -> new Item(new Item.Properties()));

    public static final DeferredHolder<Item, ArmorItem> VOID_HELMET = ITEMS.register("void_helmet",
            () -> new ArmorItem(
                    VoidArmorMaterial.VOID,
                    ArmorItem.Type.HELMET,
                    new Item.Properties().durability(ArmorItem.Type.HELMET.getDurability(37))
            )
    );

    public static final DeferredHolder<Item, ArmorItem> VOID_CHESTPLATE = ITEMS.register("void_chestplate",
            () -> new ArmorItem(
                    VoidArmorMaterial.VOID,
                    ArmorItem.Type.CHESTPLATE,
                    new Item.Properties().durability(ArmorItem.Type.CHESTPLATE.getDurability(37))
            )
    );

    public static final DeferredHolder<Item, ArmorItem> VOID_LEGGINGS = ITEMS.register("void_leggings",
            () -> new ArmorItem(
                    VoidArmorMaterial.VOID,
                    ArmorItem.Type.LEGGINGS,
                    new Item.Properties().durability(ArmorItem.Type.LEGGINGS.getDurability(37))
            )
    );

    public static final DeferredHolder<Item, ArmorItem> VOID_BOOTS = ITEMS.register("void_boots",
            () -> new ArmorItem(
                    VoidArmorMaterial.VOID,
                    ArmorItem.Type.BOOTS,
                    new Item.Properties().durability(ArmorItem.Type.BOOTS.getDurability(37))
            )
    );

    public static final VoidToolTier VOID_TIER = new VoidToolTier();

    public static final DeferredHolder<Item, SwordItem> VOID_SWORD = ITEMS.register("void_sword",
            () -> new SwordItem(
                    VOID_TIER,
                    new Item.Properties().attributes(
                            DiggerItem.createAttributes(VOID_TIER, 3.0F, -2.4F)
                    )
            )
    );

    public static final DeferredHolder<Item, PickaxeItem> VOID_PICKAXE = ITEMS.register("void_pickaxe",
            () -> new PickaxeItem(
                    VOID_TIER,
                    new Item.Properties().attributes(
                            DiggerItem.createAttributes(VOID_TIER, 1.0F, -2.8F)
                    )
            )
    );

    public static final DeferredHolder<Item, AxeItem> VOID_AXE = ITEMS.register("void_axe",
            () -> new AxeItem(
                    VOID_TIER,
                    new Item.Properties().attributes(
                            DiggerItem.createAttributes(VOID_TIER, 5.0F, -3.0F)
                    )
            )
    );

    public static final DeferredHolder<Item, ShovelItem> VOID_SHOVEL = ITEMS.register("void_shovel",
            () -> new ShovelItem(
                    VOID_TIER,
                    new Item.Properties().attributes(
                            DiggerItem.createAttributes(VOID_TIER, 1.5F, -3.0F)
                    )
            )
    );

    public static final DeferredHolder<Item, HoeItem> VOID_HOE = ITEMS.register("void_hoe",
            () -> new HoeItem(
                    VOID_TIER,
                    new Item.Properties().attributes(
                            DiggerItem.createAttributes(VOID_TIER, -2.0F, 0.0F)
                    )
            )
    );
}