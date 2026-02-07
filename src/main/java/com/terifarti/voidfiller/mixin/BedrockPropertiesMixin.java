package com.terifarti.voidfiller.mixin;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(Blocks.class)
public class BedrockPropertiesMixin {

    @ModifyArg(
            method = "<clinit>",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/Block;<init>(Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;)V"
            ),
            index = 0
    )
    private static BlockBehaviour.Properties modifyBedrockProperties(BlockBehaviour.Properties props) {
        if (Blocks.STONE == null) {
            return BlockBehaviour.Properties
                    .of()
                    .strength(2F, 6.0F)
                    .requiresCorrectToolForDrops();
        }
        if (Blocks.DIRT == null) {
            return BlockBehaviour.Properties
                    .of()
                    .strength(0.1F)
                    .sound(SoundType.GRAVEL)
                    .requiresCorrectToolForDrops();
        }
        if (Blocks.BEDROCK == null) {
            return BlockBehaviour.Properties
                    .of()
                    .strength(50.0F, 1200.0F)
                    .requiresCorrectToolForDrops();
        }
        return props;
    }
}