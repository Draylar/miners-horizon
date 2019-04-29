package com.github.draylar.miners_horizon.common;

import com.github.draylar.miners_horizon.MinersHorizon;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;

public class Items
{
    public static void register()
    {

    }

    private static void register(Item item, String name)
    {
        Registry.register(Registry.ITEM, MinersHorizon.getModIdentifier(name), item);
    }
}
