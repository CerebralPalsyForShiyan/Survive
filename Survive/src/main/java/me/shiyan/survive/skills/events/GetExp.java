package me.shiyan.survive.skills.events;

import me.shiyan.survive.Config;
import me.shiyan.survive.PlayerData;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;


import java.util.HashMap;
import java.util.Map;


public class GetExp implements Listener {
    public static Map<Player,Integer> needExp = new HashMap<>();
    @EventHandler
    public void KillEntity(EntityDeathEvent e)
    {
        if(e.getEntity().getKiller() != null)
        {
            Player p = e.getEntity().getKiller();
            if(e.getEntity() instanceof Monster)
            {
                PlayerData.addExp(p,true,5);
                CheckExp(p);
                PlayerData.saveData();
                return;
            }
            if(e.getEntity() instanceof Creature)
            {
                PlayerData.addExp(p,true,2);
                CheckExp(p);
                PlayerData.saveData();
            }
        }
    }

    @EventHandler
    public void IfPlayerJoin(PlayerJoinEvent e)
    {
        if(!needExp.containsKey(e.getPlayer()))
        {
            Player p = e.getPlayer();
            needExp.put(p,PlayerData.playerData.getInt(p.getName()+PlayerData.PlayerLevelPath)*10+10);
        }
    }
    public void CheckExp(Player p)
    {
        int need = needExp.get(p);
        if(PlayerData.playerData.getInt(p.getName()+PlayerData.PlayerExpPath) >= need)
        {
            PlayerData.addExp(p,false,1);
            needExp.replace(p,PlayerData.playerData.getInt
                    (p.getName()+PlayerData.PlayerLevelPath)*10+10);
            PlayerData.playerData.set(p.getName()+PlayerData.PlayerExpPath,0);
            PlayerData.addAttributePoint(p.getName(), Config.config.getInt("Point.EachLevelProvideAttributePoint"));
            PlayerData.addSkillPoint(p.getName(), Config.config.getInt("Point.EachLevelProvideSkillPoint"));
        }
    }
}
