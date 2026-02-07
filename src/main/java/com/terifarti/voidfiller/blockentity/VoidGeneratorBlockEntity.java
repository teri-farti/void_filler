package com.terifarti.voidfiller.blockentity;

import com.terifarti.voidfiller.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class VoidGeneratorBlockEntity extends BlockEntity {

    private int energy = 0;
    private int burnTime = 0;
    public int clientEnergy = 0;
    public int clientProgress = 0;
    private final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, ItemStack stack) {
            return stack.is(ModItems.BEDROCK_SHARD.get());
        }
    };

    public VoidGeneratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.VOID_GENERATOR.get(), pos, state);
    }

    public int getEnergy() {
        return energy;
    }

    public int getBurnTime() {
        return burnTime;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public void addEnergy(int amount) {
        energy = Math.max(0, Math.min(10000, energy + amount));
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        tag.putInt("energy", energy);
        tag.putInt("burnTime", burnTime);
        tag.put("inv", inventory.serializeNBT(provider));
        super.saveAdditional(tag, provider);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadAdditional(tag, provider);
        energy = tag.getInt("energy");
        burnTime = tag.getInt("burnTime");
        inventory.deserializeNBT(provider, tag.getCompound("inv"));
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

        boolean changed = false;

        if (burnTime > 0) {
            burnTime--;
            energy = Math.min(10000, energy + 20);
            changed = true;
        }

        if (burnTime <= 0 && energy < 10000) {
            ItemStack stack = inventory.getStackInSlot(0);
            if (!stack.isEmpty() && stack.is(ModItems.BEDROCK_SHARD.get())) {
                burnTime = 200; // Время горения одного осколка (10 секунд)
                stack.shrink(1); // Забираем 1 шт из стака
                inventory.setStackInSlot(0, stack);
                changed = true;
            }
        }

        if (energy > 0) {
            for (Direction dir : Direction.values()) {
                BlockEntity neighbor = level.getBlockEntity(pos.relative(dir));
                if (neighbor instanceof VoidEnergyPipeBlockEntity pipe) {
                    int transfer = Math.min(50, energy);
                    if (pipe.energy < VoidEnergyPipeBlockEntity.MAX) {
                        energy -= transfer;
                        pipe.energy = Math.min(pipe.energy + transfer, VoidEnergyPipeBlockEntity.MAX);
                        changed = true;
                    }
                }
            }
        }

        if (changed) {
            setChanged();
            level.sendBlockUpdated(pos, state, state, 3);
        }
    }
}