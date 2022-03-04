package me.shiyan.survive.commands;

import me.shiyan.survive.Config;
import me.shiyan.survive.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class Reload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender.hasPermission("survive.commands.reload"))
        sender.sendMessage(Config.PluginHead + "重载成功");
        Player player = (Player) sender;
        PlayerData.reloadData();
        Config.reloadConfig();
        return true;
    }
}
