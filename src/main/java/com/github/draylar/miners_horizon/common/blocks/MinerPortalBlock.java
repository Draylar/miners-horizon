package com.github.draylar.miners_horizon.common.blocks;

import com.github.draylar.miners_horizon.MinersHorizon;
import com.github.draylar.miners_horizon.config.MinersHorizonConfig;
import com.google.common.cache.LoadingCache;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.*;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.Random;

public class MinerPortalBlock extends PortalBlock
{
    public MinerPortalBlock()
    {
        super(FabricBlockSettings.copy(Blocks.NETHER_PORTAL).build());
    }

    @Override
    public void onEntityCollision(BlockState stateIn, World worldIn, BlockPos posIn, Entity entityIn)
    {
        if(!worldIn.isClient() && entityIn.isSneaking())
        {
            if(entityIn.dimension == MinersHorizon.MINERS_HORIZON) entityIn.changeDimension(DimensionType.OVERWORLD);
            else entityIn.changeDimension(MinersHorizon.MINERS_HORIZON);
        }
    }

    @Override
    public boolean createPortalAt(IWorld worldIn, BlockPos pos)
    {
        PortalSize portalSizeX = new PortalSize(worldIn, pos, Direction.Axis.X);

        if (portalSizeX.isValid() && portalSizeX.portalBlockCount == 0)
        {
            portalSizeX.placePortalBlocks();
            return true;
        }

        else
        {
            PortalSize portalSizeZ = new PortalSize(worldIn, pos, Direction.Axis.Z);

            if (portalSizeZ.isValid() && portalSizeZ.portalBlockCount == 0)
            {
                portalSizeZ.placePortalBlocks();
                return true;
            }

            else return false;
        }
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState blockState, Direction direction, BlockState blockState_2, IWorld iWorld_1, BlockPos pos, BlockPos blockPos_2)
    {
        Direction.Axis axis = direction.getAxis();
        Direction.Axis axis2 = blockState.get(AXIS);

        boolean boolean_1 = axis2 != axis && axis.isHorizontal();
        return !boolean_1 && blockState_2.getBlock() != this && !(new PortalSize(iWorld_1, pos, axis2)).method_10362() ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(blockState, direction, blockState_2, iWorld_1, pos, blockPos_2);
    }

    @Override
    public void onScheduledTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
        // overriden to stop pigman from spawning
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState blockState_1, World world_1, BlockPos blockPos_1, Random random_1) {
        if (random_1.nextInt(100) == 0) {
            world_1.playSound((double)blockPos_1.getX() + 0.5D, (double)blockPos_1.getY() + 0.5D, (double)blockPos_1.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, random_1.nextFloat() * 0.4F + 0.8F, false);
        }

        for(int int_1 = 0; int_1 < 4; ++int_1) {
            double double_1 = (double)((float)blockPos_1.getX() + random_1.nextFloat());
            double double_2 = (double)((float)blockPos_1.getY() + random_1.nextFloat());
            double double_3 = (double)((float)blockPos_1.getZ() + random_1.nextFloat());
            double double_4 = ((double)random_1.nextFloat() - 0.5D) * 0.5D;
            double double_5 = ((double)random_1.nextFloat() - 0.5D) * 0.5D;
            double double_6 = ((double)random_1.nextFloat() - 0.5D) * 0.5D;
            int int_2 = random_1.nextInt(2) * 2 - 1;
            if (world_1.getBlockState(blockPos_1.west()).getBlock() != this && world_1.getBlockState(blockPos_1.east()).getBlock() != this) {
                double_1 = (double)blockPos_1.getX() + 0.5D + 0.25D * (double)int_2;
                double_4 = (double)(random_1.nextFloat() * 2.0F * (float)int_2);
            } else {
                double_3 = (double)blockPos_1.getZ() + 0.5D + 0.25D * (double)int_2;
                double_6 = (double)(random_1.nextFloat() * 2.0F * (float)int_2);
            }

            world_1.addParticle(ParticleTypes.PORTAL, double_1, double_2, double_3, double_4, double_5, double_6);
        }

    }

    @Override
    public BlockPattern.Result findPortal(IWorld worldIn, BlockPos p_181089_2_)
    {
        Direction.Axis enumfacing$axis = Direction.Axis.Z;
        PortalSize blockportal$size = new PortalSize(worldIn, p_181089_2_, Direction.Axis.X);
        LoadingCache<BlockPos, CachedBlockPosition> loadingcache = BlockPattern.makeCache(worldIn, true);

        if (!blockportal$size.isValid())
        {
            enumfacing$axis = Direction.Axis.X;
            blockportal$size = new PortalSize(worldIn, p_181089_2_, Direction.Axis.Z);
        }

        if (!blockportal$size.isValid())
        {
            return new BlockPattern.Result(p_181089_2_, Direction.NORTH, Direction.UP, loadingcache, 1, 1, 1);
        }
        else
        {
            int[] aint = new int[Direction.AxisDirection.values().length];
            Direction enumfacing = blockportal$size.rightDir.rotateYCounterclockwise();
            BlockPos blockpos = blockportal$size.bottomLeft.up(blockportal$size.getHeight() - 1);

            for (Direction.AxisDirection enumfacing$axisdirection : Direction.AxisDirection.values())
            {
                BlockPattern.Result blockpattern$patternhelper = new BlockPattern.Result(enumfacing.getDirection() == enumfacing$axisdirection ? blockpos : blockpos.offset(blockportal$size.rightDir, blockportal$size.getWidth() - 1), Direction.get(enumfacing$axisdirection, enumfacing$axis), Direction.UP, loadingcache, blockportal$size.getWidth(), blockportal$size.getHeight(), 1);

                for (int i = 0; i < blockportal$size.getWidth(); ++i)
                {
                    for (int j = 0; j < blockportal$size.getHeight(); ++j)
                    {
                        CachedBlockPosition blockworldstate = blockpattern$patternhelper.translate(i, j, 1);

                        if (blockworldstate.getBlockState() != null && blockworldstate.getBlockState().getMaterial() != Material.AIR)
                        {
                            ++aint[enumfacing$axisdirection.ordinal()];
                        }
                    }
                }
            }

            Direction.AxisDirection enumfacing$axisdirection1 = Direction.AxisDirection.POSITIVE;

            for (Direction.AxisDirection enumfacing$axisdirection2 : Direction.AxisDirection.values())
            {
                if (aint[enumfacing$axisdirection2.ordinal()] < aint[enumfacing$axisdirection1.ordinal()])
                {
                    enumfacing$axisdirection1 = enumfacing$axisdirection2;
                }
            }

            return new BlockPattern.Result(enumfacing.getDirection() == enumfacing$axisdirection1 ? blockpos : blockpos.offset(blockportal$size.rightDir, blockportal$size.getWidth() - 1), Direction.get(enumfacing$axisdirection1, enumfacing$axis), Direction.UP, loadingcache, blockportal$size.getWidth(), blockportal$size.getHeight(), 1);
        }
    }

    public static class PortalSize
    {

        private final IWorld world;
        private final Direction.Axis axis;
        final Direction rightDir;
        final Direction leftDir;
        int portalBlockCount;
        BlockPos bottomLeft;
        int height;
        int width;
        Block portalBlock;

        PortalSize(IWorld worldIn, BlockPos position, Direction.Axis axis)
        {

            Block block = Registry.BLOCK.get(new Identifier(AutoConfig.getConfigHolder(MinersHorizonConfig.class).getConfig().portalBlockId));
            if(block == Blocks.AIR) block = Blocks.CHISELED_STONE_BRICKS;
            this.portalBlock = block;

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
                ;

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

        int getDistanceUntilEdge(BlockPos position, Direction axis)
        {
            int i;

            for (i = 0; i < 22; ++i)
            {
                BlockPos blockpos = position.offset(axis, i);

                if (!this.isEmptyBlock(this.world.getBlockState(blockpos).getBlock()) || this.world.getBlockState(blockpos.down()).getBlock() != portalBlock)
                {
                    break;
                }
            }

            Block block = this.world.getBlockState(position.offset(axis, i)).getBlock();
            return block == portalBlock ? i : 0;
        }

        int getHeight()
        {
            return this.height;
        }

        int getWidth()
        {
            return this.width;
        }

        int calculatePortalHeight()
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

                        if (block != portalBlock)
                        {
                            break label24;
                        }
                    }
                    else if (i == this.width - 1)
                    {
                        block = this.world.getBlockState(blockpos.offset(this.rightDir)).getBlock();

                        if (block != portalBlock)
                        {
                            break label24;
                        }
                    }
                }
            }

            for (int j = 0; j < this.width; ++j)
            {
                if (this.world.getBlockState(this.bottomLeft.offset(this.rightDir, j).up(this.height)).getBlock() != portalBlock)
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

        boolean isEmptyBlock(Block blockIn)
        {
            return blockIn.getDefaultState().getMaterial() == Material.AIR || blockIn == Blocks.FIRE || blockIn == com.github.draylar.miners_horizon.common.Blocks.MINER_PORTAL;
        }

        boolean isValid()
        {
            return this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
        }

        void placePortalBlocks()
        {
            for (int i = 0; i < this.width; ++i)
            {
                BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i);

                for (int j = 0; j < this.height; ++j)
                {
                    this.world.setBlockState(blockpos.up(j), com.github.draylar.miners_horizon.common.Blocks.MINER_PORTAL.getDefaultState().with(AXIS, this.axis), 2);
                }
            }
        }

        private boolean method_10361()
        {
            return this.portalBlockCount >= this.height * this.width;
        }

        boolean method_10362()
        {
            return this.isValid() && this.method_10361();
        }

    }
}
