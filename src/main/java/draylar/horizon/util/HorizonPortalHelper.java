package draylar.horizon.util;

import draylar.horizon.block.HorizonPortalBlock;
import draylar.horizon.registry.HorizonBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.PortalUtil;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Predicate;

// source: HorizonPortalHelper
public class HorizonPortalHelper {

    private static final AbstractBlock.ContextPredicate FRAME_VALIDITY_PREDICATE = (blockState, blockView, blockPos) -> blockState.isOf(Blocks.CHISELED_STONE_BRICKS);
    private final WorldAccess world;
    private final Direction.Axis axis;
    private final Direction negativeDir;
    private int foundPortalBlocks;
    @Nullable private BlockPos lowerCorner;
    private int height;
    private int width;

    public HorizonPortalHelper(WorldAccess world, BlockPos blockPos, Direction.Axis axis) {
        this.world = world;
        this.axis = axis;
        this.negativeDir = axis == Direction.Axis.X ? Direction.WEST : Direction.SOUTH;
        this.lowerCorner = this.findLowerCorner(blockPos);

        if (this.lowerCorner == null) {
            this.lowerCorner = blockPos;
            this.width = 1;
            this.height = 1;
        } else {
            this.width = this.method_30495();
            if (this.width > 0) {
                this.height = this.method_30496();
            }
        }
    }

    public static Optional<HorizonPortalHelper> method_30485(WorldAccess worldAccess, BlockPos blockPos, Direction.Axis axis) {
        return method_30486(worldAccess, blockPos, (HorizonPortalHelper) -> {
            return HorizonPortalHelper.isValid() && HorizonPortalHelper.foundPortalBlocks == 0;
        }, axis);
    }

    public static Optional<HorizonPortalHelper> method_30486(WorldAccess worldAccess, BlockPos blockPos, Predicate<HorizonPortalHelper> predicate, Direction.Axis axis) {
        Optional<HorizonPortalHelper> optional = Optional.of(new HorizonPortalHelper(worldAccess, blockPos, axis)).filter(predicate);

        if (optional.isPresent()) {
            return optional;
        } else {
            Direction.Axis axis2 = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
            return Optional.of(new HorizonPortalHelper(worldAccess, blockPos, axis2)).filter(predicate);
        }
    }

    @Nullable
    private BlockPos findLowerCorner(BlockPos blockPos) {
        for(int i = Math.max(0, blockPos.getY() - 21); blockPos.getY() > i && validStateInsidePortal(this.world.getBlockState(blockPos.down())); blockPos = blockPos.down()) {
        }

        Direction direction = this.negativeDir.getOpposite();
        int j = this.method_30493(blockPos, direction) - 1;
        return j < 0 ? null : blockPos.offset(direction, j);
    }

    private int method_30495() {
        int i = this.method_30493(this.lowerCorner, this.negativeDir);
        return i >= 2 && i <= 21 ? i : 0;
    }

    private int method_30493(BlockPos blockPos, Direction direction) {
        BlockPos.Mutable mutable = new BlockPos.Mutable();

        for(int i = 0; i <= 21; ++i) {
            mutable.set(blockPos).move(direction, i);
            BlockState blockState = this.world.getBlockState(mutable);
            if (!validStateInsidePortal(blockState)) {
                if (FRAME_VALIDITY_PREDICATE.test(blockState, this.world, mutable)) {
                    return i;
                }
                break;
            }

            BlockState blockState2 = this.world.getBlockState(mutable.move(Direction.DOWN));
            if (!FRAME_VALIDITY_PREDICATE.test(blockState2, this.world, mutable)) {
                break;
            }
        }

        return 0;
    }

    private int method_30496() {
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        int i = this.method_30490(mutable);
        return i >= 3 && i <= 21 && this.method_30491(mutable, i) ? i : 0;
    }

    private boolean method_30491(BlockPos.Mutable mutable, int i) {
        for(int j = 0; j < this.width; ++j) {
            BlockPos.Mutable mutable2 = mutable.set(this.lowerCorner).move(Direction.UP, i).move(this.negativeDir, j);
            if (!FRAME_VALIDITY_PREDICATE.test(this.world.getBlockState(mutable2), this.world, mutable2)) {
                return false;
            }
        }

        return true;
    }

    private int method_30490(BlockPos.Mutable mutable) {
        for(int i = 0; i < 21; ++i) {
            mutable.set(this.lowerCorner).move(Direction.UP, i).move(this.negativeDir, -1);
            if (!FRAME_VALIDITY_PREDICATE.test(this.world.getBlockState(mutable), this.world, mutable)) {
                return i;
            }

            mutable.set(this.lowerCorner).move(Direction.UP, i).move(this.negativeDir, this.width);
            if (!FRAME_VALIDITY_PREDICATE.test(this.world.getBlockState(mutable), this.world, mutable)) {
                return i;
            }

            for(int j = 0; j < this.width; ++j) {
                mutable.set(this.lowerCorner).move(Direction.UP, i).move(this.negativeDir, j);
                BlockState blockState = this.world.getBlockState(mutable);
                if (!validStateInsidePortal(blockState)) {
                    return i;
                }

                if (blockState.isOf(HorizonBlocks.HORIZON_PORTAL)) {
                    ++this.foundPortalBlocks;
                }
            }
        }

        return 21;
    }

    /**
     * Returns whether the given {@link BlockState} is valid when inside a Miner's Horizon portal.
     * @param state state to check for validity
     * @return whether the state is valid when inside the portal
     */
    private static boolean validStateInsidePortal(BlockState state) {
        return state.isAir() || state.isOf(HorizonBlocks.HORIZON_PORTAL);
    }

    /**
     * @return true if the current portal is valid, accounting for size. If the portal is not valid, returns false.
     */
    public boolean isValid() {
        return this.lowerCorner != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
    }

    public void createPortal() {
        BlockState portalState = HorizonBlocks.HORIZON_PORTAL.getDefaultState().with(HorizonPortalBlock.AXIS, this.axis);
        BlockPos.iterate(this.lowerCorner, this.lowerCorner.offset(Direction.UP, this.height - 1).offset(this.negativeDir, this.width - 1)).forEach((blockPos) -> {
            this.world.setBlockState(blockPos, portalState, 18);
        });
    }

    public boolean wasAlreadyValid() {
        return this.isValid() && this.foundPortalBlocks == this.width * this.height;
    }

    public static Vec3d method_30494(PortalUtil.Rectangle rectangle, Direction.Axis axis, Vec3d vec3d, EntityDimensions entityDimensions) {
        double d = (double)rectangle.width - (double)entityDimensions.width;
        double e = (double)rectangle.height - (double)entityDimensions.height;
        BlockPos blockPos = rectangle.lowerLeft;
        double h;
        if (d > 0.0D) {
            float f = (float)blockPos.getComponentAlongAxis(axis) + entityDimensions.width / 2.0F;
            h = MathHelper.clamp(MathHelper.getLerpProgress(vec3d.getComponentAlongAxis(axis) - (double)f, 0.0D, d), 0.0D, 1.0D);
        } else {
            h = 0.5D;
        }

        double j;
        Direction.Axis axis3;
        if (e > 0.0D) {
            axis3 = Direction.Axis.Y;
            j = MathHelper.clamp(MathHelper.getLerpProgress(vec3d.getComponentAlongAxis(axis3) - (double)blockPos.getComponentAlongAxis(axis3), 0.0D, e), 0.0D, 1.0D);
        } else {
            j = 0.0D;
        }

        axis3 = axis == Direction.Axis.X ? Direction.Axis.Z : Direction.Axis.X;
        double k = vec3d.getComponentAlongAxis(axis3) - ((double)blockPos.getComponentAlongAxis(axis3) + 0.5D);
        return new Vec3d(h, j, k);
    }

    public static TeleportTarget method_30484(ServerWorld serverWorld, PortalUtil.Rectangle rectangle, Direction.Axis axis, Vec3d vec3d, EntityDimensions entityDimensions, Vec3d vec3d2, float f, float g) {
        BlockPos blockPos = rectangle.lowerLeft;
        BlockState blockState = serverWorld.getBlockState(blockPos);
        Direction.Axis axis2 = (Direction.Axis)blockState.get(Properties.HORIZONTAL_AXIS);
        double d = (double)rectangle.width;
        double e = (double)rectangle.height;
        int i = axis == axis2 ? 0 : 90;
        Vec3d vec3d3 = axis == axis2 ? vec3d2 : new Vec3d(vec3d2.z, vec3d2.y, -vec3d2.x);
        double h = (double)entityDimensions.width / 2.0D + (d - (double)entityDimensions.width) * vec3d.getX();
        double j = (e - (double)entityDimensions.height) * vec3d.getY();
        double k = 0.5D + vec3d.getZ();
        boolean bl = axis2 == Direction.Axis.X;
        Vec3d vec3d4 = new Vec3d((double)blockPos.getX() + (bl ? h : k), (double)blockPos.getY() + j, (double)blockPos.getZ() + (bl ? k : h));
        return new TeleportTarget(vec3d4, vec3d3, f + (float)i, g);
    }
}
