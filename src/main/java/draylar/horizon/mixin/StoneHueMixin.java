package draylar.horizon.mixin;

import draylar.horizon.util.UndergroundOreChecker;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.texture.Sprite;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BakedQuad.class)
public class StoneHueMixin {
    @Shadow
    @Final
    protected Sprite sprite;

    @Inject(at = @At("HEAD"), method = "hasColor", cancellable = true)
    private void hasColor(CallbackInfoReturnable<Boolean> info) {
        // Prevent odd NPE with other mods
        if(sprite == null) {
            return;
        }

        if (UndergroundOreChecker.shouldBeHued(sprite.getId())) {
            info.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "getColorIndex", cancellable = true)
    private void getColorIndex(CallbackInfoReturnable<Integer> info) {
        // Prevent odd NPE with other mods
        if(sprite == null) {
            return;
        }

        if (UndergroundOreChecker.shouldBeHued(sprite.getId())) {
            info.setReturnValue(0);
        }
    }
}
