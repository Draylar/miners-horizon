package com.github.draylar.miners_horizon.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Block.class)
public class StoneDepthMixin
{
    @Inject(at = @At("RETURN"), method = "calcBlockBreakingDelta", cancellable = true)
    private void onBlockBreakDelta(BlockState blockState, PlayerEntity playerEntity, BlockView blockView, BlockPos blockPos, CallbackInfoReturnable<Float> info)
    {
        if(isUndergroundMaterial(blockState))
        {
            info.setReturnValue(info.getReturnValue() * 0.05f);
        }
    }

    private boolean isUndergroundMaterial(BlockState state)
    {
        Block block = state.getBlock();
        return block == Blocks.STONE || block == Blocks.GRAVEL || block == Blocks.ANDESITE || block == Blocks.COBBLESTONE || block == Blocks.COAL_ORE || block == Blocks.IRON_ORE;
    }
}
