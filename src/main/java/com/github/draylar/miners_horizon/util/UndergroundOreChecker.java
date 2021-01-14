package com.github.draylar.miners_horizon.util;

import net.minecraft.util.Identifier;

public class UndergroundOreChecker
{
    public static boolean shouldBeHued(Identifier identifier)
    {
        String path = identifier.getPath();

        if(path.contains("stone") || path.contains("ore"))
        {
            return true;
        }

        return false;
    }
}
