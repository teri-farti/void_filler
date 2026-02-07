package com.terifarti.voidfiller.block;

import com.terifarti.voidfiller.blockentity.ModBlockEntities;
import com.terifarti.voidfiller.blockentity.VoidExtractorBlockEntity;
import com.terifarti.voidfiller.item.ModItems;
import com.terifarti.voidfiller.screen.VoidExtractorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemStackHandler;

public class VoidExtractorBlock extends Block implements EntityBlock {

    public VoidExtractorBlock(BlockBehaviour.Properties props) {
        super(props);
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                           Player player, InteractionHand hand, BlockHitResult hit) {

        if (level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof VoidExtractorBlockEntity extractor) {
                Minecraft.getInstance().setScreen(new VoidExtractorScreen(extractor));
            }
        }

        return ItemInteractionResult.SUCCESS;
    }

    @Override
    public void tick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {

        BlockPos below = pos.below();
        level.scheduleTick(pos, this, 20);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean moved) {
        if (!level.isClientSide()) {
            level.scheduleTick(pos, this, 20);
        }
    }
    @Override
    public InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos,
                                            Player player, BlockHitResult hit) {

        if (!level.isClientSide()) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof VoidExtractorBlockEntity extractor) {

                ItemStackHandler inv = extractor.getInventory();
                ItemStack stack = inv.getStackInSlot(0);

                if (!stack.isEmpty()) {
                    player.addItem(stack.copy());
                    inv.setStackInSlot(0, ItemStack.EMPTY);
                    return InteractionResult.SUCCESS;
                }
            }
        }

        return InteractionResult.SUCCESS;
    }
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide()) {
            return null;
        }

        if (type == ModBlockEntities.VOID_EXTRACTOR.get()) {
            return (lvl, pos, st, be) -> {
                if (be instanceof VoidExtractorBlockEntity extractor) {
                    VoidExtractorBlockEntity.tick(lvl, pos, st, extractor);
                }
            };
        }

        return null;
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new VoidExtractorBlockEntity(pos, state);
    }
}

