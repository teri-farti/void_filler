package com.terifarti.voidfiller.block;

import com.terifarti.voidfiller.voidfiller;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;



public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Registries.BLOCK, voidfiller.MODID);

    public static final DeferredHolder<Block, VoidEnergyPipeBlock> VOID_PIPE =
            BLOCKS.register("void_pipe", () ->
                    new VoidEnergyPipeBlock(BlockBehaviour.Properties.of().strength(1f).noOcclusion()));

    public static final DeferredHolder<Block, VoidGeneratorBlock> VOID_GENERATOR =
            BLOCKS.register("void_generator", () ->
                    new VoidGeneratorBlock(BlockBehaviour.Properties.of().strength(3f).noOcclusion()));

    public static final DeferredHolder<Block, VoidExtractorBlock> VOID_EXTRACTOR =
            BLOCKS.register("void_extractor", () ->
                    new VoidExtractorBlock(BlockBehaviour.Properties.of()
                            .strength(3.0F)
                            .noOcclusion()
                    ));

    public static final DeferredHolder<Block, Block> SOLID_VOID =
            BLOCKS.register("solid_void", () ->
                    new Block(BlockBehaviour.Properties.of()
                            .strength(-1.0F, 3600000.0F)
                            .noOcclusion()
                            .requiresCorrectToolForDrops()
                    ));

    public static final DeferredHolder<Block, Block> VOID_BLOCK =
            BLOCKS.register("void_block", () ->
                    new VoidBlock(BlockBehaviour.Properties.of()
                            .noCollission()
                            .strength(-1.0F)
                            .noOcclusion()
                            .lightLevel(s -> 1)
                    ));
}