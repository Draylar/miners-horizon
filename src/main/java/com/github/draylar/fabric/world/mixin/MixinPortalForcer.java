package com.github.draylar.fabric.world.mixin;

import com.github.draylar.fabric.FabricDimensions;
import com.github.draylar.fabric.world.FabricPlacementHandler;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PortalForcer;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PortalForcer.class)
public class MixinPortalForcer
{
    @Shadow
    @Final
    private ServerWorld world;

    @Inject(method = "method_8653", at = @At("HEAD"), cancellable = true)
    public void method_8653(Entity entity, float float_1, CallbackInfoReturnable<Boolean> infoReturnable) {
        //If going to the void world
        if(world.getDimension().getType() == FabricDimensions.FABRIC_WORLD){
            FabricPlacementHandler.enterDimension(entity, (ServerWorld) entity.getEntityWorld(), world);
            infoReturnable.setReturnValue(true);
            infoReturnable.cancel();
        }

        //Coming from the void world
        if(entity.getEntityWorld().getDimension().getType() == FabricDimensions.FABRIC_WORLD){
            FabricPlacementHandler.leaveDimension(entity, (ServerWorld) entity.getEntityWorld(), world);
            infoReturnable.setReturnValue(true);
            infoReturnable.cancel();
        }
    }
}
