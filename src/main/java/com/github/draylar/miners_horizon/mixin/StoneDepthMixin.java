package com.github.draylar.miners_horizon.mixin;

import com.github.draylar.miners_horizon.MinersHorizon;
import com.github.draylar.miners_horizon.config.MinersHorizonConfig;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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
        if(playerEntity.dimension == MinersHorizon.MINERS_HORIZON)
        {
            if (isUndergroundMaterial(blockState))
            {
                MinersHorizonConfig config = AutoConfig.getConfigHolder(MinersHorizonConfig.class).getConfig();

                int multiplier;
                int y = blockPos.getY();

                if(y < config.zone3Start)
                {
                    multiplier = config.zone3HardnessModifier;
                }

                else if (y < config.zone2Start)
                {
                    multiplier = config.zone2HardnessModifier;
                }

                else if (y < config.zone1Start)
                {
                    multiplier = config.zone1HardnessModifier;
                }

                else
                {
                    multiplier = 1;
                }

                info.setReturnValue(info.getReturnValue() / multiplier);
            }
        }
    }

    private boolean isUndergroundMaterial(BlockState state)
    {
        Block block = state.getBlock();
        String transKey = block.getTranslationKey();
        return (transKey.contains("stone") || transKey.contains("andesite") || transKey.contains("ore"));
    }
}
