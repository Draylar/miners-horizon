package com.github.draylar.miners_horizon.config;

public class OreConfig
{
    public final String block;

    public final int size;

    public final int count;
    public final int bottomOffset;
    public final int topOffset;
    public final int maxY;

    public OreConfig(String block, int size, int count, int bottomOffset, int topOffset, int maxY)
    {
        this.block = block;
        this.size = size;
        this.count = count;
        this.bottomOffset = bottomOffset;
        this.topOffset = topOffset;
        this.maxY = maxY;
    }
}
