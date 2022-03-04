package me.shiyan.survive.commands;

import me.shiyan.survive.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

//没用的
public class scax implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        FileConfiguration config = Config.config;
        int yes = config.getStringList("Msg."+args[0]).size();
        for(int i=0;i< yes;i++)
        {
            sender.sendMessage(config.getStringList("Msg."+args[0]).get(i));
        }
        return true;
    }
}
