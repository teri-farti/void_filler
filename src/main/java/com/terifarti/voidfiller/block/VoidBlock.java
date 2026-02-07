package com.terifarti.voidfiller.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class VoidBlock extends Block {

    public VoidBlock(BlockBehaviour.Properties props) {
        super(props);
    }

    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {

        entity.hurt(level.damageSources().magic(), 2.0f);

        entity.setDeltaMovement(
                entity.getDeltaMovement().x,
                entity.getDeltaMovement().y - 0.08,
                entity.getDeltaMovement().z
        );
    }
}