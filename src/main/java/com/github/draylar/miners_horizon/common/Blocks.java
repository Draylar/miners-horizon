package com.github.draylar.miners_horizon.common;

import com.github.draylar.miners_horizon.MinersHorizon;
import com.github.draylar.miners_horizon.common.blocks.MinerPortalBlock;
import net.minecraft.block.Block;
import net.minecraft.util.registry.Registry;

public class Blocks
{
    public static final MinerPortalBlock MINER_PORTAL = new MinerPortalBlock();

    public static void register()
    {
        register(MINER_PORTAL, "miner_portal");
    }

    private static void register(Block block, String name)
    {
        Registry.register(Registry.BLOCK, MinersHorizon.getModIdentifier(name), block);
    }
}
