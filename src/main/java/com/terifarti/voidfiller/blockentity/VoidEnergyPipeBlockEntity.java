package com.terifarti.voidfiller.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class VoidEnergyPipeBlockEntity extends BlockEntity {

    public int energy = 0;
    public static final int MAX = 1000;

    public VoidEnergyPipeBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VOID_PIPE.get(), pos, state);
    }
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, provider);
        return tag;
    }

    @Override
    public net.minecraft.network.protocol.Packet<net.minecraft.network.protocol.game.ClientGamePacketListener> getUpdatePacket() {
        return net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket.create(this);
    }
    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) return;

        for (Direction dir : Direction.values()) {
            BlockEntity be = level.getBlockEntity(pos.relative(dir));

            if (be instanceof VoidGeneratorBlockEntity generator) {
                int taken = Math.min(50, generator.getEnergy());
                generator.addEnergy(-taken);
                energy = Math.min(MAX, energy + taken);
            }

            if (be instanceof VoidExtractorBlockEntity extractor) {
                int give = Math.min(20, energy);
                energy -= give;
                extractor.addEnergy(give);
            }

            if (be instanceof VoidEnergyPipeBlockEntity pipe) {
                int diff = (energy - pipe.energy) / 2;
                if (diff > 0) {
                    energy -= diff;
                    pipe.energy += diff;
                }
            }
        }

        setChanged();
        level.sendBlockUpdated(pos, state, state, 3);
    }
}
