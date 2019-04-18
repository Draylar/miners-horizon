package com.github.draylar.fabric.world;

import com.github.draylar.fabric.FabricDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.HashSet;

public class FabricPlacementHandler
{
    public static void enterDimension(Entity entity, ServerWorld previousWorld, ServerWorld newWorld) {
        if (newWorld.dimension.getType() == FabricDimensions.FABRIC_WORLD) {
            BlockPos spawnPos = new BlockPos(0, 200, 0);
            spawnEntryPlatform(newWorld, spawnPos.down());
            entity.setPositionAndAngles(spawnPos.getX(), spawnPos.getY(), spawnPos.getZ(), 0, 0);
        }
    }

    public static void leaveDimension(Entity entity, ServerWorld previousWorld, ServerWorld newWorld){
        BlockPos spawnLocation = getBedLocation((PlayerEntity) entity, newWorld);
        if (spawnLocation == null) {
            spawnLocation = newWorld.getSpawnPos();
        }

        setEntityLocation(entity, spawnLocation);
    }

    public static void spawnEntryPlatform(World world, BlockPos pos) {

//            BlockState platformBlock = Blocks.DIAMOND_BLOCK.getDefaultState();
//            for (int x = -3; x < 4; x++) {
//                for (int z = -3; z < 4; z++) {
//                    if (world.isAir(pos.add(x, 0, z))) {
//                        world.setBlockState(pos.add(x, 0, z), platformBlock);
//                    }
//
//                }
//            }
    }

    public static BlockPos getBedLocation(PlayerEntity player, ServerWorld world) {
        BlockPos bedLocation = player.getSpawnPosition();
        if (bedLocation == null) {
            return null;
        }
        //method_7288 = getBedSpawn
        BlockPos bedSpawnLocation = PlayerEntity.method_7288(world, bedLocation, false);
        return bedSpawnLocation;
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
