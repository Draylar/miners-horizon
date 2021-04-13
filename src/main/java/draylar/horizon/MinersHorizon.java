package draylar.horizon;

import draylar.horizon.config.MinersHorizonConfig;
import draylar.horizon.registry.HorizonBiomes;
import draylar.horizon.registry.HorizonBlocks;
import draylar.horizon.registry.HorizonWorld;
import draylar.horizon.util.HorizonPortalHelper;
import me.sargunvohra.mcmods.autoconfig1u.AutoConfig;
import me.sargunvohra.mcmods.autoconfig1u.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.Optional;

public class MinersHorizon implements ModInitializer {

    public static final MinersHorizonConfig CONFIG = AutoConfig.register(MinersHorizonConfig.class, GsonConfigSerializer::new).getConfig();
    public static final PointOfInterestType HORIZON_PORTAL = PointOfInterestHelper.register(id("horizon_portal"), 0, 1, HorizonBlocks.HORIZON_PORTAL);

    @Override
    public void onInitialize() {
        HorizonBlocks.init();
        HorizonWorld.init();
        HorizonBiomes.init();

        // The following callback is used for portal activation via any Pickaxe.
        UseBlockCallback.EVENT.register((player, world, hand, blockHitResult) -> {
            if(!world.isClient) {
                ItemStack held = player.getStackInHand(hand);

                // If the player is holding a pickaxe, attempt to create a portal at the given position.
                if(held.getItem() instanceof PickaxeItem) {
                    Optional<HorizonPortalHelper> optional = HorizonPortalHelper.method_30485(world, blockHitResult.getBlockPos().up(), Direction.Axis.X);
                    optional.ifPresent(HorizonPortalHelper::createPortal);
                }
            }

            return ActionResult.PASS;
        });
    }

    public static Identifier id(String path) {
        return new Identifier("minershorizon", path);
    }
}
