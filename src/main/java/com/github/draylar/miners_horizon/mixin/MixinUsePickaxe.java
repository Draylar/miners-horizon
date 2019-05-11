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
public class MixinUsePickaxe
{
    @Inject(at = @At("HEAD"), method = "useOnBlock")
    private void useOnBlock(ItemUsageContext usageContext, CallbackInfoReturnable<ActionResult> info)
    {
        Item item = (Item) (Object) this;
        if(item instanceof PickaxeItem)
        {
            if(usageContext.getWorld().getBlockState(usageContext.getBlockPos()).getBlock() == net.minecraft.block.Blocks.CHISELED_STONE_BRICKS)
            {
                Blocks.MINER_PORTAL.method_10352(usageContext.getWorld(), usageContext.getBlockPos().up());
            }
        }
    }
}
