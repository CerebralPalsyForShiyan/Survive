package me.shiyan.survive.methods;

import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class Methods {
    protected Map<Player,Integer> blockCooldown = new HashMap<>();


    public boolean ItemcanBlock(ItemMeta meta)
    {
        return meta.getDisplayName().contains("反击");
    }
    public boolean canBlock(Player player)
    {
        if(!blockCooldown.containsKey(player))
        {
            blockCooldown.put(player,30);
            return true;
        }
        if(blockCooldown.get(player) >= 0)
        {
            return false;
        }
        return false;
    }
}
