package com.terifarti.voidfiller.block;

import com.terifarti.voidfiller.blockentity.VoidEnergyPipeBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public class VoidEnergyPipeBlock extends Block implements EntityBlock {

    public VoidEnergyPipeBlock(Properties props) {
        super(props.noOcclusion());
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new VoidEnergyPipeBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null :
                (lvl, p, st, be) -> {
                    if (be instanceof VoidEnergyPipeBlockEntity pipe) {
                        pipe.tick(lvl, p, st);
                    }
                };
    }
}