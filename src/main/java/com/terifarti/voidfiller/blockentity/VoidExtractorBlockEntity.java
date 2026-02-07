package com.terifarti.voidfiller.blockentity;

import com.terifarti.voidfiller.block.VoidBlock;
import com.terifarti.voidfiller.entity.VoidEnemySpawner;
import com.terifarti.voidfiller.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class VoidExtractorBlockEntity extends BlockEntity {

    private int energy = 0;
    private int progress = 0;
    public int clientEnergy = 0;
    public int clientProgress = 0;
    private final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };
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
    public VoidExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VOID_EXTRACTOR.get(), pos, state);
    }

    public int getEnergy() {
        return energy;
    }

    public int getProgress() {
        return progress;
    }

    public void addEnergy(int amount) {
        energy = Math.max(0, Math.min(10000, energy + amount));
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putInt("energy", energy);
        tag.putInt("progress", progress);
        tag.put("inv", inventory.serializeNBT(provider));
        super.saveAdditional(tag, provider);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        energy = tag.getInt("energy");
        progress = tag.getInt("progress");
        inventory.deserializeNBT(provider, tag.getCompound("inv"));
    }
    public static boolean canExtract(Level level, BlockPos pos) {
        return pos.getY() <= 1 && level.getBlockState(pos).isAir();
    }

    public static void tick(Level level, BlockPos pos, BlockState state, VoidExtractorBlockEntity be) {
        if (level.isClientSide()) return;

        if (be.energy >= 50) {
            be.progress++;
            be.energy -= 5;

            if (be.progress >= 100) {
                be.progress = 0;
                ItemStack slot = be.inventory.getStackInSlot(0);
                BlockPos below = pos.below();
                if (level.getBlockState(below).getBlock() instanceof VoidBlock) {

                    level.removeBlock(below, false);
                    if (slot.isEmpty()) {
                        Block.popResource(level, pos.above(), ModItems.VOID_RESIN.get().getDefaultInstance());
                    } else if (slot.getCount() < slot.getMaxStackSize()) {
                        Block.popResource(level, pos.above(), ModItems.VOID_RESIN.get().getDefaultInstance());
                    }

                    if (level.random.nextFloat() < 0.05f) {
                        VoidEnemySpawner.spawn(level, pos);
                    }
                }
            }
        }
        be.setChanged();
        level.sendBlockUpdated(pos, state, state, 3);
    }
}