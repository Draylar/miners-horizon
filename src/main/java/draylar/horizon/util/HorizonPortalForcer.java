package draylar.horizon.util;

import draylar.horizon.MinersHorizon;
import draylar.horizon.registry.HorizonBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.server.world.ChunkTicketType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.*;
import net.minecraft.world.Heightmap;
import net.minecraft.world.PortalUtil;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.poi.PointOfInterest;
import net.minecraft.world.poi.PointOfInterestStorage;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Optional;

public class HorizonPortalForcer {

    private final ServerWorld world;

    public HorizonPortalForcer(ServerWorld world) {
        this.world = world;
    }

    public Optional<PortalUtil.Rectangle> findPortal(BlockPos blockPos, boolean goingToHorizon) {
        PointOfInterestStorage storage = this.world.getPointOfInterestStorage();
        int radius = goingToHorizon ? 16 : 128;
        storage.preloadChunks(this.world, blockPos, radius);

        Optional<PointOfInterest> point = storage
                .getInSquare(poi -> poi == MinersHorizon.HORIZON_PORTAL, blockPos, radius, PointOfInterestStorage.OccupationStatus.ANY)
                .sorted(Comparator.<PointOfInterest>comparingDouble(poi -> poi.getPos().getSquaredDistance(blockPos))
                .thenComparingInt(poi -> poi.getPos().getY()))
                .filter(poi -> this.world.getBlockState(poi.getPos()).contains(Properties.HORIZONTAL_AXIS)).findFirst();

        return point.map(poi -> {
            BlockPos pos = poi.getPos();
            this.world.getChunkManager().addTicket(ChunkTicketType.PORTAL, new ChunkPos(pos), 3, pos);
            BlockState state = this.world.getBlockState(pos);

            return PortalUtil.getLargestRectangle(
                    pos,
                    state.get(Properties.HORIZONTAL_AXIS),
                    21,
                    Direction.Axis.Y,
                    21,
                    nPos -> this.world.getBlockState(nPos) == state);
        });
    }

    public Optional<PortalUtil.Rectangle> createPortal(BlockPos blockPos, Direction.Axis axis) {
        Direction direction = Direction.get(Direction.AxisDirection.POSITIVE, axis);
        double d = -1.0D;
        BlockPos blockPos2 = null;
        double e = -1.0D;
        BlockPos blockPos3 = null;
        WorldBorder worldBorder = this.world.getWorldBorder();
        int i = this.world.getDimensionHeight() - 1;
        BlockPos.Mutable mutable = blockPos.mutableCopy();
        Iterator<BlockPos.Mutable> var13 = BlockPos.method_30512(blockPos, 16, Direction.EAST, Direction.SOUTH).iterator();

        while(true) {
            BlockPos.Mutable mutable2;
            int p;
            do {
                do {
                    if (!var13.hasNext()) {
                        if (d == -1.0D && e != -1.0D) {
                            blockPos2 = blockPos3;
                            d = e;
                        }

                        int o;
                        if (d == -1.0D) {
                            blockPos2 = (new BlockPos(blockPos.getX(), MathHelper.clamp(blockPos.getY(), 70, this.world.getDimensionHeight() - 10), blockPos.getZ())).toImmutable();
                            Direction direction2 = direction.rotateYClockwise();
                            if (!worldBorder.contains(blockPos2)) {
                                return Optional.empty();
                            }

                            for(o = -1; o < 2; ++o) {
                                for(p = 0; p < 2; ++p) {
                                    for(int q = -1; q < 3; ++q) {
                                        BlockState blockState = q < 0 ? Blocks.CHISELED_STONE_BRICKS.getDefaultState() : Blocks.AIR.getDefaultState();
                                        mutable.set((Vec3i)blockPos2, p * direction.getOffsetX() + o * direction2.getOffsetX(), q, p * direction.getOffsetZ() + o * direction2.getOffsetZ());
                                        this.world.setBlockState(mutable, blockState);
                                    }
                                }
                            }
                        }

                        for(int r = -1; r < 3; ++r) {
                            for(o = -1; o < 4; ++o) {
                                if (r == -1 || r == 2 || o == -1 || o == 3) {
                                    mutable.set(blockPos2, r * direction.getOffsetX(), o, r * direction.getOffsetZ());
                                    this.world.setBlockState(mutable, Blocks.CHISELED_STONE_BRICKS.getDefaultState(), 3);
                                }
                            }
                        }

                        BlockState blockState2 = HorizonBlocks.HORIZON_PORTAL.getDefaultState().with(NetherPortalBlock.AXIS, axis);

                        for(o = 0; o < 2; ++o) {
                            for(p = 0; p < 3; ++p) {
                                mutable.set(blockPos2, o * direction.getOffsetX(), p, o * direction.getOffsetZ());
                                this.world.setBlockState(mutable, blockState2, 18);
                            }
                        }

                        return Optional.of(new PortalUtil.Rectangle(blockPos2.toImmutable(), 2, 3));
                    }

                    mutable2 = var13.next();
                    p = Math.min(i, this.world.getTopY(Heightmap.Type.MOTION_BLOCKING, mutable2.getX(), mutable2.getZ()));
                } while(!worldBorder.contains(mutable2));
            } while(!worldBorder.contains(mutable2.move(direction, 1)));

            mutable2.move(direction.getOpposite(), 1);

            for(int l = p; l >= 0; --l) {
                mutable2.setY(l);
                if (this.world.isAir(mutable2)) {
                    int m;
                    m = l;
                    while (l > 0 && this.world.isAir(mutable2.move(Direction.DOWN))) {
                        --l;
                    }

                    if (l + 4 <= i) {
                        int n = m - l;
                        if (n <= 0 || n >= 3) {
                            mutable2.setY(l);
                            if (this.canHostFrame(mutable2, mutable, direction, 0)) {
                                double f = blockPos.getSquaredDistance(mutable2);
                                if (this.canHostFrame(mutable2, mutable, direction, -1) && this.canHostFrame(mutable2, mutable, direction, 1) && (d == -1.0D || d > f)) {
                                    d = f;
                                    blockPos2 = mutable2.toImmutable();
                                }

                                if (d == -1.0D && (e == -1.0D || e > f)) {
                                    e = f;
                                    blockPos3 = mutable2.toImmutable();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private boolean canHostFrame(BlockPos blockPos, BlockPos.Mutable mutable, Direction direction, int i) {
        Direction rotated = direction.rotateYClockwise();

        for(int horizontal = -1; horizontal < 3; ++horizontal) {
            for(int vertical = -1; vertical < 4; ++vertical) {
                mutable.set(blockPos, direction.getOffsetX() * horizontal + rotated.getOffsetX() * i, vertical, direction.getOffsetZ() * horizontal + rotated.getOffsetZ() * i);

                if (vertical < 0 && !this.world.getBlockState(mutable).getMaterial().isSolid()) {
                    return false;
                }

                if (vertical >= 0 && !this.world.isAir(mutable)) {
                    return false;
                }
            }
        }

        return true;
    }
}
