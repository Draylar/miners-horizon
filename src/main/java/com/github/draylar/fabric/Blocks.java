package com.github.draylar.fabric;

import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Blocks
{
    public static final Block HARDENED_STONE = new Block(FabricBlockSettings.of(Material.STONE).hardness(2.5f).build());
    public static final Block REINFORCED_STONE = new Block(FabricBlockSettings.of(Material.STONE).hardness(4.0f).build());
    public static final Block COMPRESSED_STONE = new Block(FabricBlockSettings.of(Material.STONE).hardness(6.0f).build());

    public static void registerBlocks()
    {
        register(HARDENED_STONE, "hardened_stone");
        register(REINFORCED_STONE, "reinforced_stone");
        register(COMPRESSED_STONE, "compressed_stone");
    }

    private static void register(Block block, String name)
    {
        Registry.register(Registry.BLOCK, new Identifier("fabric", name), block);
    }
}
