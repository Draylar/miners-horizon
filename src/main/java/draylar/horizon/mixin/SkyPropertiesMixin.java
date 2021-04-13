package draylar.horizon.mixin;

import draylar.horizon.MinersHorizon;
import draylar.horizon.world.HorizonSkyProperties;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SkyProperties.class)
public class SkyPropertiesMixin {

    @Shadow @Final private static Object2ObjectMap<Identifier, SkyProperties> BY_IDENTIFIER;

    static {
        BY_IDENTIFIER.put(MinersHorizon.id("miners_horizon"), new HorizonSkyProperties());
    }
}
