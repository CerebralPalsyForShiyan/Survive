package me.shiyan.survive.commands;

import me.shiyan.survive.Config;
import me.shiyan.survive.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class TheCommands implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length > 0)
        {
            switch (args[0])
            {
                case "reload":
                {
                    if(sender.hasPermission("survive.commands.reload"))
                    {
                        sender.sendMessage(Config.PluginHead+"重载成功");
                        Config.reloadConfig();
                        PlayerData.reloadData();
                    }

                }
            }
        }
        return false;
    }
}
