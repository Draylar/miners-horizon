package draylar.horizon.mixin;

import draylar.horizon.registry.HorizonWorld;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {

    @Shadow public abstract RegistryKey<World> getRegistryKey();

    @Inject(
            method = "getTimeOfDay",
            at = @At("HEAD"), cancellable = true)
    private void getTimeOfDay(CallbackInfoReturnable<Long> cir) {
        if(getRegistryKey().equals(HorizonWorld.MINERS_HORIZON)) {
            cir.setReturnValue(3000L);
        }
    }
}
