package com.github.draylar.miners_horizon.mixin;

import com.github.draylar.miners_horizon.common.Blocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

// thanks to kitten/chloe for the help on this :_)
@Mixin(OreFeatureConfig.Target.class)
abstract class NaturalStoneMixin
{
    private NaturalStoneMixin(){ }

    @SuppressWarnings("UnresolvedMixinReference")
    @Inject(method = "method_13637", at = @At("RETURN"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    private static void modifyPredicate(BlockState state, CallbackInfoReturnable<Boolean> callback)
    {
        Block block = state.getBlock();
        if(!callback.getReturnValueZ() && (block == Blocks.COMPRESSED_STONE || block == Blocks.HARDENED_STONE || block == Blocks.REINFORCED_STONE))
        {
            callback.setReturnValue(true);
        }
    }
}
