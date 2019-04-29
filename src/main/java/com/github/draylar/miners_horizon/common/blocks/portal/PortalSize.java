package com.github.draylar.miners_horizon.common.blocks.portal;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;

public class PortalSize
{

    private final IWorld world;
    private final Direction.Axis axis;
    public final Direction rightDir;
    public final Direction leftDir;
    public int portalBlockCount;
    public BlockPos bottomLeft;
    public int height;
    public int width;

    public PortalSize(IWorld worldIn, BlockPos position, Direction.Axis axis)
    {
        this.world = worldIn;
        this.axis = axis;

        if (axis == Direction.Axis.X)
        {
            this.leftDir = Direction.EAST;
            this.rightDir = Direction.WEST;
        }
        else
        {
            this.leftDir = Direction.NORTH;
            this.rightDir = Direction.SOUTH;
        }

        for (BlockPos blockpos = position; position.getY() > blockpos.getY() - 21 && position.getY() > 0 && this.isEmptyBlock(worldIn.getBlockState(position.down()).getBlock()); position = position.down())
        {
            ;
        }

        int i = this.getDistanceUntilEdge(position, this.leftDir) - 1;

        if (i >= 0)
        {
            this.bottomLeft = position.offset(this.leftDir, i);
            this.width = this.getDistanceUntilEdge(this.bottomLeft, this.rightDir);

            if (this.width < 2 || this.width > 21)
            {
                this.bottomLeft = null;
                this.width = 0;
            }
        }

        if (this.bottomLeft != null)
        {
            this.height = this.calculatePortalHeight();
        }
    }

    protected int getDistanceUntilEdge(BlockPos position, Direction axis)
    {
        int i;

        for (i = 0; i < 22; ++i)
        {
            BlockPos blockpos = position.offset(axis, i);

            if (!this.isEmptyBlock(this.world.getBlockState(blockpos).getBlock()) || this.world.getBlockState(blockpos.down()).getBlock() != Blocks.CHISELED_STONE_BRICKS)
            {
                break;
            }
        }

        Block block = this.world.getBlockState(position.offset(axis, i)).getBlock();
        return block == Blocks.CHISELED_STONE_BRICKS ? i : 0;
    }

    public int getHeight()
    {
        return this.height;
    }

    public int getWidth()
    {
        return this.width;
    }

    protected int calculatePortalHeight()
    {
        label24:

        for (this.height = 0; this.height < 21; ++this.height)
        {
            for (int i = 0; i < this.width; ++i)
            {
                BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
                Block block = this.world.getBlockState(blockpos).getBlock();

                if (!this.isEmptyBlock(block))
                {
                    break label24;
                }

                if (block == com.github.draylar.miners_horizon.common.Blocks.MINER_PORTAL)
                {
                    ++this.portalBlockCount;
                }

                if (i == 0)
                {
                    block = this.world.getBlockState(blockpos.offset(this.leftDir)).getBlock();

                    if (block != Blocks.CHISELED_STONE_BRICKS)
                    {
                        break label24;
                    }
                }
                else if (i == this.width - 1)
                {
                    block = this.world.getBlockState(blockpos.offset(this.rightDir)).getBlock();

                    if (block != Blocks.CHISELED_STONE_BRICKS)
                    {
                        break label24;
                    }
                }
            }
        }

        for (int j = 0; j < this.width; ++j)
        {
            if (this.world.getBlockState(this.bottomLeft.offset(this.rightDir, j).up(this.height)).getBlock() != Blocks.CHISELED_STONE_BRICKS)
            {
                this.height = 0;
                break;
            }
        }

        if (this.height <= 21 && this.height >= 3)
        {
            return this.height;
        }
        else
        {
            this.bottomLeft = null;
            this.width = 0;
            this.height = 0;
            return 0;
        }
    }

    protected boolean isEmptyBlock(Block blockIn)
    {
        return blockIn.getDefaultState().getMaterial() == Material.AIR || blockIn == Blocks.FIRE || blockIn == com.github.draylar.miners_horizon.common.Blocks.MINER_PORTAL;
    }

    public boolean isValid()
    {
        return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
    }

    public void placePortalBlocks()
    {
        for (int i = 0; i < this.width; ++i)
        {
            BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i);

            for (int j = 0; j < this.height; ++j)
            {
                this.world.setBlockState(blockpos.up(j), com.github.draylar.miners_horizon.common.Blocks.MINER_PORTAL.getDefaultState().with(MinerPortalBlock.AXIS, this.axis), 2);
            }
        }
    }

    private boolean method_10361()
    {
        return this.portalBlockCount >= this.height * this.width;
    }

    public boolean method_10362()
    {
        return this.isValid() && this.method_10361();
    }

}