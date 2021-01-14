package com.github.draylar.miners_horizon;

import com.github.draylar.miners_horizon.config.MinersHorizonConfig;
import me.sargunvohra.mcmods.autoconfig1.AutoConfig;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.impl.client.render.ColorProviderRegistryImpl;
import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.awt.*;

public class MinersHorizonClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        MinersHorizonConfig config = AutoConfig.getConfigHolder(MinersHorizonConfig.class).getConfig();

        for(Block newBlock : Registry.BLOCK)
        {
            if((newBlock.getTranslationKey().contains("ore") || newBlock.getTranslationKey().contains("stone")) && !newBlock.getTranslationKey().contains("redstone_wire"))
            {
                ColorProviderRegistryImpl.BLOCK.register((block, view, pos, layer) ->
                        {
                            World world = MinecraftClient.getInstance().world;
                            if (world != null)
                            {
                                if (world.getDimension().getType() == MinersHorizon.MINERS_HORIZON)
                                {
                                    int y = pos.getY();

                                    Color returnColor = Color.getHSBColor(0, 0, 100 / 100f);


                                    // bottom zone
                                    if (y < config.zone3Start)
                                    {
                                        returnColor = Color.getHSBColor(0, 0, config.zone1StoneDarkness / 100f);
                                    }

                                    // bottom zone smoothing
                                    else if (y < config.zone3Start + 5)
                                    {
                                        int difference = (config.zone3Start + 5) - y;
                                        returnColor = Color.getHSBColor(0, 0, (config.zone1StoneDarkness + (20f / difference)) / 100f);
                                    }

                                    // middle zone
                                    else if (y < config.zone2Start)
                                    {
                                        returnColor = Color.getHSBColor(0, 0, config.zone2StoneDarkness / 100f);
                                    }

                                    // middle zone smoothing
                                    else if (y < config.zone2Start + 5)
                                    {
                                        int difference = (config.zone2Start + 5) - y;
                                        returnColor = Color.getHSBColor(0, 0, (config.zone2StoneDarkness + (20f / difference)) / 100f);
                                    }

                                    // first zone
                                    else if (y < config.zone1Start)
                                    {
                                        returnColor = Color.getHSBColor(0, 0, config.zone3StoneDarkness / 100f);
                                    } else if (y < config.zone1Start + 5)
                                    {
                                        int difference = (config.zone1Start + 5) - y;
                                        returnColor = Color.getHSBColor(0, 0, (config.zone3StoneDarkness + (20f / difference)) / 100f);
                                    }

                                    return returnColor.getRGB();
                                }
                            }

                            // return white, nothing happens
                            return 16777215;
                        },
                        newBlock

                );
            }
        }
    }
}
