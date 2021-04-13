package draylar.horizon.registry;

import draylar.horizon.MinersHorizon;
import draylar.horizon.block.MinerPortalBlock;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

public class HorizonBlocks {

    public static final MinerPortalBlock MINER_PORTAL = register("miner_portal", new MinerPortalBlock());

    public static void init() {

    }

    private static <T extends Block> T register(String name, T block) {
        return Registry.register(Registry.BLOCK, MinersHorizon.id(name), block);
    }

    private HorizonBlocks() {
        // NO-OP
    }
}
