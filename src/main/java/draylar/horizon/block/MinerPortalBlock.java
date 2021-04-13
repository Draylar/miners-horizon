package draylar.horizon.block;

import draylar.horizon.registry.HorizonBlocks;
import draylar.horizon.registry.HorizonWorld;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.fabricmc.fabric.api.entity.event.v1.ServerEntityCombatEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.entity.Entity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

import java.util.Random;

public class MinerPortalBlock extends NetherPortalBlock {

    public MinerPortalBlock() {
        super(FabricBlockSettings.copy(Blocks.NETHER_PORTAL).build());
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {

    }

    @Environment(EnvType.CLIENT)
    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(100) == 0) {
            world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.AMBIENT_CAVE, SoundCategory.BLOCKS, 0.2F, random.nextFloat() * 0.4F, false);
        }

        for(int i = 0; i < 4; ++i) {
            double d = (double)pos.getX() + random.nextDouble();
            double e = (double)pos.getY() + random.nextDouble();
            double f = (double)pos.getZ() + random.nextDouble();
            double g = ((double)random.nextFloat() - 0.5D) * 0.5D;
            double h = ((double)random.nextFloat() - 0.5D) * 0.5D;
            double j = ((double)random.nextFloat() - 0.5D) * 0.5D;
            int k = random.nextInt(2) * 2 - 1;
            if (!world.getBlockState(pos.west()).isOf(this) && !world.getBlockState(pos.east()).isOf(this)) {
                d = (double)pos.getX() + 0.5D + 0.25D * (double)k;
                g = (double)(random.nextFloat() * 2.0F * (float)k);
            } else {
                f = (double)pos.getZ() + 0.5D + 0.25D * (double)k;
                j = (double)(random.nextFloat() * 2.0F * (float)k);
            }

//            world.addParticle(new BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.DIAMOND_ORE.getDefaultState()), d, e, f, g, h, j);
            world.addParticle(ParticleTypes.ASH, d, e, f, g, h, j);
        }
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if(entity.isSneaking()) {
            entity.setSneaking(false);

            if(!world.isClient) {
                ServerWorld target;

                if(entity.world.getRegistryKey().equals(HorizonWorld.MINERS_HORIZON)) {
                    target = ((ServerWorld) world).getServer().getWorld(World.OVERWORLD);
                } else {
                    target = ((ServerWorld) world).getServer().getWorld(HorizonWorld.MINERS_HORIZON);
                }

                // create portal if it does not exist
                if(!target.getBlockState(entity.getBlockPos()).getBlock().equals(HorizonBlocks.MINER_PORTAL)) {
                    buildPortal(target, entity.getBlockPos());
                }

                FabricDimensions.teleport(entity, target, new TeleportTarget(entity.getPos(), entity.getVelocity(), entity.yaw, entity.pitch));
            }
        }

        super.onEntityCollision(state, world, pos, entity);
    }

    private void buildPortal(ServerWorld world, BlockPos pos) {
        // hollow frame
        for(int x = -1; x <= 2; x++) {
            for(int y = -1; y <= 3; y++) {
                world.setBlockState(pos.up(y).north(x), Blocks.CHISELED_STONE_BRICKS.getDefaultState());
            }
        }

        // portal
        for(int x = 0; x <= 1; x++) {
            for(int y = 0; y <= 2; y++) {
                world.setBlockState(pos.up(y).north(x), HorizonBlocks.MINER_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, Direction.Axis.Z));
            }
        }
    }
}
