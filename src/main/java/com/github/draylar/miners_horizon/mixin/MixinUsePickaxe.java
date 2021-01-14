package com.github.draylar.miners_horizon.mixin;

import com.github.draylar.miners_horizon.common.Blocks;
import com.github.draylar.miners_horizon.config.MinersHorizonConfig;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
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
            Block block = Registry.BLOCK.get(new Identifier(AutoConfig.getConfigHolder(MinersHorizonConfig.class).getConfig().portalBlockId));
            if(block == net.minecraft.block.Blocks.AIR) block = net.minecraft.block.Blocks.CHISELED_STONE_BRICKS;

            if(usageContext.getWorld().getBlockState(usageContext.getBlockPos()).getBlock() == block)
            {
                Blocks.MINER_PORTAL.createPortalAt(usageContext.getWorld(), usageContext.getBlockPos().up());
            }
        }
    }
}
