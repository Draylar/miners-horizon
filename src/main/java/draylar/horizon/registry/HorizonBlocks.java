package draylar.horizon.registry;

import draylar.horizon.MinersHorizon;
import draylar.horizon.block.HorizonPortalBlock;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

public class HorizonBlocks {

    public static final HorizonPortalBlock HORIZON_PORTAL = register("horizon_portal", new HorizonPortalBlock());

    public static void init() {

    }

    private static <T extends Block> T register(String name, T block) {
        return Registry.register(Registry.BLOCK, MinersHorizon.id(name), block);
    }

    private HorizonBlocks() {
        // NO-OP
    }
}
