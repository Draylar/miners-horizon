package com.github.draylar.miners_horizon.mixin;

import com.github.draylar.miners_horizon.common.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class MixinUsePickaxe
{
    @Inject(at = @At("HEAD"), method = "useOnBlock")
    public void useOnBlock(ItemUsageContext itemUsageContext_1, CallbackInfoReturnable<ActionResult> cir)
    {
        if(((Item) (Object) this) instanceof PickaxeItem)
        {
            if(itemUsageContext_1.getWorld().getBlockState(itemUsageContext_1.getBlockPos()).getBlock() == net.minecraft.block.Blocks.CHISELED_STONE_BRICKS)
            {
                Blocks.MINER_PORTAL.method_10352(itemUsageContext_1.getWorld(), itemUsageContext_1.getBlockPos().up());
            }
        }
    }
}
