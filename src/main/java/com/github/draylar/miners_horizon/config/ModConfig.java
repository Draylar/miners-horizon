package com.github.draylar.miners_horizon.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.Identifier;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ModConfig
{
    private String modID = "miners-horizon";

    /**
     * Checks to see if the config folder exists.
     * If the config folder does not exist, it creates it.
     * It then moves on to checking the config file.
     */
    public void checkConfigFolder()
    {
        Path configPath = Paths.get(System.getProperty("user.dir") + "/config/" + modID);

        if (configPath.toFile().isDirectory()) checkConfigFile(configPath);

        else
        {
            configPath.toFile().mkdirs();
            checkConfigFile(configPath);
        }
    }


    /**
     * Checks the config file.
     * If the config file does not exist, it is created and filled with default values.
     * If the config file does exist, the values are transferred to the config holder.
     *
     * @param path
     */
    public void checkConfigFile(Path path)
    {
        Path jsonPath = Paths.get(path + "/"+ modID + ".json");

        if (!jsonPath.toFile().exists())
        {
            createNewFile(jsonPath);
            setBlankConfig(jsonPath, true);
        }

        try {
            // read config
            String input = new String(Files.readAllBytes(jsonPath));

            // save to current config holder
            Gson gson = new Gson();
            ConfigHolder.configInstance = gson.fromJson(input, ConfigGson.class);

            // check for null json reading
            if(ConfigHolder.configInstance.oreConfigList == null)
            {
                System.out.println("[Miner's Horizon] Json is invalid! Try to fix from the error, or remove config file. Rolling with defaults!");
                setBlankConfig(jsonPath, false);
            }
        }

        catch (Exception e)
        {
            System.out.println("[Miner's Horizon] Json is invalid! Try to fix from the error, or remove config file. Rolling with defaults!");
            e.printStackTrace();

            // to prevent future errors, just set filler values in current config.
            // we leave the config file alone and assume the user will see the errors in console
            // and either delete or fix it.
            setBlankConfig(jsonPath, false);
        }
    }


    /**
     * Sets the current config instance to blank values.
     * If writeConfig is true, we also write the config to the config file.
     * @param jsonPath
     * @param writeConfig
     */
    private void setBlankConfig(Path jsonPath, boolean writeConfig)
    {
        // create blank config
        ConfigGson config = new ConfigGson();

        config.enableMineshafts = true;
        config.mineshaftRarity = 0.008d;
        config.mountainHeight = 2.5;
        config.worldMidHeight = 150;
        config.zone1Y = 100;
        config.zone2Y = 65;
        config.zone3Y = 30;
        config.oreConfigList = new OreConfig[]
                {
                        // default, same density and size as vanilla
                        new OreConfig(new Identifier("minecraft:coal_ore"), 17, 17, 100, 0, 256),
                        new OreConfig(new Identifier("minecraft:iron_ore"), 13, 17, 100, 0, 256),

                        // zone 1, larger coal & iron with vanilla gold rate
                        new OreConfig(new Identifier("minecraft:coal_ore"), 17, 10, config.zone2Y, 0, config.zone1Y),
                        new OreConfig(new Identifier("minecraft:iron_ore"), 14, 10, config.zone2Y, 0, config.zone1Y),
                        new OreConfig(new Identifier("minecraft:gold_ore"), 9, 4, config.zone2Y, 0, config.zone1Y - config.zone2Y),

                        // zone 2, more coal/iron/gold with default redstone & lapis
                        new OreConfig(new Identifier("minecraft:coal_ore"), 13, 10, config.zone3Y, 0, config.zone2Y),
                        new OreConfig(new Identifier("minecraft:iron_ore"), 13, 13, config.zone3Y, 0, config.zone2Y),
                        new OreConfig(new Identifier("minecraft:gold_ore"), 14, 4, config.zone3Y, 0, config.zone2Y),
                        new OreConfig(new Identifier("minecraft:redstone_ore"), 14, 8, config.zone3Y, 0, config.zone2Y),
                        new OreConfig(new Identifier("minecraft:lapis_ore"), 7, 4, config.zone3Y, 0, config.zone2Y),

                        // zone 3, more redstone & lapis with slightly higher diamond/emerald rate
                        new OreConfig(new Identifier("minecraft:coal_ore"), 9, 5, 0, 0, config.zone3Y),
                        new OreConfig(new Identifier("minecraft:iron_ore"), 13, 10, 0, 0, config.zone3Y),
                        new OreConfig(new Identifier("minecraft:gold_ore"), 14, 3, 0, 0, config.zone3Y),
                        new OreConfig(new Identifier("minecraft:redstone_ore"), 14, 8, 0, 0, config.zone3Y),
                        new OreConfig(new Identifier("minecraft:lapis_ore"), 7, 3, 0, 0, config.zone3Y),
                        new OreConfig(new Identifier("minecraft:emerald_ore"), 8, 2, 0, 0, config.zone3Y),
                        new OreConfig(new Identifier("minecraft:diamond_ore"), 8, 2, 0, 0, config.zone3Y)
                };


        // write to config holder
        ConfigHolder.configInstance = config;

        if(writeConfig) writeConfigToPath(jsonPath, config);
    }


    /**
     * Writes the input config to our config file.
     * @param jsonPath
     * @param config
     */
    private void writeConfigToPath(Path jsonPath, ConfigGson config)
    {
        try
        {
            Files.write(jsonPath, new GsonBuilder().setPrettyPrinting().create().toJson(config).getBytes());
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Attempts to create a new file.
     * @param path
     */
    private void createNewFile(Path path)
    {
        try
        {
            path.toFile().createNewFile();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
