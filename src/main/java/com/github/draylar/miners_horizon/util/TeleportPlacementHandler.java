package com.github.draylar.miners_horizon.util;

import com.github.draylar.miners_horizon.MinersHorizon;
import com.github.draylar.miners_horizon.common.Blocks;
import com.github.draylar.miners_horizon.config.MinersHorizonConfig;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

import java.util.HashSet;

public class TeleportPlacementHandler
{
    public static void enterDimension(Entity entity, ServerWorld previousWorld, ServerWorld newWorld) {
        if (newWorld.dimension.getType() == MinersHorizon.MINERS_HORIZON) {
            Block block = Registry.BLOCK.get(new Identifier(AutoConfig.getConfigHolder(MinersHorizonConfig.class).getConfig().portalBlockId));
            if(block == net.minecraft.block.Blocks.AIR) block = net.minecraft.block.Blocks.CHISELED_STONE_BRICKS;

            // find closest portal block in a 4 chunk radius
            for (int x = -16 * 4; x < 16 * 4; x++)
            {
                for (int z = -16 * 4; z < 16 * 4; z++)
                {
                    for (int y = 0; y < 250; y++)
                    {
                        BlockPos pos = new BlockPos(entity.getPos().getX() + x, y, entity.getPos().getZ() + z);

                        if(newWorld.getBlockState(pos).getBlock() == Blocks.MINER_PORTAL)
                        {
                            entity.setSneaking(false);
                            entity.setPositionAndAngles(pos.getX() + 1, pos.getY() + 1, pos.getZ(), 0, 0);
                            return;
                        }
                    }
                }
            }

            entity.setSneaking(false);

            // nothing found, just spawn them in the top pos and place a portal

            int firstY = 160;

            // find first open y spot
            for (int i = 255; i > 0; i--)
            {
                if(newWorld.getBlockState(new BlockPos(entity.getPos().x, i, entity.getPos().z)).getBlock() != net.minecraft.block.Blocks.AIR)
                {
                    firstY = i;
                    break;
                }
            }

            BlockPos spawnPos = new BlockPos(entity.getPos().x, firstY, entity.getPos().z);
            entity.setPositionAndAngles(spawnPos.getX() + 1, spawnPos.getY() + 1, spawnPos.getZ(), 0, 0);

            BlockPos pos = new BlockPos(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ());

            newWorld.setBlockState(pos, block.getDefaultState());
            newWorld.setBlockState(pos.east(), block.getDefaultState());

            newWorld.setBlockState(pos.down().east(2).up(2), block.getDefaultState());
            newWorld.setBlockState(pos.down().east(2).up(3), block.getDefaultState());
            newWorld.setBlockState(pos.down().east(2).up(4), block.getDefaultState());

            newWorld.setBlockState(pos.down().west().up(2), block.getDefaultState());
            newWorld.setBlockState(pos.down().west().up(3), block.getDefaultState());
            newWorld.setBlockState(pos.down().west().up(4), block.getDefaultState());

            newWorld.setBlockState(pos.up(4), block.getDefaultState());
            newWorld.setBlockState(pos.up(4).east(), block.getDefaultState());

            for (int x = 0; x < 2; x++)
            {
                for (int y = 0; y < 3; y++)
                {
                    newWorld.setBlockState(pos.up(y).east(x).up(), Blocks.MINER_PORTAL.getDefaultState());
                }
            }
        }
    }

    public static void leaveDimension(Entity entity, ServerWorld previousWorld, ServerWorld newWorld){
        BlockPos spawnLocation = getBedLocation((PlayerEntity) entity, newWorld);

        if (spawnLocation == null) {
            // if you're reading this and wondering why it changed... oh boy :)
            spawnLocation = new BlockPos(0, 100, 0);
        }

        // find closest portal block in a 4 chunk radius
        for (int x = -16 * 4; x < 16 * 4; x++)
        {
            for (int z = -16 * 4; z < 16 * 4; z++)
            {
                for (int y = 0; y < 250; y++)
                {
                    BlockPos pos = new BlockPos(entity.getPos().getX() + x, y, entity.getPos().getZ() + z);

                    if(newWorld.getBlockState(pos).getBlock() == Blocks.MINER_PORTAL)
                    {
                        entity.setSneaking(false);
                        entity.setPositionAndAngles(pos.getX() + .5, pos.getY() + .5, pos.getZ() + .5, 0, 0);
                        return;
                    }
                }
            }
        }

        setEntityLocation(entity, spawnLocation);
    }


    public static BlockPos getBedLocation(PlayerEntity player, ServerWorld world) {
        BlockPos bedLocation = player.getSpawnPosition();
        if (bedLocation == null) {
            return null;
        }
        //method_7288 = getBedSpawn
        BlockPos origin = world.getTopPosition(Heightmap.Type.MOTION_BLOCKING, new BlockPos(0, 0, 0));
        return new BlockPos(PlayerEntity.method_7288(world, bedLocation, false).orElse(new Vec3d(origin.getX(), origin.getY(), origin.getZ())));
    }

    public static void setEntityLocation(Entity entity, BlockPos pos) {
        if (entity instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity) entity).networkHandler.teleportRequest(pos.getX(), pos.getY(), pos.getZ(), 0, 0, new HashSet<>());
            ((ServerPlayerEntity) entity).networkHandler.syncWithPlayerPosition();
        } else {
            entity.setPositionAndAngles(pos.getX(), pos.getY(), pos.getZ(), 0, 0);
        }
    }
}
