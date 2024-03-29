package draylar.horizon;

import dev.latvian.kubejs.script.ScriptType;
import draylar.horizon.config.MinersHorizonConfig;
import draylar.horizon.config.OreConfig;
import draylar.horizon.kubejs.MinersHorizonOreEventJS;
import draylar.horizon.registry.HorizonBiomes;
import draylar.horizon.registry.HorizonBlocks;
import draylar.horizon.registry.HorizonWorld;
import draylar.horizon.util.HorizonPortalHelper;
import draylar.omegaconfig.OmegaConfig;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PointOfInterestHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PickaxeItem;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.world.poi.PointOfInterestType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MinersHorizon implements ModInitializer {

    public static final MinersHorizonConfig CONFIG = OmegaConfig.register(MinersHorizonConfig.class);
    public static final PointOfInterestType HORIZON_PORTAL = PointOfInterestHelper.register(id("horizon_portal"), 0, 1, HorizonBlocks.HORIZON_PORTAL);

    @Override
    public void onInitialize() {
        HorizonBlocks.init();
        HorizonWorld.init();

        // Call KubeJS ore event
        if(FabricLoader.getInstance().isModLoaded("kubejs")) {
            new MinersHorizonOreEventJS().post(ScriptType.STARTUP, "horizon.ores");
        }

        // Register ores from config & KubeJS
        HorizonWorld.loadOres();

        // Load biomes after ores are loaded
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
