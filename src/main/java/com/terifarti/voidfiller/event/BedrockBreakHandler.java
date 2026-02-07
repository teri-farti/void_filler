package com.terifarti.voidfiller.event;

import com.terifarti.voidfiller.voidfiller;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

@EventBusSubscriber(modid = voidfiller.MODID)
public class BedrockBreakHandler {

    @SubscribeEvent
    public static void onHarvestCheck(PlayerEvent.HarvestCheck event) {
        if (!event.getTargetBlock().is(Blocks.BEDROCK)) {
            return;
        }

        ItemStack stack = event.getEntity().getMainHandItem();

        if (isStrongPickaxe(stack)) {
            event.setCanHarvest(true);
        }
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (!event.getState().is(Blocks.BEDROCK)) return;

        ItemStack stack = event.getEntity().getMainHandItem();

        if (!(stack.getItem() instanceof PickaxeItem pickaxe)) {
            event.setNewSpeed(0);
            return;
        }

        if (pickaxe.getTier().getUses() < 2031) {
            event.setNewSpeed(0);
            return;
        }

        event.setNewSpeed(8.0F);
    }


    private static boolean isStrongPickaxe(ItemStack stack) {
        if (!(stack.getItem() instanceof PickaxeItem pickaxe)) {
            return false;
        }

        if (!stack.isCorrectToolForDrops(Blocks.OBSIDIAN.defaultBlockState())) {
            return false;
        }

        return pickaxe.getTier().getUses() >= 2031;
    }
}