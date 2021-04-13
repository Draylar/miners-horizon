package draylar.horizon.world;

import net.minecraft.client.render.SkyProperties;
import net.minecraft.util.math.Vec3d;

public class HorizonSkyProperties extends SkyProperties {

    public HorizonSkyProperties() {
        super(350, true, SkyProperties.SkyType.NORMAL, false, false);
    }

    @Override
    public Vec3d adjustFogColor(Vec3d color, float sunHeight) {
        return color.multiply((double)(sunHeight * 0.94F + 0.06F), (sunHeight * 0.94F + 0.06F), (sunHeight * 0.91F + 0.09F));
    }

    @Override
    public boolean useThickFog(int camX, int camY) {
        return false;
    }
}