package com.github.draylar.miners_horizon.mixin;

import com.github.draylar.miners_horizon.MinersHorizon;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecraftClient.class)
public abstract class LateClientInit {

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/util/Window;setPhase(Ljava/lang/String;)V", ordinal = 0))
    public void runCottonInit(CallbackInfo ci) {
        MinersHorizon.addOres();
    }
}
