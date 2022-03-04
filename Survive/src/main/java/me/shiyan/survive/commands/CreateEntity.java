package me.shiyan.survive.commands;

import me.shiyan.survive.PlayerData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.*;

public class CreateEntity implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
        {
            return true;
        }
        Player player = (Player) sender;
        Map<String,String> s = new HashMap<>();
        s.put(player.getName(), String.valueOf(player.getExp()));
        List<Map<String,String>> mapList = new ArrayList<>();
        List<Map<String,Integer>> mapList1 = new ArrayList<>();
        mapList.add(s);
        PlayerData.playerData.set(player.getName(),mapList);
        return false;
    }
}
