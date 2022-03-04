package me.shiyan.survive.item;

import me.shiyan.survive.Config;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;


//SHIELD = 盾牌
public class BlockItem implements CommandExecutor {
    static ItemStack itemStack = new ItemStack(Material.ARROW);
    static ItemMeta meta = itemStack.getItemMeta();
    Map<Player,Integer> strength = new HashMap<>();


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player))
        {
            sender.sendMessage("只能由玩家使用");
            return true;
        }
        if(sender instanceof Player) {
            ItemStack sword = new ItemStack(Material.GOLD_SWORD);
            ItemMeta meta = sword.getItemMeta();
            meta.setDisplayName("§b反击剑");
            sword.setItemMeta(meta);
            Player player = ((Player) sender).getPlayer();
            player.getInventory().addItem(sword);
            strength.put(player, 100);
            player.sendMessage(Config.config.getName() + " 安装" +
                    Config.config);
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new
                    TextComponent(Config.PluginHead + "成功获得"));

        }
        return false;
    }
}
