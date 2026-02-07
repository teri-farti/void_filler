package com.terifarti.voidfiller.block;

import com.terifarti.voidfiller.blockentity.VoidGeneratorBlockEntity;
import com.terifarti.voidfiller.item.ModItems;
import com.terifarti.voidfiller.screen.VoidGeneratorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.neoforge.items.ItemStackHandler;

public class VoidGeneratorBlock extends Block implements EntityBlock {
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public VoidGeneratorBlock(Properties props) {
        super(props.noOcclusion());
    }
    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                           Player player, InteractionHand hand, BlockHitResult hit) {

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof VoidGeneratorBlockEntity gen)) return ItemInteractionResult.FAIL;

        if (!level.isClientSide()) {
            if (stack.is(ModItems.BEDROCK_SHARD.get())) {
                ItemStackHandler inv = gen.getInventory();
                ItemStack remaining = inv.insertItem(0, stack.copy(), false);

                int amountInserted = stack.getCount() - remaining.getCount();
                stack.shrink(amountInserted);

                if (amountInserted > 0) return ItemInteractionResult.SUCCESS;
            }
            return ItemInteractionResult.SUCCESS;
        }

        Minecraft.getInstance().setScreen(new VoidGeneratorScreen(gen));
        return ItemInteractionResult.SUCCESS;
    }
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        return this.defaultBlockState().setValue(FACING, ctx.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new VoidGeneratorBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null :
                (lvl, p, st, be) -> {
                    if (be instanceof VoidGeneratorBlockEntity gen) {
                        gen.tick(lvl, p, st);
                    }
                };
    }
}
