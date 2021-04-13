package draylar.horizon.mixin;

import draylar.horizon.MinersHorizon;
import draylar.horizon.config.MinersHorizonConfig;
import draylar.horizon.registry.HorizonWorld;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractBlock.class)
public class StoneDepthMixin {

    @Inject(at = @At("RETURN"), method = "calcBlockBreakingDelta", cancellable = true)
    private void onBlockBreakDelta(BlockState state, PlayerEntity player, BlockView blockView, BlockPos blockPos, CallbackInfoReturnable<Float> info) {
        if (player.world.getRegistryKey().equals(HorizonWorld.MINERS_HORIZON)) {
            if (state.getBlock() instanceof OreBlock || state.getMaterial().equals(Material.STONE)) {
                MinersHorizonConfig config = MinersHorizon.CONFIG;

                int multiplier;
                int y = blockPos.getY();

                if (y < config.zone3Start) {
                    multiplier = config.zone3HardnessModifier;
                } else if (y < config.zone2Start) {
                    multiplier = config.zone2HardnessModifier;
                } else if (y < config.zone1Start) {
                    multiplier = config.zone1HardnessModifier;
                } else {
                    multiplier = 1;
                }

                info.setReturnValue(info.getReturnValue() / multiplier);
            }
        }
    }
}
