package me.shiyan.survive.events;

import me.shiyan.survive.Main;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ShowDamage implements Listener {

    @EventHandler
    public void show(EntityDamageByEntityEvent e)
    {
        if(e.getDamager().getType().equals(EntityType.PLAYER))
        {

            Player player = (Player) e.getDamager();
            new BukkitRunnable(){
                @Override
                public void run() {
                    player.sendTitle("",
                            "造成了："+String.valueOf("§b"+e.getDamage())+"点伤害",1,15,8);
                    this.cancel();

                }
            }.runTaskLater(Main.getPlugin(Main.class),5);

        }
    }
}
