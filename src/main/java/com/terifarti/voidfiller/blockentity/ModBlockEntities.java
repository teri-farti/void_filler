package com.terifarti.voidfiller.blockentity;

import com.terifarti.voidfiller.voidfiller;
import com.terifarti.voidfiller.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, voidfiller.MODID);
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidGeneratorBlockEntity>> VOID_GENERATOR =
            BLOCK_ENTITIES.register("void_generator", () ->
                    BlockEntityType.Builder.of(VoidGeneratorBlockEntity::new, ModBlocks.VOID_GENERATOR.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidEnergyPipeBlockEntity>> VOID_PIPE =
            BLOCK_ENTITIES.register("void_pipe", () ->
                    BlockEntityType.Builder.of(VoidEnergyPipeBlockEntity::new, ModBlocks.VOID_PIPE.get()).build(null));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<VoidExtractorBlockEntity>> VOID_EXTRACTOR =
            BLOCK_ENTITIES.register("void_extractor", () ->
                    BlockEntityType.Builder.of(
                            VoidExtractorBlockEntity::new,
                            ModBlocks.VOID_EXTRACTOR.get()
                    ).build(null)
            );
}