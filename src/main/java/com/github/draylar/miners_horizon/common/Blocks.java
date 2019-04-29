package com.github.draylar.miners_horizon.common;

import com.github.draylar.miners_horizon.MinersHorizon;
import com.github.draylar.miners_horizon.common.blocks.portal.MinerPortalBlock;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.registry.Registry;

public class Blocks
{
    public static final Block HARDENED_STONE = new Block(FabricBlockSettings.of(Material.STONE).hardness(4f).dropsLike(net.minecraft.block.Blocks.STONE).build());
    public static final Block REINFORCED_STONE = new Block(FabricBlockSettings.of(Material.STONE).hardness(8.0f).dropsLike(net.minecraft.block.Blocks.STONE).build());
    public static final Block COMPRESSED_STONE = new Block(FabricBlockSettings.of(Material.STONE).hardness(15.0f).dropsLike(net.minecraft.block.Blocks.STONE).build());
    public static final MinerPortalBlock MINER_PORTAL = new MinerPortalBlock();

    public static void register()
    {
        register(HARDENED_STONE, "hardened_stone");
        register(REINFORCED_STONE, "reinforced_stone");
        register(COMPRESSED_STONE, "compressed_stone");
        register(MINER_PORTAL, "miner_portal");
    }

    private static void register(Block block, String name)
    {
        Registry.register(Registry.BLOCK, MinersHorizon.getModIdentifier(name), block);
    }
}
