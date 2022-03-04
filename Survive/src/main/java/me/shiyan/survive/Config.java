package me.shiyan.survive;



import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config {
    public static String PluginHead = "&b[Survive]&e".replace("&","§");
    public static
    FileConfiguration config = YamlConfiguration.loadConfiguration(new File(Main.getInstance().getDataFolder(), "config.yml"));
    public static Plugin plugin = me.shiyan.survive.Main.getPlugin(me.shiyan.survive.Main.class);

    public void loadConfig() {
        config = YamlConfiguration.loadConfiguration(new File(Main.getInstance().getDataFolder(), "config.yml"));
        plugin.saveConfig();
        plugin.reloadConfig();
    }

    public static void saveConfig() {
        try
        {
            config.save(new File(Main.getInstance().getDataFolder(), "config.yml"));
        }catch (IOException e)
        {
            e.printStackTrace();
        }

    }
    public static void reloadConfig()
    {
        config = YamlConfiguration.loadConfiguration(new File(Main.getInstance().getDataFolder(), "config.yml"));
    }


}
