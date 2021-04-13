package draylar.horizon;

import draylar.horizon.registry.HorizonWorld;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.ColorProviderRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.OreBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.awt.*;

public class MinersHorizonClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Register a color provider for stone/ore blocks.
        // This color provider is used for the darkened layers in the Miner's Horizon.
        for (Block entry : Registry.BLOCK) {
            if (entry instanceof OreBlock || entry.getDefaultState().getMaterial().equals(Material.STONE)) {
                ColorProviderRegistry.BLOCK.register((block, view, pos, layer) -> {
                            World world = MinecraftClient.getInstance().world;
                            if (world != null && pos != null) {

                                // Only apply the darkened hue if the player is in the Miner's Horizon dimension
                                if (world.getRegistryKey().equals(HorizonWorld.MINERS_HORIZON)) {
                                    int y = pos.getY();
                                    Color returnColor = Color.getHSBColor(0, 0, 100 / 100f);

                                    // The bottom zone has the darkest hue.
                                    if (y < MinersHorizon.CONFIG.zone3Start) {
                                        returnColor = Color.getHSBColor(0, 0, MinersHorizon.CONFIG.zone1StoneDarkness / 100f);
                                    }

                                    // bottom zone smoothing
                                    else if (y < MinersHorizon.CONFIG.zone3Start + 5) {
                                        int difference = (MinersHorizon.CONFIG.zone3Start + 5) - y;
                                        returnColor = Color.getHSBColor(0, 0, (MinersHorizon.CONFIG.zone1StoneDarkness + (20f / difference)) / 100f);
                                    }

                                    // middle zone
                                    else if (y < MinersHorizon.CONFIG.zone2Start) {
                                        returnColor = Color.getHSBColor(0, 0, MinersHorizon.CONFIG.zone2StoneDarkness / 100f);
                                    }

                                    // middle zone smoothing
                                    else if (y < MinersHorizon.CONFIG.zone2Start + 5) {
                                        int difference = (MinersHorizon.CONFIG.zone2Start + 5) - y;
                                        returnColor = Color.getHSBColor(0, 0, (MinersHorizon.CONFIG.zone2StoneDarkness + (20f / difference)) / 100f);
                                    }

                                    // first zone
                                    else if (y < MinersHorizon.CONFIG.zone1Start) {
                                        returnColor = Color.getHSBColor(0, 0, MinersHorizon.CONFIG.zone3StoneDarkness / 100f);
                                    } else if (y < MinersHorizon.CONFIG.zone1Start + 5) {
                                        int difference = (MinersHorizon.CONFIG.zone1Start + 5) - y;
                                        returnColor = Color.getHSBColor(0, 0, (MinersHorizon.CONFIG.zone3StoneDarkness + (20f / difference)) / 100f);
                                    }

                                    return returnColor.getRGB();
                                }
                            }

                            // return white, nothing happens
                            return 0x00ffffff;
                        },
                        entry
                );
            }
        }
    }
}
