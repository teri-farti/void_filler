package com.terifarti.voidfiller.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;

public class VoidEnemySpawner {

    public static void spawn(Level level, BlockPos pos) {
        if (level.isClientSide()) return;

        VoidEnemy enemy = ModEntities.VOID_ENEMY.get().create((ServerLevel) level);
        enemy.moveTo(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
        level.addFreshEntity(enemy);
    }
}
