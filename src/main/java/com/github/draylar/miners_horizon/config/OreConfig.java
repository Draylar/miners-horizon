package com.github.draylar.miners_horizon.config;

import net.minecraft.util.Identifier;

public class OreConfig
{
    public final Identifier blockIdentifier;

    public final int size;

    public final int count;
    public final int bottomOffset;
    public final int topOffset;
    public final int maxY;

    public OreConfig(Identifier blockIdentifier, int size, int count, int bottomOffset, int topOffset, int maxY)
    {
        this.blockIdentifier = blockIdentifier;
        this.size = size;
        this.count = count;
        this.bottomOffset = bottomOffset;
        this.topOffset = topOffset;
        this.maxY = maxY;
    }
}
