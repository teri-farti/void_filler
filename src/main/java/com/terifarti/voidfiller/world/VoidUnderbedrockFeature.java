package com.terifarti.voidfiller.world;

import com.mojang.serialization.Codec;
import com.terifarti.voidfiller.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class VoidUnderbedrockFeature extends Feature<NoneFeatureConfiguration> {

    public VoidUnderbedrockFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> ctx) {
        LevelAccessor level = ctx.level();
        BlockPos origin = ctx.origin();

        int chunkX = origin.getX() >> 4;
        int chunkZ = origin.getZ() >> 4;
        int startX = chunkX << 4;
        int startZ = chunkZ << 4;

        for (int x = startX; x < startX + 16; x++) {
            for (int z = startZ; z < startZ + 16; z++) {
                for (int y = -64; y > -128; y--) {
                    level.setBlock(new BlockPos(x, y, z), ModBlocks.VOID_BLOCK.get().defaultBlockState(), 2);
                }
            }
        }
        return true;
    }
}