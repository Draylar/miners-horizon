package com.github.draylar.miners_horizon.mixin;

import com.github.draylar.miners_horizon.MinersHorizon;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftDedicatedServer.class)
public class LateServerInit {

    @Inject(method = "setupServer", at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;info(Ljava/lang/String;)V", ordinal = 0, remap = false))
    public void runCottonInit(CallbackInfoReturnable cir) {
        MinersHorizon.addOres();
    }
}