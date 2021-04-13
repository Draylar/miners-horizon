package draylar.horizon.mixin;

import draylar.horizon.registry.HorizonWorld;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.MutableWorldProperties;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
@Mixin(ClientWorld.class)
public abstract class ClientWorldMixin extends World {

    @Shadow public abstract void setTimeOfDay(long l);
    @Shadow @Final private ClientWorld.Properties clientWorldProperties;

    private ClientWorldMixin(MutableWorldProperties properties, RegistryKey<World> registryRef, DimensionType dimensionType, Supplier<Profiler> profiler, boolean isClient, boolean debugWorld, long seed) {
        super(properties, registryRef, dimensionType, profiler, isClient, debugWorld, seed);
    }

    @Inject(
            method = "tickTime",
            at = @At("HEAD"), cancellable = true)
    private void onTickTime(CallbackInfo ci) {
        if(getRegistryKey().equals(HorizonWorld.MINERS_HORIZON)) {
            setTimeOfDay(3000);
            clientWorldProperties.setTime(3000);
            ci.cancel();
        }
    }
}
