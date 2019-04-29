package com.github.draylar.miners_horizon.common.world.dims;

import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;

import java.util.function.BiFunction;

public class FabricDimensionType extends DimensionType
{
    private int id;

    public FabricDimensionType(Identifier name, int id, BiFunction<World, DimensionType, ? extends Dimension> factory)
    {
        super(id, name.getNamespace() + "_" + name.getPath(), "DIM_" + name.getNamespace(), factory, true);
        this.id = id;
        register(name);
    }

    private DimensionType register(Identifier id)
    {
        return Registry.register(Registry.DIMENSION, this.id, id.toString(), this);
    }
}
