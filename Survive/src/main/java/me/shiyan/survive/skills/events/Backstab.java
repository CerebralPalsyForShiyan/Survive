package me.shiyan.survive.skills.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.List;

//Backstab = 背刺
//现在还不想写..下次一定
public class Backstab implements Listener {

    @EventHandler
    public void BackAttack(EntityDamageByEntityEvent e)
    {
        if(e.getDamager() instanceof Player)
        {
            e.getDamager().sendMessage(String.valueOf(e.getEntity().getNearbyEntities(3,3,3)));
            if(e.getEntity().getNearbyEntities(3,3,3).contains(e.getDamager())
                    )
            {
                e.getDamager().sendMessage("yes");
                e.setDamage(e.getDamage()*1.5);
            }
            if(onlyHasPlayer(e.getEntity().getNearbyEntities(3,3,3), ((Player) e.getDamager()).getPlayer()))
            {

            }
        }
    }

    public static boolean onlyHasPlayer(List<Entity> entityList,Player player)
    {
        for(Entity e : entityList)
        {
            player.sendMessage(String.valueOf(e));
            if(e instanceof Player)
            {
                player.sendMessage("qwer");
                return true;
            }
        }
        return false;
    }
}
