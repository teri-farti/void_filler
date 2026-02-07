package com.terifarti.voidfiller.event;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class VoidEvents {

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent.Post event) {

        Player player = event.getEntity();
        Level level = player.level();

        if (player.getY() < -64) {
            player.hurt(level.damageSources().magic(), 999999f);
        }
    }
}